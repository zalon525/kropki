package com.galas.filip.kropki.loading;

import org.w3c.dom.Element;

public class BooleanEntityParameter extends EntityParameter<Boolean> {

	public BooleanEntityParameter(String name, boolean defaultValue) {
		super(name, defaultValue);
	}

	@Override
	public void setValueFromElement(Element element) {
		String text = element.getTextContent();
		text = text.trim();
		setValue(text.equalsIgnoreCase("true") ? true : false);
	}

}
