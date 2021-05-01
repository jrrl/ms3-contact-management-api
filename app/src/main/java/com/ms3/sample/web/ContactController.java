package com.ms3.sample.web;

import com.ms3.sample.core.contact.model.ContactChangeSet;
import com.ms3.sample.core.contact.model.ContactDTO;
import com.ms3.sample.core.contact.model.ContactPage;
import com.ms3.sample.core.contact.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/v1/contacts")
@AllArgsConstructor
public class ContactController {
	private final ContactService contactService;

	@PostMapping
	@ResponseBody
	public ContactDTO createContact(@RequestBody @Valid ContactDTO contactDTO) {
		return contactService.createContact(contactDTO);
	}

	@GetMapping
	@ResponseBody
	public ContactPage getContacts(
		@RequestParam(value = "page", defaultValue = "1") int page,
		@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
		@RequestParam(value = "order", defaultValue = "desc") String order,
		@RequestParam(value = "sortBy", defaultValue = "id") String sortBy) {
		return contactService.getAllContacts(page, pageSize, sortBy, order);
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

	@ExceptionHandler
	public ResponseEntity<String> handleNotFound(NoSuchElementException e) {
		return ResponseEntity.notFound().build();
	}
}
