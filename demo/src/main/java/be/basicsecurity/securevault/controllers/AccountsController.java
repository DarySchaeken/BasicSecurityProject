package be.basicsecurity.securevault.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
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
	public String create(@RequestBody Map<String, Object> json) {
		String username =  json.get("username").toString();
		String password = json.get("password").toString();
		Account account = new Account(username, password);
		accountRepository.save(account);
		return "Created";
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
	public String get(@PathVariable("username") String username) {
		try {
			return accountRepository.findById(username).get().getUserName();
		} catch (Exception e) {
			e.printStackTrace();
			return "NOT FOUND!";
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
