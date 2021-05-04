package com.ms3.sample.core.address.model;

import com.ms3.sample.core.contact.model.Contact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	Integer id;
	@Column(nullable = false)
	AddressType type;
	@Column(nullable = false)
	String streetNumber;
	@Column(nullable = false)
	String street;
	@Column
	String unit;
	@Column(nullable = false)
	String city;
	@Column(nullable = false)
	String state;
	@Column(nullable = false)
	String zipCode;
	@ManyToOne
	@JoinColumn
	Contact contact;

	public AddressDTO toDTO() {
		return AddressDTO.builder()
			.id(id)
			.type(type)
			.streetNumber(streetNumber)
			.street(street)
			.unit(unit)
			.city(city)
			.state(state)
			.zipCode(zipCode)
			.build();
	}
}
