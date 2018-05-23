package be.basicsecurity.securevault.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import be.basicsecurity.securevault.models.Account;
import be.basicsecurity.securevault.repositories.AccountRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/account")
public class AccountsController {
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public boolean create(@RequestBody Map<String, Object> json) {
		String username =  json.get("username").toString();
		String password = json.get("password").toString();
		Account account = new Account(username, password);
		accountRepository.save(account);
		return true;
	}
	
	@PostMapping("/login")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public boolean login(@RequestBody Map<String, Object> json) {
		String username =  json.get("username").toString();
		String password = json.get("password").toString();
		Account account = accountRepository.getOne(username);
		return account.checkPassword(password);
	}

	@GetMapping("/{username}")
	public boolean get(@PathVariable("username") String username) {
		try {
			accountRepository.findById(username).get().getUserName();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@GetMapping
	public String list() {
		try {
			return objectMapper.writeValueAsString(accountRepository.findAll());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "No Accounts!";
		}
	}
}
