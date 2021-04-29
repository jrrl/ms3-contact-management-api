package com.ms3.sample.core;

import com.ms3.sample.core.address.Address;
import com.ms3.sample.core.address.AddressDTO;
import com.ms3.sample.core.communication.Communication;
import com.ms3.sample.core.communication.CommunicationDTO;
import com.ms3.sample.core.contact.Contact;
import com.ms3.sample.core.contact.ContactDTO;
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
