package com.ms3.sample.core.communication;

import com.ms3.sample.core.communication.model.Communication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunicationRepo extends JpaRepository<Communication, Integer> {
	List<Communication> findByContactId(int contactId);
	Page<Communication> findByContactId(int contactId, Pageable pageable);
	Integer countByContactId(int contactId);
}
