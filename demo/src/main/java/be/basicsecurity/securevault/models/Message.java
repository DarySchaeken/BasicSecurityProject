package be.basicsecurity.securevault.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

// SOURCES: http://www.novixys.com/blog/rsa-file-encryption-decryption-java/
// SOURCES: http://www.novixys.com/blog/using-aes-rsa-file-encryption-decryption-java/

@Entity
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private Account sender;
	@ManyToOne
	private Account receiver;
	@Lob
	private File encryptedMessage;
	@Lob
	private byte[] encryptedHash;
	@Transient
	private byte[] signature;

	static SecureRandom srandom = new SecureRandom();

	public static File decryptMessage(Message message) {
		try {
			byte[] bytesPublicKey = Files.readAllBytes(message.getSender().getPublicKey().toPath());
			X509EncodedKeySpec ks = new X509EncodedKeySpec(bytesPublicKey);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			PublicKey pub = kf.generatePublic(ks);

			byte[] bytesPrivateKey = Files.readAllBytes(message.getReceiver().getPrivateKey().toPath());
			PKCS8EncodedKeySpec privks = new PKCS8EncodedKeySpec(bytesPrivateKey);
			KeyFactory privkf = KeyFactory.getInstance("RSA");
			PrivateKey pvt = privkf.generatePrivate(privks);

			FileInputStream in = new FileInputStream(message.getEncryptedMessage());
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, pvt);
			byte[] b = new byte[256];
			in.read(b);
			byte[] keyb = cipher.doFinal(b);
			SecretKeySpec skey = new SecretKeySpec(keyb, "AES");

			byte[] iv = new byte[128 / 8];
			in.read(iv);
			IvParameterSpec ivspec = new IvParameterSpec(iv);

			String fileBase = Paths.get(System.getProperty("user.home"), "/decryptedIsBetter").toString();
			File decryptedMessage = new File(fileBase + "/" + message.getEncryptedMessage().getName() + ".txt");
			if (!decryptedMessage.getParentFile().exists()) {
				decryptedMessage.getParentFile().mkdirs();
			}

			Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
			ci.init(Cipher.DECRYPT_MODE, skey, ivspec);
			try (FileOutputStream out = new FileOutputStream(decryptedMessage)) {
				processFile(ci, in, out);
			}

			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(Files.readAllBytes(decryptedMessage.toPath()));
			byte[] testSignature = messageDigest.digest();
			Cipher signCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			signCipher.init(Cipher.DECRYPT_MODE, pub);
			byte[] decryptedSignature = signCipher.doFinal(message.encryptedHash);
			

			if (Arrays.equals(decryptedSignature, testSignature)) {
				System.out.println("ok!");
				return decryptedMessage;
			} else {
				System.out.println("Someone tried to fool you!");
			}
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Message(Account sender, Account receiver, File message) {
		try {
			this.receiver = receiver;
			this.sender = sender;
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128);
			SecretKey secretKey = keyGenerator.generateKey();

			byte[] iv = new byte[128 / 8];
			srandom.nextBytes(iv);
			IvParameterSpec ivspec = new IvParameterSpec(iv);

			Random random = new Random();
			String fileBase = Paths.get(System.getProperty("user.home"), "/messageCenter").toString();
			encryptedMessage = new File(fileBase + "/" + random.nextLong() + ".message");
			if (!encryptedMessage.getParentFile().exists()) {
				encryptedMessage.getParentFile().mkdirs();
			}
			byte[] bytesPublicKey = Files.readAllBytes(receiver.getPublicKey().toPath());
			X509EncodedKeySpec ks = new X509EncodedKeySpec(bytesPublicKey);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			PublicKey pub = kf.generatePublic(ks);

			byte[] bytesPrivateKey = Files.readAllBytes(sender.getPrivateKey().toPath());
			PKCS8EncodedKeySpec privks = new PKCS8EncodedKeySpec(bytesPrivateKey);
			KeyFactory privkf = KeyFactory.getInstance("RSA");
			PrivateKey pvt = privkf.generatePrivate(privks);

			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(Files.readAllBytes(message.toPath()));
			signature = messageDigest.digest();

			Cipher signCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			signCipher.init(Cipher.ENCRYPT_MODE, pvt);
			encryptedHash = signCipher.doFinal(signature);

			FileOutputStream outputStream = new FileOutputStream(encryptedMessage);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, pub);
			byte[] b = cipher.doFinal(secretKey.getEncoded());
			outputStream.write(b);

			outputStream.write(iv);

			Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
			ci.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			try (FileInputStream in = new FileInputStream(message)) {
				processFile(ci, in, outputStream);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
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

	public byte[] getEncryptedHash() {
		return encryptedHash;
	}

	static private void processFile(Cipher ci, InputStream in, OutputStream out)
			throws javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException, java.io.IOException {
		byte[] ibuf = new byte[1024];
		int len;
		while ((len = in.read(ibuf)) != -1) {
			byte[] obuf = ci.update(ibuf, 0, len);
			if (obuf != null)
				out.write(obuf);
		}
		byte[] obuf = ci.doFinal();
		if (obuf != null)
			out.write(obuf);
	}
}
