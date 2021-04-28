package com.ms3.sample.core.contact;

import com.ms3.sample.core.address.Address;
import com.ms3.sample.core.address.AddressDTO;
import com.ms3.sample.core.address.AddressRepo;
import com.ms3.sample.core.communication.Communication;
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
	public ContactDTO createContact(@Valid  ContactDTO newContact) {


		val contact = contactRepo.save(newContact.toEntity());
		val communicationsToSave = newContact.getCommunications()
				.parallelStream()
				.map(communicationDTO -> communicationDTO.toEntity(contact))
				.collect(Collectors.toList());
		val communications = communicationRepo.saveAll(communicationsToSave)
			.parallelStream()
			.map(Communication::toDTO)
			.collect(Collectors.toList());

		List<AddressDTO> addresses = null;
		if(newContact.getAddresses() != null && !newContact.getAddresses().isEmpty()) {
			val addressesToSave = newContact.getAddresses()
				.parallelStream()
				.map(addressDTO -> addressDTO.toEntity(contact))
				.collect(Collectors.toList());
			addresses = addressRepo.saveAll(addressesToSave)
				.parallelStream()
				.map(Address::toDTO)
				.collect(Collectors.toList());
		}

		return contact.toDTO()
			.withAddresses(addresses)
			.withCommunications(communications);
	}

	@Override
	public List<ContactDTO> getAllContacts() {
		return null;
	}

	@Override
	public ContactDTO getContact(int contactId) {
		val contact = contactRepo.getOne(contactId);
		var contactDto = contact.toDTO();
		if(contact.getCommunications() != null && !contact.getCommunications().isEmpty()) {
			val communications = contact.getCommunications()
				.parallelStream()
				.map(Communication::toDTO)
				.collect(Collectors.toList());
			contactDto = contactDto.withCommunications(communications);
		}
		if(contact.getAddresses() != null && !contact.getAddresses().isEmpty()) {
			val addresses = contact.getAddresses()
				.parallelStream()
				.map(Address::toDTO)
				.collect(Collectors.toList());
			contactDto = contactDto.withAddresses(addresses);
		}
		return contactDto;
	}
}
