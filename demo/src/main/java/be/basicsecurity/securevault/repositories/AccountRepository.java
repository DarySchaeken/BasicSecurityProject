package be.basicsecurity.securevault.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import be.basicsecurity.securevault.models.Account;

public interface AccountRepository extends JpaRepository<Account, String> {

}
