package com.ms3.sample.core.contact;

import com.ms3.sample.core.address.Address;
import com.ms3.sample.core.communication.Communication;
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
	@OneToMany(mappedBy = "contact")
	List<Address> addresses;
	@OneToMany(mappedBy = "contact")
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
