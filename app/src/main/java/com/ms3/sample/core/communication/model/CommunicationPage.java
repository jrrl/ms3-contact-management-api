package com.ms3.sample.core.communication.model;

import com.ms3.sample.core.Pagination;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CommunicationPage {
	Pagination pagination;
	List<CommunicationDTO> communications;
}
