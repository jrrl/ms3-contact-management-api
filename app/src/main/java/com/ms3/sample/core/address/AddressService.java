package com.ms3.sample.core.address;

import com.ms3.sample.core.PageResponse;
import com.ms3.sample.core.address.model.AddressChangeSet;
import com.ms3.sample.core.address.model.AddressDTO;

public interface AddressService {
	AddressDTO createAddress(int contactId, AddressDTO addressDTO);
	AddressDTO updateAddress(int contactId, int addressId, AddressChangeSet addressDTO);
	void deleteAddress(int contactId, int addressId);
	AddressDTO getAddress(int contactId, int addressId);
	PageResponse<AddressDTO> getAddressForContact(int contactId, int page, int size, String sortField, String order);
}
