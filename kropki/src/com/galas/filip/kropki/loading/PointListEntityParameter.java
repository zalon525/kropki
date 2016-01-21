package com.galas.filip.kropki.loading;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PointListEntityParameter extends EntityParameter<List<Point>> {

	public PointListEntityParameter(String name) {
		super(name, new ArrayList<>());
	}

	@Override
	public void setValueFromElement(Element element) {
		NodeList nodeList = element.getChildNodes();
		List<Point> value = new ArrayList<>();

		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element memberElement = (Element) nodeList.item(i);

				String text = memberElement.getTextContent();
				value.add(ParsingUtil.parsePoint(text));
			}
		}

		setValue(value);
	}

}
