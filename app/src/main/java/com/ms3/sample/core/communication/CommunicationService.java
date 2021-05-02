package com.ms3.sample.core.communication;

import com.ms3.sample.core.PageResponse;
import com.ms3.sample.core.communication.model.CommunicationChangeSet;
import com.ms3.sample.core.communication.model.CommunicationDTO;

public interface CommunicationService {
	CommunicationDTO createCommunication(int contactId, CommunicationDTO newCommunication);
	CommunicationDTO getCommunication(int contactId, int commId);
	PageResponse<CommunicationDTO> getCommunicationsForContact(int contactId, int page, int size, String sortField, String order);
	CommunicationDTO updateCommunication(int contactId, int commId, CommunicationChangeSet communicationUpdates);
	void deleteCommunication(int contactId, int commId);
}
