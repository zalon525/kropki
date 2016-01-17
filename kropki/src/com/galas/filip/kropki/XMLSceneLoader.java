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

public class XMLSceneLoader implements SceneLoader {

	private File xmlSceneFile;

	public XMLSceneLoader(File xmlSceneFile) {
		this.xmlSceneFile = xmlSceneFile;
	}

	public Scene getScene() {

		SortedMap<Integer,List<Entity>> layers = new TreeMap<Integer,List<Entity>>();
		List<Entity> collidableEntities = new ArrayList<Entity>();
		Point start = new Point();
		Color backgroundColor = null;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		Document doc = null;
		try {
			doc = dBuilder.parse(xmlSceneFile);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		doc.getDocumentElement().normalize();

		// load start point
		String str = doc.getDocumentElement().getAttribute("start");
		start = ParsingUtil.parsePoint(str);

		// load background color
		if (doc.getDocumentElement().hasAttribute("background")) {
			str = doc.getDocumentElement().getAttribute("background");
			backgroundColor = ParsingUtil.parseColor(str);
		}

		// load entities
		NodeList nodeList = doc.getElementsByTagName("entity");

		for (int i = 0; i < nodeList.getLength(); i++) {

			Node n = nodeList.item(i);

			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				String className = e.getAttribute("class");
				Class<?> c = null;
				try {
					c = Class.forName("com.galas.filip.kropki." + className);
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace();
				}
				Entity entity = null;
				try {
					entity = (Entity) (c.newInstance());
				} catch (InstantiationException ex) {
					ex.printStackTrace();
				} catch (IllegalAccessException ex) {
					ex.printStackTrace();
				}

				// entity must know how to setup itself from an
				// xml element node
				if (XMLLoadable.class.isInstance(entity)) {
					XMLLoadable xmlLoadableEntity = (XMLLoadable) entity;
					xmlLoadableEntity.setupFromXMLElement(e);

					// if collider
					if (Collider.class.isInstance(entity)) {
						Collider collider = (Collider) entity;
						collider.setCollidableEntities(collidableEntities);
					}

					// if collidable
					if (e.hasAttribute("collidable")
							&& e.getAttribute("collidable").equals("true")) {
						collidableEntities.add(entity);
					}
					
					int layer = Integer.valueOf(e.getAttribute("layer"));
					
					if (layers.get(layer) == null) {
						List<Entity> layerList = new ArrayList<Entity>();
						layerList.add(entity);
						layers.put(layer, layerList);
					} else {
						List<Entity> layerList = layers.get(layer);
						layerList.add(entity);
					}
				}
			}
		}

		return new Scene(layers, start, backgroundColor);
	}
}
