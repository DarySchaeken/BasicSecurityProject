package be.basicsecurity.securevault.models;

import java.io.File;
import java.nio.file.Paths;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

// SOURCES: http://www.novixys.com/blog/rsa-file-encryption-decryption-java/
// SOURCES: http://www.novixys.com/blog/using-aes-rsa-file-encryption-decryption-java/


@Entity
public class Message {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private Account sender;
	@ManyToOne
	private Account receiver;
	@Lob
	private File encryptedMessage; // Encrypted with des key.
	@Lob
	private File encryptedHash; // Encrypted with private key of sender.
	@Lob
	private File encryptedDesKey; // Encrypted with public key of receiver.
	
	public Message (Account sender, Account receiver, File message) {
		Random random = new Random();
		String fileBase = Paths.get(System.getProperty("user.home"), "/messageCenter").toString();
		encryptedMessage = new File(fileBase + "/" + random.nextLong() + ".message");
		if(!encryptedMessage.getParentFile().exists()) {
			encryptedMessage.getParentFile().mkdirs();
		}
		encryptedDesKey = new File(fileBase + "/" + random.nextLong() + ".key");
	}
	
	
	public long getId() {
		return id;
	}
	
	public Account getSender() {
		return sender;
	}
	
	public Account getReceiver() {
		return receiver;
	}
	
	public File getEncryptedMessage() {
		return encryptedMessage;
	}
	
	public File getEncryptedHash() {
		return encryptedHash;
	}
	
	public File getEncryptedDesKey() {
		return encryptedDesKey;
	}
}
