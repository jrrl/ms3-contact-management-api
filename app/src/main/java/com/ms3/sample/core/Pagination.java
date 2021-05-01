package com.ms3.sample.core;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Pagination {
	int page;
	int totalCount;
	int count;
}
