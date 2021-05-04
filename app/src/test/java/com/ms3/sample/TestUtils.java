package com.ms3.sample;

import com.ms3.sample.core.address.model.Address;
import com.ms3.sample.core.address.model.AddressDTO;
import com.ms3.sample.core.communication.model.Communication;
import com.ms3.sample.core.communication.model.CommunicationDTO;
import com.ms3.sample.core.contact.model.Contact;
import com.ms3.sample.core.contact.model.ContactDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtils {

	public static void compareContactToDTO(Contact contact, ContactDTO contactDTO) {
		assertEquals(contact.getId(), contactDTO.getId());
		assertEquals(contact.getGender(), contactDTO.getGender());
		assertEquals(contact.getFirstName(), contactDTO.getFirstName());
		assertEquals(contact.getLastName(), contactDTO.getLastName());
		assertEquals(contact.getTitle(), contactDTO.getTitle());
	}

	public static void compareAddressToDTO(Address address, AddressDTO addressDTO) {
		assertEquals(address.getId(), addressDTO.getId());
		assertEquals(address.getStreetNumber(), addressDTO.getStreetNumber());
		assertEquals(address.getStreet(), addressDTO.getStreet());
		assertEquals(address.getUnit(), addressDTO.getUnit());
		assertEquals(address.getCity(), addressDTO.getCity());
		assertEquals(address.getState(), addressDTO.getState());
		assertEquals(address.getType(), addressDTO.getType());
	}

	public static void compareCommunicationToDTO(Communication communication, CommunicationDTO communicationDTO) {
		assertEquals(communication.getId(), communicationDTO.getId());
		assertEquals(communication.getType(), communicationDTO.getType());
		assertEquals(communication.getValue(), communicationDTO.getValue());
		assertEquals(communication.getPreferred(), communicationDTO.getPreferred());
	}
}
