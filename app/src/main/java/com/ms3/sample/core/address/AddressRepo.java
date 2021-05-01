package com.ms3.sample.core.address;

import com.ms3.sample.core.contact.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepo extends JpaRepository<Address, Integer> {
	List<Address> findByContact(Contact contact);
}
