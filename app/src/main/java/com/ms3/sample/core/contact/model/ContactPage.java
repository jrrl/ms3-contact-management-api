package com.ms3.sample.core.contact.model;

import com.ms3.sample.core.Pagination;
import lombok.Value;

import java.util.List;

@Value
public class ContactPage {
	Pagination pagination;
	List<ContactDTO> contacts;
}
