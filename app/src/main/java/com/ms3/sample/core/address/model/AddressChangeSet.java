package com.ms3.sample.core.address.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Pattern;

@Value
@Builder
public class AddressChangeSet {
	AddressType type;
	String streetNumber;
	String street;
	String unit;
	String city;
	@Pattern(regexp = "^[A-Z]{2}$")
	String state;
	@Pattern(regexp = "^\\d{5}(?:[-\\s]\\d{4})?$")
	String zipCode;
}
