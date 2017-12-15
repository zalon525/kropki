package com.galas.filip.kropki.loading;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.galas.filip.kropki.exception.SceneLoadingException;

public class XMLEntityParser {

	private Element element;

	public XMLEntityParser(Element element) {
		this.element = element;
	}

	public Loadable getEntity() throws SceneLoadingException {
		Loadable entity = getRawEntityFromElement(element);
		List<EntityParameter<?>> parameters = entity.getEntityParameters();

		for (EntityParameter<?> parameter : parameters) {
			fillParameter(element, parameter);
		}

		entity.setupFromParameters(new EntityParameterMap(parameters));

		return entity;
	}

	private static Loadable getRawEntityFromElement(Element element) throws SceneLoadingException {
		String className = element.getAttribute("class");

		Class<?> c = null;
		try {
			c = Class.forName(className);
		} catch (ClassNotFoundException ex) {
			throw new SceneLoadingException("found entity of unknown class: " + className, ex);
		}
		Object obj = null;
		try {
			obj = c.newInstance();
		} catch (InstantiationException | IllegalAccessException ex) {
			throw new SceneLoadingException("instantiating class: " + className + " failed", ex);
		}

		try {
			return (Loadable) obj;
		} catch (ClassCastException ex) {
			throw new SceneLoadingException("class " + className + " is not loadable");
		}
	}

	private static void fillParameter(Element element, EntityParameter<?> parameter) throws SceneLoadingException {
		NodeList nodeList = element.getElementsByTagName(parameter.getName());
		Element paramElement = null;

		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				paramElement = (Element) nodeList.item(i);
			}
		}
		if (paramElement == null) {
			throw new SceneLoadingException("unfilled parameter \"" + parameter.getName() + "\"");
		}
		parameter.setValueFromElement(paramElement);
	}
}
