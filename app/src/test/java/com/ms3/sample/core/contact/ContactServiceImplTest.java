package com.ms3.sample.core.contact;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms3.sample.core.address.model.Address;
import com.ms3.sample.core.address.model.AddressDTO;
import com.ms3.sample.core.address.AddressRepo;
import com.ms3.sample.core.address.model.AddressType;
import com.ms3.sample.core.communication.model.Communication;
import com.ms3.sample.core.communication.model.CommunicationDTO;
import com.ms3.sample.core.communication.CommunicationRepo;
import com.ms3.sample.core.communication.model.CommunicationType;
import com.ms3.sample.core.contact.model.Contact;
import com.ms3.sample.core.contact.model.ContactDTO;
import com.ms3.sample.core.contact.model.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ContactServiceImplTest {

	private ContactRepo contactRepo;
	private AddressRepo addressRepo;
	private CommunicationRepo communicationRepo;

	@BeforeEach
	public void setUp() {
		contactRepo = mock(ContactRepo.class);
		addressRepo = mock(AddressRepo.class);
		communicationRepo = mock(CommunicationRepo.class);
	}

	@Test
	public void testCreateContact() {
		AddressDTO addressDTO = AddressDTO.builder()
			.id(1)
			.type(AddressType.HOME)
			.streetNumber("1234")
			.street("Road Street")
			.unit("1A")
			.city("New York")
			.state("NY")
			.zipCode("12345")
			.build();

		CommunicationDTO communicationDTO = CommunicationDTO.builder()
			.id(1)
			.type(CommunicationType.EMAIL)
			.value("test@test.com")
			.build();

		ContactDTO contactDTO = ContactDTO.builder()
			.id(1)
			.dateOfBirth(LocalDate.of(2020, 10, 20))
			.firstName("John")
			.lastName("Doe")
			.gender(Gender.M)
			.title("Manager")
			.addresses(List.of(addressDTO))
			.communications(List.of(communicationDTO))
			.build();

		Contact contact = contactDTO.toEntity();
		Address address = addressDTO.toEntity(contact);
		Communication communication = communicationDTO.toEntity(contact);

		when(contactRepo.save(any(Contact.class))).thenReturn(contact);
		when(addressRepo.saveAll(any())).thenReturn(List.of(address));
		when(communicationRepo.saveAll(any())).thenReturn(List.of(communication));

		ContactService contactService = new ContactServiceImpl(contactRepo, addressRepo, communicationRepo);
		ContactDTO result = contactService.createContact(contactDTO);

		verify(contactRepo, times(1)).save(any(Contact.class));
		verify(addressRepo, times(1)).saveAll(any());
		verify(communicationRepo, times(1)).saveAll(any());

		assertEquals(contactDTO, result);
	}

	@Test
	public void testCreateContact_NoAddress() {
		CommunicationDTO communicationDTO = CommunicationDTO.builder()
			.id(1)
			.type(CommunicationType.EMAIL)
			.value("test@test.com")
			.build();

		ContactDTO contactDTO = ContactDTO.builder()
			.id(1)
			.dateOfBirth(LocalDate.of(2020, 10, 20))
			.firstName("John")
			.lastName("Doe")
			.gender(Gender.M)
			.title("Manager")
			.communications(List.of(communicationDTO))
			.build();

		Contact contact = contactDTO.toEntity();
		Communication communication = communicationDTO.toEntity(contact);

		when(contactRepo.save(any(Contact.class))).thenReturn(contact);
		when(communicationRepo.saveAll(any())).thenReturn(List.of(communication));

		ContactService contactService = new ContactServiceImpl(contactRepo, addressRepo, communicationRepo);
		ContactDTO result = contactService.createContact(contactDTO);

		verify(contactRepo, times(1)).save(any(Contact.class));
		verify(addressRepo, times(0)).saveAll(any());
		verify(communicationRepo, times(1)).saveAll(any());

		assertEquals(contactDTO, result);
	}

	@Test
	public void testGetContact() {
		CommunicationDTO communicationDTO = CommunicationDTO.builder()
			.id(1)
			.type(CommunicationType.EMAIL)
			.value("test@test.com")
			.build();

		ContactDTO contactDTO = ContactDTO.builder()
			.id(1)
			.dateOfBirth(LocalDate.of(2020, 10, 20))
			.firstName("John")
			.lastName("Doe")
			.gender(Gender.M)
			.title("Manager")
			.communications(List.of(communicationDTO))
			.build();

		Contact contact = contactDTO.toEntity();
		contact.setCommunications(List.of(communicationDTO.toEntity(contact)));

		when(contactRepo.existsById(eq(1))).thenReturn(true);
		when(contactRepo.getOne(eq(1))).thenReturn(contact);

		ContactService contactService = new ContactServiceImpl(contactRepo, addressRepo, communicationRepo);
		ContactDTO result = contactService.getContact(1);

		assertEquals(contactDTO, result);
	}

	@Test
	public void testGetContact_NonExistent() {
		CommunicationDTO communicationDTO = CommunicationDTO.builder()
			.id(1)
			.type(CommunicationType.EMAIL)
			.value("test@test.com")
			.build();

		ContactDTO contactDTO = ContactDTO.builder()
			.id(1)
			.dateOfBirth(LocalDate.of(2020, 10, 20))
			.firstName("John")
			.lastName("Doe")
			.gender(Gender.M)
			.title("Manager")
			.communications(List.of(communicationDTO))
			.build();

		Contact contact = contactDTO.toEntity();
		contact.setCommunications(List.of(communicationDTO.toEntity(contact)));

		when(contactRepo.existsById(eq(1))).thenReturn(false);
		when(contactRepo.getOne(eq(1))).thenReturn(contact);

		ContactService contactService = new ContactServiceImpl(contactRepo, addressRepo, communicationRepo);

		try {
			contactService.getContact(1);
			fail("NoSuchElementException expected");
		}
		catch(Exception e) {
			assertTrue(e instanceof NoSuchElementException);
		}
	}
}
