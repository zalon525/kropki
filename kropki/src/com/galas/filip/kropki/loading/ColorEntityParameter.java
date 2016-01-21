package com.galas.filip.kropki.loading;

import java.awt.Color;

import org.w3c.dom.Element;

public class ColorEntityParameter extends EntityParameter<Color> {

	public ColorEntityParameter(String name, Color defaultValue) {
		super(name, defaultValue);
	}

	@Override
	public void setValueFromElement(Element element) {
		String text = element.getTextContent();
		setValue(ParsingUtil.parseColor(text.trim()));
	}

}
