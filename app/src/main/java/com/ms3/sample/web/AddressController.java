package com.ms3.sample.web;

import com.ms3.sample.core.PageResponse;
import com.ms3.sample.core.address.AddressService;
import com.ms3.sample.core.address.model.AddressChangeSet;
import com.ms3.sample.core.address.model.AddressDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/v1/contacts/{contactId}/addresses")
@AllArgsConstructor
public class AddressController {
	private final AddressService addressService;

	@PostMapping
	@ResponseBody
	public AddressDTO createAddress(
		@PathVariable Integer contactId,
		@RequestBody @Valid AddressDTO addressDTO
	) {
		return addressService.createAddress(contactId, addressDTO);
	}

	@GetMapping
	@ResponseBody
	public PageResponse<AddressDTO> getAddresses(
		@PathVariable Integer contactId,
		@RequestParam(value = "page", defaultValue = "1") int page,
		@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
		@RequestParam(value = "order", defaultValue = "desc") String order,
		@RequestParam(value = "sortBy", defaultValue = "id") String sortBy
	) {
		return addressService.getAddressForContact(contactId, page, pageSize, sortBy, order);
	}

	@GetMapping("/{addressId}")
	@ResponseBody
	public AddressDTO getAddress(
		@PathVariable Integer contactId,
		@PathVariable Integer addressId
	) {
		return addressService.getAddress(contactId, addressId);
	}

	@RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT}, path = "/{addressId}")
	@ResponseBody
	public AddressDTO updateAddress(
		@PathVariable Integer contactId,
		@PathVariable Integer addressId,
		@RequestBody AddressChangeSet addressChangeSet
	) {
		return addressService.updateAddress(contactId, addressId, addressChangeSet);
	}

	@ExceptionHandler
	public ResponseEntity<String> handleNotFound(NoSuchElementException e) {
		return ResponseEntity.notFound().build();
	}
}
