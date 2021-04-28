package com.ms3.sample.core.communication;

import com.ms3.sample.core.contact.Contact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Communication {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	Integer id;
	@Column(nullable = false, updatable = false)
	CommunicationType type;
	@Column(nullable = false)
	String value;
	@Column(nullable = false)
	Boolean preferred;
	@ManyToOne
	@JoinColumn(nullable = false)
	Contact contact;

	public CommunicationDTO toDTO() {
		return CommunicationDTO.builder()
			.id(id)
			.value(value)
			.type(type)
			.preferred(preferred)
			.build();
	}
}
