package com.ms3.sample.web;

import com.ms3.sample.core.contact.ContactDTO;
import com.ms3.sample.core.contact.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/contacts")
@AllArgsConstructor
public class WebController {
	private ContactService contactService;

	@PostMapping
	@ResponseBody
	public ContactDTO createContact(@RequestBody ContactDTO contactDTO) {
		return contactService.createContact(contactDTO);
	}

	@GetMapping("/{contactId}")
	@ResponseBody
	public ContactDTO getContact(@PathVariable Integer contactId) {
		return contactService.getContact(contactId);
	}
}
