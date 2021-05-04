package com.ms3.sample.core.contact;

import com.ms3.sample.core.contact.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepo extends JpaRepository<Contact, Integer> {
}
