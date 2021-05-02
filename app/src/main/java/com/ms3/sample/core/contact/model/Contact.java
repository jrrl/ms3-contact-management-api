package com.ms3.sample.core.contact.model;

import com.ms3.sample.core.address.model.Address;
import com.ms3.sample.core.communication.model.Communication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	Integer id;
	@Column(nullable = false)
	String firstName;
	@Column(nullable = false)
	String lastName;
	@Column(nullable = false)
	LocalDate dateOfBirth;
	@Column
	Gender gender;
	@Column
	String title;
	@OneToMany(mappedBy = "contact", orphanRemoval = true)
	List<Address> addresses;
	@OneToMany(mappedBy = "contact", orphanRemoval = true)
	List<Communication> communications;

	public ContactDTO toDTO() {
		return ContactDTO.builder()
			.id(id)
			.firstName(firstName)
			.lastName(lastName)
			.dateOfBirth(dateOfBirth)
			.gender(gender)
			.title(title)
			.build();
	}
}
