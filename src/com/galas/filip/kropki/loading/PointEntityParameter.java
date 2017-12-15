package com.galas.filip.kropki.loading;

import java.awt.Point;

import org.w3c.dom.Element;

public class PointEntityParameter extends EntityParameter<Point> {

	public PointEntityParameter(String name, Point defaultValue) {
		super(name, (Point) defaultValue.clone());
	}

	@Override
	public void setValueFromElement(Element element) {
		String text = element.getTextContent();
		setValue(ParsingUtil.parsePoint(text.trim()));
	}

}
