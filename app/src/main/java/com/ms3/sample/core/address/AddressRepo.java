package com.ms3.sample.core.address;

import com.ms3.sample.core.address.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Integer> {
	Page<Address> findByContactId(int contactId, Pageable pageable);
}
