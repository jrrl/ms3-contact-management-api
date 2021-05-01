package com.ms3.sample.core.contact;

import java.util.List;
import java.util.NoSuchElementException;

public interface ContactService {
	ContactDTO createContact(ContactDTO newContact);
	ContactPage getAllContacts(int page, int size, String sortField, String order);
	ContactDTO getContact(int contactId);
	ContactDTO updateContact(int contactId, ContactChangeSet contactUpdates);
	void deleteContact(int contactId);
}
