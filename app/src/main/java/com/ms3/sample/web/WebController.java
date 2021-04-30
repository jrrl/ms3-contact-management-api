package com.ms3.sample.web;

import com.ms3.sample.core.contact.ContactChangeSet;
import com.ms3.sample.core.contact.ContactDTO;
import com.ms3.sample.core.contact.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/contacts")
@AllArgsConstructor
public class WebController {
	private final ContactService contactService;

	@PostMapping
	@ResponseBody
	public ContactDTO createContact(@RequestBody @Valid ContactDTO contactDTO) {
		return contactService.createContact(contactDTO);
	}

	@GetMapping
	@ResponseBody
	public List<ContactDTO> getContacts() {
		return contactService.getAllContacts();
	}

	@GetMapping("/{contactId}")
	@ResponseBody
	public ContactDTO getContact(@PathVariable Integer contactId) {
		return contactService.getContact(contactId);
	}

	@PatchMapping("/{contactId}")
	@PutMapping("/{contactId}")
	@ResponseBody
	public ContactDTO updateContact(@RequestBody ContactChangeSet changeSet, @PathVariable Integer contactId) {
		return contactService.updateContact(contactId, changeSet);
	}

	@DeleteMapping("/{contactId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteContact(@PathVariable Integer contactId) {
		contactService.deleteContact(contactId);
	}
}
