package com.ms3.sample.core.contact;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms3.sample.core.PageResponse;
import com.ms3.sample.core.Pagination;
import com.ms3.sample.core.Util;
import com.ms3.sample.core.address.model.AddressDTO;
import com.ms3.sample.core.address.AddressRepo;
import com.ms3.sample.core.communication.CommunicationRepo;
import com.ms3.sample.core.contact.model.Contact;
import com.ms3.sample.core.contact.model.ContactChangeSet;
import com.ms3.sample.core.contact.model.ContactDTO;
import lombok.AllArgsConstructor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {
	private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

	private final ContactRepo contactRepo;
	private final AddressRepo addressRepo;
	private final CommunicationRepo communicationRepo;

	@Override
	public ContactDTO createContact(ContactDTO newContact) {
		val contact = contactRepo.save(newContact.toEntity());
		val communicationsToSave = newContact.getCommunications()
				.parallelStream()
				.map(communicationDTO -> communicationDTO.toEntity(contact))
				.collect(Collectors.toList());
		val communications = Util.convertCommListToDTO(communicationRepo.saveAll(communicationsToSave));

		List<AddressDTO> addresses = null;
		if(newContact.getAddresses() != null && !newContact.getAddresses().isEmpty()) {
			val addressesToSave = newContact.getAddresses()
				.parallelStream()
				.map(addressDTO -> addressDTO.toEntity(contact))
				.collect(Collectors.toList());
			addresses = Util.convertAddressListToDTO(addressRepo.saveAll(addressesToSave));
		}

		return contact.toDTO()
			.withAddresses(addresses)
			.withCommunications(communications);
	}

	@Override
	public PageResponse<ContactDTO> getAllContacts(int page, int size, String sortField, String order) {
		val orderDirection = order == null || order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

		val contacts = contactRepo.findAll(PageRequest.of(page - 1, size, Sort.by(orderDirection, sortField)));
		val pagination = Pagination.builder()
			.page(page)
			.count(contacts.getNumberOfElements())
			.totalCount((int) contacts.getTotalElements())
			.build();
		val results =  contacts.get()
			.map(Util::toContactDTO)
			.collect(Collectors.toList());
		return new PageResponse<>(pagination, results);
	}

	@Override
	public ContactDTO getContact(int contactId) {
		if(!contactRepo.existsById(contactId)) {
			throw new NoSuchElementException("Contact does not exist");
		}

		val contact = contactRepo.getOne(contactId);
		return Util.toContactDTO(contact);
	}

	@Override
	public ContactDTO updateContact(int contactId, ContactChangeSet contactUpdates) {
		if(!contactRepo.existsById(contactId)) {
			throw new NoSuchElementException("Contact does not exist");
		}

		val contact = contactRepo.getOne(contactId);
		if(contactUpdates.getFirstName() != null && !contactUpdates.getFirstName().isBlank()) {
			contact.setFirstName(contactUpdates.getFirstName());
		}
		if(contactUpdates.getLastName() != null && !contactUpdates.getLastName().isBlank()) {
			contact.setLastName(contactUpdates.getLastName());
		}
		if(contactUpdates.getDateOfBirth() != null) {
			contact.setDateOfBirth(contactUpdates.getDateOfBirth());
		}
		if(contactUpdates.getGender() != null) {
			contact.setGender(contactUpdates.getGender());
		}
		if(contactUpdates.getTitle() != null) {
			contact.setTitle(contactUpdates.getTitle());
		}

		return Util.toContactDTO(contactRepo.saveAndFlush(contact));
	}

	@Override
	public void deleteContact(int contactId) {
		if(!contactRepo.existsById(contactId)) {
			throw new NoSuchElementException("Contact does not exist");
		}
		contactRepo.deleteById(contactId);
	}
}
