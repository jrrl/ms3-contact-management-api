package com.ms3.sample.core.contact.model;

import com.ms3.sample.core.address.model.AddressDTO;
import com.ms3.sample.core.communication.model.CommunicationDTO;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class ContactDTO {
	Integer id;
	@NotEmpty
	String firstName;
	@NotEmpty
	String lastName;
	@NotNull
	@Past
	LocalDate dateOfBirth;
	Gender gender;
	String title;
	@With
	@Valid
	List<AddressDTO> addresses;
	@NotEmpty
	@With
	@Valid
	List<CommunicationDTO> communications;

	public Contact toEntity() {
		return Contact.builder()
			.id(id)
			.firstName(firstName)
			.lastName(lastName)
			.dateOfBirth(dateOfBirth)
			.gender(gender)
			.title(title)
			.build();
	}
}
