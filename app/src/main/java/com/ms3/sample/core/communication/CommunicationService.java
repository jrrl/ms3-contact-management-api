package com.ms3.sample.core.communication;

import java.util.List;

public interface CommunicationService {
	CommunicationDTO createCommunication(int contactId, CommunicationDTO newCommunication);
	CommunicationDTO getCommunication(int contactId, int commId);
	CommunicationPage getCommunicationsForContact(int contactId, int page, int size, String sortField, String order);
	CommunicationDTO updateCommunication(int contactId, int commId, CommunicationChangeSet communicationUpdates);
	void deleteCommunication(int contactId, int commId);
}
