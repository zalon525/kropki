package com.galas.filip.kropki;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.galas.filip.kropki.entity.Collider;
import com.galas.filip.kropki.entity.Entity;
import com.galas.filip.kropki.exception.SceneLoadingException;

public class XMLSceneLoader implements SceneLoader {

	private File xmlSceneFile;
	private Scene cachedScene = null;

	public XMLSceneLoader(File xmlSceneFile) {
		this.xmlSceneFile = xmlSceneFile;
	}

	public Scene getScene() throws SceneLoadingException {

		if (cachedScene != null) {
			return cachedScene;
		}

		SortedMap<Integer, List<Entity>> layers = new TreeMap<Integer, List<Entity>>();
		List<Entity> collidableEntities = new ArrayList<Entity>();
		Point start = new Point();
		Color backgroundColor = null;

		Document doc = getSceneDocument(xmlSceneFile);

		start = ParsingUtil.parsePoint(doc.getDocumentElement().getAttribute("start"));

		if (doc.getDocumentElement().hasAttribute("background")) {
			backgroundColor = ParsingUtil.parseColor(doc.getDocumentElement().getAttribute("background"));
		}

		NodeList nodeList = doc.getElementsByTagName("entity");
		for (int i = 0; i < nodeList.getLength(); i++) {

			Node n = nodeList.item(i);

			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				Entity entity = getEntityFromElement(e);

				if (entity instanceof XMLLoadable) {
					XMLLoadable xmlLoadableEntity = (XMLLoadable) entity;
					xmlLoadableEntity.setupFromXMLElement(e);

					if (isCollider(entity)) {
						Collider collider = (Collider) entity;
						collider.setCollidableEntities(collidableEntities);
					}

					if (isCollidable(e)) {
						collidableEntities.add(entity);
					}

					int layer = Integer.valueOf(e.getAttribute("layer"));

					if (layers.get(layer) == null) {
						List<Entity> layerList = new ArrayList<Entity>();
						layerList.add(entity);
						layers.put(layer, layerList);
					} else {
						layers.get(layer).add(entity);
					}
				}
			}
		}

		return cachedScene = new Scene(layers, start, backgroundColor);
	}

	private static Document getSceneDocument(File sceneFile) throws SceneLoadingException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new SceneLoadingException("xml document builder creation failed", e);
		}
		Document doc = null;
		try {
			doc = dBuilder.parse(sceneFile);
		} catch (SAXException | IOException e) {
			throw new SceneLoadingException("parsing scene file failed", e);
		}
		doc.getDocumentElement().normalize();

		return doc;
	}

	private static Entity getEntityFromElement(Element e) throws SceneLoadingException {
		String className = e.getAttribute("class");
		Class<?> c = null;
		try {
			c = Class.forName(className);
		} catch (ClassNotFoundException ex) {
			throw new SceneLoadingException("found enitity of unknown class: " + className, ex);
		}
		try {
			return (Entity) (c.newInstance());
		} catch (InstantiationException | IllegalAccessException ex) {
			throw new SceneLoadingException("instantiating entity of class: " + className + " failed", ex);
		}
	}

	private static boolean isCollider(Entity entity) {
		return entity instanceof Collider;
	}

	private static boolean isCollidable(Element e) {
		return e.hasAttribute("collidable") && e.getAttribute("collidable").equals("true");
	}
}