package be.basicsecurity.securevault;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import be.basicsecurity.securevault.models.Account;
import be.basicsecurity.securevault.models.Message;

@SpringBootApplication
public class SecureVaultApplication {	
	public static void main(String[] args) {
		SpringApplication.run(SecureVaultApplication.class, args);
		
		// Message Test Code!
		Account account1 = new Account("Dary", "Schaeken");
		Account account2 = new Account("Lotte", "Stockmans");
		// Replace path with a file on your own pc =>
		// Dary : "C:\\Users\\Dary\\Desktop\\test.txt"
		File message = new File("C:\\Users\\Dary\\Desktop\\test.txt");
		Message message1 = new Message(account1, account2, message);
		Message.decryptMessage(message1);
	}
}
