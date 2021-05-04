package com.ms3.sample.core;

import com.ms3.sample.TestUtils;
import com.ms3.sample.core.address.model.Address;
import com.ms3.sample.core.address.model.AddressDTO;
import com.ms3.sample.core.address.model.AddressType;
import com.ms3.sample.core.communication.model.Communication;
import com.ms3.sample.core.communication.model.CommunicationDTO;
import com.ms3.sample.core.communication.model.CommunicationType;
import com.ms3.sample.core.contact.model.Contact;
import com.ms3.sample.core.contact.model.ContactDTO;
import com.ms3.sample.core.contact.model.Gender;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilTest {
	@Test
	public void testContactToDTO() {
		Address address = Address.builder()
			.id(1)
			.type(AddressType.HOME)
			.streetNumber("1234")
			.street("Road Street")
			.unit("1A")
			.city("New York")
			.state("NY")
			.zipCode("12345")
			.build();

		Communication communication = Communication.builder()
			.id(1)
			.type(CommunicationType.EMAIL)
			.value("test@test.com")
			.build();

		Contact contact = Contact.builder()
			.id(1)
			.dateOfBirth(LocalDate.of(2020, 10, 20))
			.firstName("John")
			.lastName("Doe")
			.gender(Gender.M)
			.title("Manager")
			.addresses(List.of(address))
			.communications(List.of(communication))
			.build();

		ContactDTO contactDTO = Util.toContactDTO(contact);

		TestUtils.compareContactToDTO(contact, contactDTO);
		// address
		assertEquals(contact.getAddresses().size(), contactDTO.getAddresses().size());
		AddressDTO addressDTO = contactDTO.getAddresses().get(0);
		TestUtils.compareAddressToDTO(address, addressDTO);

		// communication
		assertEquals(contact.getCommunications().size(), contactDTO.getCommunications().size());
		CommunicationDTO communicationDTO = contactDTO.getCommunications().get(0);
		TestUtils.compareCommunicationToDTO(communication, communicationDTO);
	}
}
