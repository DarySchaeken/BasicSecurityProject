package be.basicsecurity.securevault.models;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
public class Account {
	@Id
	private String userName;
	private String hashedPassword;
	private String salt;
	@Lob
	private File privateKey;
	@Lob
	private File publicKey;

	public Account() {}
	
	public Account(String username, String password) {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			salt = BCrypt.gensalt();
			hashedPassword = BCrypt.hashpw(password, salt);
			userName = username;
			keyPairGenerator.initialize(2048);
			KeyPair keyPair = keyPairGenerator.genKeyPair();
			String fileBase = Paths.get(System.getProperty("user.home"), "/lockerRoom").toString();

			privateKey = new File(fileBase + "/" + username + ".key");
			publicKey = new File(fileBase + "/" + username + ".pub");

			if(!privateKey.exists()) {
				privateKey.getParentFile().mkdirs();
			}
			
			if(!publicKey.exists()) {
				publicKey.getParentFile().mkdirs();
			}
			
			try (FileOutputStream out = new FileOutputStream(privateKey)) {
				out.write(keyPair.getPrivate().getEncoded());
			}
			try (FileOutputStream out = new FileOutputStream(publicKey)) {
				out.write(keyPair.getPublic().getEncoded());
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getUserName() {
		return userName;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public File getPrivateKey() {
		return privateKey;
	}

	public File getPublicKey() {
		return publicKey;
	}

	public String getSalt() {
		return salt;
	}

	public boolean checkPassword(String password) {
		return hashedPassword.equals(BCrypt.hashpw(password, salt));
	}
}
