package be.basicsecurity.securevault.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import be.basicsecurity.securevault.models.Account;
import be.basicsecurity.securevault.repositories.AccountRepository;

@RestController
@RequestMapping("/account")
public class AccountsController {
	@Autowired
	private AccountRepository accountRepository;
	
	@GetMapping
	public List<Account> list(){
		return accountRepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody Account account) {
		accountRepository.save(account);
	}
	
	@GetMapping("/{username}")
	public Account get(@PathVariable("username") String username) {
		return accountRepository.getOne(username);
	}
}
