package be.basicsecurity.securevault.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import be.basicsecurity.securevault.models.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
