package com.galas.filip.kropki.loading;

import org.w3c.dom.Element;

import com.galas.filip.kropki.exception.SceneLoadingException;

public class EntityEntityParameter extends EntityParameter<Loadable> {

	public EntityEntityParameter(String name, Loadable defaultValue) {
		super(name, defaultValue);
	}

	@Override
	public void setValueFromElement(Element element) {
		try {
			setValue(new XMLEntityParser(element).getEntity());
		} catch (SceneLoadingException e) {
			throw new RuntimeException("cannot parse entity \"" + getName() + "\"", e);
		}
	}
}
