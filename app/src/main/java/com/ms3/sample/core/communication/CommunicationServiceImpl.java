package com.ms3.sample.core.communication;

import com.ms3.sample.core.PageResponse;
import com.ms3.sample.core.Pagination;
import com.ms3.sample.core.communication.model.Communication;
import com.ms3.sample.core.communication.model.CommunicationChangeSet;
import com.ms3.sample.core.communication.model.CommunicationDTO;
import com.ms3.sample.core.contact.ContactRepo;
import lombok.AllArgsConstructor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class CommunicationServiceImpl implements CommunicationService {
	private static final Logger logger = LoggerFactory.getLogger(CommunicationServiceImpl.class);

	private CommunicationRepo communicationRepo;
	private ContactRepo contactRepo;


	@Override
	public CommunicationDTO createCommunication(int contactId, CommunicationDTO newCommunication) {
		if(!contactRepo.existsById(contactId)) {
			throw new NoSuchElementException("Contact does not exist");
		}

		val contact = contactRepo.getOne(contactId);
		val communication = newCommunication.toEntity(contact);
		val savedCommunication = communicationRepo.saveAndFlush(communication);
		return savedCommunication.toDTO();
	}

	@Override
	public CommunicationDTO getCommunication(int contactId, int commId) {
		if(!contactRepo.existsById(contactId)) {
			throw new NoSuchElementException("Contact does not exist");
		}

		if(!communicationRepo.existsById(commId)) {
			throw new NoSuchElementException("Communication does not exist");
		}

		val communication = communicationRepo.getOne(commId);
		return communication.toDTO();
	}

	@Override
	public PageResponse<CommunicationDTO> getCommunicationsForContact(int contactId, int page, int size, String sortField, String order) {
		if(!contactRepo.existsById(contactId)) {
			throw new NoSuchElementException("Contact does not exist");
		}

		val orderDirection = order == null || order.isEmpty() || order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		val pageable = PageRequest.of(page - 1, size, Sort.by(orderDirection, sortField));
		val communication = communicationRepo.findByContactId(contactId, pageable);
		val pagination = Pagination.builder()
			.page(page)
			.count(communication.getNumberOfElements())
			.totalCount((int)communication.getTotalElements())
			.build();

		return new PageResponse<>(
			pagination,
			communication.get()
				.map(Communication::toDTO)
				.collect(Collectors.toList())
		);
	}

	@Override
	public CommunicationDTO updateCommunication(int contactId, int commId, CommunicationChangeSet communicationUpdates) {
		logger.info("Updating communication={} for contact={}: {}", commId, contactId, communicationUpdates);
		if(!contactRepo.existsById(contactId)) {
			throw new NoSuchElementException("Contact does not exist");
		}
		if(!communicationRepo.existsById(commId)) {
			throw new NoSuchElementException("Communication does not exist");
		}

		val currentCommunication = communicationRepo.getOne(commId);
		if(communicationUpdates.getValue() != null && !communicationUpdates.getValue().isEmpty()) {
			currentCommunication.setValue(communicationUpdates.getValue());
		}
		if(communicationUpdates.getPreferred() != null) {
			currentCommunication.setPreferred(communicationUpdates.getPreferred());
		}
		return currentCommunication.toDTO();
	}

	@Override
	public void deleteCommunication(int contactId, int commId) {
		if(!contactRepo.existsById(contactId)) {
			throw new NoSuchElementException("Contact does not exist");
		}
		if(!communicationRepo.existsById(commId)) {
			throw new NoSuchElementException("Communication does not exist");
		}
		communicationRepo.deleteById(commId);
	}
}
