package com.ms3.sample.core.communication.model;

import com.ms3.sample.core.contact.model.Contact;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Value
@Builder
public class CommunicationDTO {
	Integer id;
	@NotNull
	CommunicationType type;
	@NotEmpty
	String value;
	@Builder.Default
	Boolean preferred = false;

	public Communication toEntity(Contact contact) {
		return Communication.builder()
			.id(id)
			.value(value)
			.type(type)
			.preferred(preferred)
			.contact(contact)
			.build();
	}
}
