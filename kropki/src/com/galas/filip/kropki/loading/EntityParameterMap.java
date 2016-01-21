package com.galas.filip.kropki.loading;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EntityParameterMap {

	private Map<String, EntityParameter<?>> parameterMap;

	public EntityParameterMap(Collection<EntityParameter<?>> parameters) {
		this.parameterMap = parameters.stream()
				.collect(Collectors.toMap(EntityParameter::getName, Function.identity()));
	}

	public <T> T getParameterValue(String name) {
		return (T) parameterMap.get(name).getValue();
	}
}
