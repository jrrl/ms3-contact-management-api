package com.ms3.sample.core.contact;

import java.util.List;

public interface ContactService {
	ContactDTO createContact(ContactDTO newContact);
	List<ContactDTO> getAllContacts();
	ContactDTO getContact(int contactId);
}
