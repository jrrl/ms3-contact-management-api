package com.ms3.sample.core.address;

import com.ms3.sample.core.contact.model.Contact;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
@Builder
public class AddressDTO {
	Integer id;
	@NotNull
	AddressType type;
	@NotEmpty
	String streetNumber;
	@NotEmpty
	String street;
	String unit;
	@NotEmpty
	String city;
	@NotEmpty
	@Pattern(regexp = "^[A-Z]{2}$")
	String state;
	@NotEmpty
	@Pattern(regexp = "^\\d{5}(?:[-\\s]\\d{4})?$")
	String zipCode;

	public Address toEntity(Contact contact) {
		return Address.builder()
			.id(id)
			.type(type)
			.streetNumber(streetNumber)
			.street(street)
			.unit(unit)
			.city(city)
			.state(state)
			.zipCode(zipCode)
			.contact(contact)
			.build();
	}
}
