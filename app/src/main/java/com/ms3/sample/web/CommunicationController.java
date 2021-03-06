package com.ms3.sample.web;

import com.ms3.sample.core.InvalidRequestException;
import com.ms3.sample.core.PageResponse;
import com.ms3.sample.core.communication.model.CommunicationChangeSet;
import com.ms3.sample.core.communication.model.CommunicationDTO;
import com.ms3.sample.core.communication.CommunicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/v1/contacts/{contactId}/communications")
@AllArgsConstructor
public class CommunicationController {
	private final CommunicationService communicationService;

	@PostMapping
	@ResponseBody
	public CommunicationDTO createCommunication(@PathVariable Integer contactId,
	                                            @RequestBody @Valid CommunicationDTO communicationDTO) {
		return communicationService.createCommunication(contactId, communicationDTO);
	}

	@GetMapping
	@ResponseBody
	public PageResponse<CommunicationDTO> getCommunicationsForContact(
		@PathVariable Integer contactId,
		@RequestParam(value = "page", defaultValue = "1") int page,
		@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
		@RequestParam(value = "order", defaultValue = "desc") String order,
		@RequestParam(value = "sortBy", defaultValue = "id") String sortBy) {
		return communicationService.getCommunicationsForContact(contactId, page, pageSize, sortBy, order);
	}

	@GetMapping("/{commId}")
	@ResponseBody
	public CommunicationDTO getCommunication(
		@PathVariable Integer contactId,
		@PathVariable Integer commId) {
		return communicationService.getCommunication(contactId, commId);
	}

	@RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT}, path = "/{commId}")
	@ResponseBody
	public CommunicationDTO updateCommunication(
		@PathVariable Integer contactId,
		@PathVariable Integer commId,
		@RequestBody CommunicationChangeSet communicationChangeSet) {
		return communicationService.updateCommunication(contactId, commId, communicationChangeSet);
	}

	@DeleteMapping("/{commId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteContent(
		@PathVariable Integer contactId,
		@PathVariable Integer commId) {
		communicationService.deleteCommunication(contactId, commId);
	}

	@ExceptionHandler
	public ResponseEntity<String> handleNotFound(NoSuchElementException e) {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler
	public ResponseEntity<String> handleNotFound(InvalidRequestException e) {
		return ResponseEntity.badRequest().build();
	}
}
