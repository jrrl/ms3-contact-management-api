package com.ms3.sample.core.communication;

import com.ms3.sample.core.contact.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunicationRepo extends JpaRepository<Communication, Integer> {
	List<Communication> findByContact(Contact contact);
}
