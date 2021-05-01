package com.ms3.sample.core.communication;

import com.ms3.sample.core.communication.model.CommunicationChangeSet;
import com.ms3.sample.core.communication.model.CommunicationDTO;
import com.ms3.sample.core.communication.model.CommunicationPage;

public interface CommunicationService {
	CommunicationDTO createCommunication(int contactId, CommunicationDTO newCommunication);
	CommunicationDTO getCommunication(int contactId, int commId);
	CommunicationPage getCommunicationsForContact(int contactId, int page, int size, String sortField, String order);
	CommunicationDTO updateCommunication(int contactId, int commId, CommunicationChangeSet communicationUpdates);
	void deleteCommunication(int contactId, int commId);
}
