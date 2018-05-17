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
	}
}
