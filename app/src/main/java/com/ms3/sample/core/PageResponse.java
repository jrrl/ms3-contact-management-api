package com.ms3.sample.core;

import lombok.Value;

import java.util.List;

@Value
public class PageResponse<T> {
	Pagination pagination;
	List<T> items;
}
