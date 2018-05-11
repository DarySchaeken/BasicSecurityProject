package be.basicsecurity.securevault.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
public class Account {
	@Id
	private String userName;
	private String hashedPassword;
    private String publicKey;
    private String salt;
    
    public Account(String username, String password) {
    	salt = BCrypt.gensalt();
    	hashedPassword = BCrypt.hashpw(password, salt);
    	userName = username;
    }
    
    public String getUserName() {
		return userName;
	}
    
	public String getHashedPassword() {
		return hashedPassword;
	}
	
	public String getPublicKey() {
		return publicKey;
	}
	
	public String getSalt() {
		return salt;
	}
	
	public boolean checkPassword(String password) {
		return hashedPassword.equals(password);
	}
	
}
