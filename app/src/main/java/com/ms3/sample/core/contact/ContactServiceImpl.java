package com.ms3.sample.core.contact;

import com.ms3.sample.core.Util;
import com.ms3.sample.core.address.Address;
import com.ms3.sample.core.address.AddressDTO;
import com.ms3.sample.core.address.AddressRepo;
import com.ms3.sample.core.communication.Communication;
import com.ms3.sample.core.communication.CommunicationDTO;
import com.ms3.sample.core.communication.CommunicationRepo;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {
	private ContactRepo contactRepo;
	private AddressRepo addressRepo;
	private CommunicationRepo communicationRepo;

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
	public List<ContactDTO> getAllContacts() {
		val contacts = contactRepo.findAll();
		return contacts.parallelStream()
			.map(Util::toContactDTO)
			.collect(Collectors.toList());
	}

	@Override
	public ContactDTO getContact(int contactId) {
		val contact = contactRepo.getOne(contactId);
		return Util.toContactDTO(contact);
	}
}
