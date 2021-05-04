package com.ms3.sample.core.contact.model;

import lombok.Value;

import java.time.LocalDate;

@Value
public class ContactChangeSet {
	String firstName;
	String lastName;
	LocalDate dateOfBirth;
	Gender gender;
	String title;
}
