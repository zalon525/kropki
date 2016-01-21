package com.galas.filip.kropki.loading;

import org.w3c.dom.Element;

public class StringEntityParameter extends EntityParameter<String> {

	public StringEntityParameter(String name, String defaultValue) {
		super(name, defaultValue);
	}

	@Override
	public void setValueFromElement(Element element) {
		String text = element.getTextContent();
		setValue(text);
	}

}
