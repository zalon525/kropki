package com.galas.filip.kropki.loading;

import org.w3c.dom.Element;

public class IntegerEntityParameter extends EntityParameter<Integer> {

	public IntegerEntityParameter(String name, int defaultValue) {
		super(name, defaultValue);
	}

	@Override
	public void setValueFromElement(Element element) {
		String text = element.getTextContent();
		setValue(Integer.valueOf(text.trim()));
	}

}
