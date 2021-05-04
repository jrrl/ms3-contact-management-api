package com.ms3.sample.core.contact;

import com.ms3.sample.core.PageResponse;
import com.ms3.sample.core.contact.model.ContactChangeSet;
import com.ms3.sample.core.contact.model.ContactDTO;

public interface ContactService {
	ContactDTO createContact(ContactDTO newContact);
	PageResponse<ContactDTO> getAllContacts(int page, int size, String sortField, String order);
	ContactDTO getContact(int contactId);
	ContactDTO updateContact(int contactId, ContactChangeSet contactUpdates);
	void deleteContact(int contactId);
}
