package com.ms3.sample.core;

import com.ms3.sample.core.address.model.Address;
import com.ms3.sample.core.address.model.AddressDTO;
import com.ms3.sample.core.communication.model.Communication;
import com.ms3.sample.core.communication.model.CommunicationDTO;
import com.ms3.sample.core.contact.model.Contact;
import com.ms3.sample.core.contact.model.ContactDTO;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;

public class Util {
	public static ContactDTO toContactDTO(Contact contact) {
		var contactDto = contact.toDTO();
		if(contact.getCommunications() != null && !contact.getCommunications().isEmpty()) {
			val communications = convertCommListToDTO(contact.getCommunications());
			contactDto = contactDto.withCommunications(communications);
		}
		if(contact.getAddresses() != null && !contact.getAddresses().isEmpty()) {
			val addresses = convertAddressListToDTO(contact.getAddresses());
			contactDto = contactDto.withAddresses(addresses);
		}
		return contactDto;
	};

	public static List<CommunicationDTO> convertCommListToDTO(List<Communication> communications) {
		return communications.parallelStream()
			.map(Communication::toDTO)
			.collect(Collectors.toList());
	};

	public static List<AddressDTO> convertAddressListToDTO(List<Address> addresses) {
		return addresses.parallelStream()
			.map(Address::toDTO)
			.collect(Collectors.toList());
	};
}
