package com.ms3.sample.core.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms3.sample.core.PageResponse;
import com.ms3.sample.core.Pagination;
import com.ms3.sample.core.Util;
import com.ms3.sample.core.address.model.Address;
import com.ms3.sample.core.address.model.AddressChangeSet;
import com.ms3.sample.core.address.model.AddressDTO;
import com.ms3.sample.core.contact.ContactRepo;
import lombok.AllArgsConstructor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
@Transactional
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
	private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

	private final ContactRepo contactRepo;
	private final AddressRepo addressRepo;
	private final ObjectMapper objectMapper;

	@Override
	public AddressDTO createAddress(int contactId, AddressDTO addressDTO) {
		if(!contactRepo.existsById(contactId)) {
			throw new NoSuchElementException("Contact does not exist");
		}

		val contact = contactRepo.getOne(contactId);
		val addressToCreate = addressDTO.toEntity(contact);

		return addressRepo.saveAndFlush(addressToCreate).toDTO();
	}

	@Override
	public AddressDTO updateAddress(int contactId, int addressId, AddressChangeSet addressDTO) {
		if(!contactRepo.existsById(contactId)) {
			throw new NoSuchElementException("Contact does not exist");
		}
		if(!addressRepo.existsById(addressId)) {
			throw new NoSuchElementException("Address does not exist");
		}

		var address = addressRepo.getOne(addressId);
		val contact = address.getContact();

		var addressMap = objectMapper.convertValue(address.toDTO(), Map.class);
		val changeSetMap = objectMapper.convertValue(addressDTO, Map.class);
		addressMap = Util.mergeUpdatesToTarget(addressMap, changeSetMap);

		address = objectMapper.convertValue(addressMap, Address.class);
		address.setContact(contact);
		val updatedAddress = addressRepo.saveAndFlush(address);

		return updatedAddress.toDTO();
	}

	@Override
	public void deleteAddress(int contactId, int addressId) {
		if(!contactRepo.existsById(contactId)) {
			throw new NoSuchElementException("Contact does not exist");
		}
		if(!addressRepo.existsById(addressId)) {
			throw new NoSuchElementException("Address does not exist");
		}
		addressRepo.deleteById(addressId);
	}

	@Override
	public AddressDTO getAddress(int contactId, int addressId) {
		if(!contactRepo.existsById(contactId)) {
			throw new NoSuchElementException("Contact does not exist");
		}
		if(!addressRepo.existsById(addressId)) {
			throw new NoSuchElementException("Address does not exist");
		}
		val address = addressRepo.getOne(addressId);
		return address.toDTO();
	}

	@Override
	public PageResponse<AddressDTO> getAddressForContact(int contactId, int page, int size, String sortField, String order) {
		if(!contactRepo.existsById(contactId)) {
			throw new NoSuchElementException("Contact does not exist");
		}
		val orderDirection = order == null || order.isEmpty() || order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		val addresses = addressRepo.findByContactId(contactId, PageRequest.of(page - 1, size, Sort.by(orderDirection, sortField)));
		val pagination = Pagination.builder()
			.page(page)
			.count(addresses.getNumberOfElements())
			.totalCount((int)addresses.getTotalElements())
			.build();
		return new PageResponse<>(
			pagination,
			addresses.get()
				.map(Address::toDTO)
				.collect(Collectors.toList())
		);
	}
}
