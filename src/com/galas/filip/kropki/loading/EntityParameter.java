package com.galas.filip.kropki.loading;

import org.w3c.dom.Element;

public abstract class EntityParameter<T> {

	private String name;
	private T value;

	protected EntityParameter(String name, T defaultValue) {
		this.name = name;
		this.value = defaultValue;
	}

	public String getName() {
		return name;
	}

	public T getValue() {
		return value;
	}

	protected void setValue(T value) {
		this.value = value;
	}

	public abstract void setValueFromElement(Element element);
}
