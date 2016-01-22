package com.galas.filip.kropki.loading;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
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

import com.galas.filip.kropki.Scene;
import com.galas.filip.kropki.entity.Collider;
import com.galas.filip.kropki.entity.Entity;
import com.galas.filip.kropki.exception.SceneLoadingException;

public class XMLSceneLoader implements SceneLoader {

	private InputStream sceneInputStream;
	private Scene cachedScene = null;

	public XMLSceneLoader(File sceneFile) throws FileNotFoundException {
		this.sceneInputStream = new FileInputStream(sceneFile);
	}

	public XMLSceneLoader(InputStream sceneImputStream) {
		this.sceneInputStream = sceneImputStream;
	}

	public Scene getScene() throws SceneLoadingException {

		if (cachedScene != null) {
			return cachedScene;
		}

		SortedMap<Integer, List<Entity>> layers = new TreeMap<Integer, List<Entity>>();
		List<Entity> collidableEntities = new ArrayList<Entity>();
		Point start = new Point();
		Color backgroundColor = null;

		Document doc = getSceneDocument(sceneInputStream);

		start = ParsingUtil.parsePoint(doc.getDocumentElement().getAttribute("start"));

		if (doc.getDocumentElement().hasAttribute("background")) {
			backgroundColor = ParsingUtil.parseColor(doc.getDocumentElement().getAttribute("background"));
		}

		Collection<Collider> colliders = new ArrayList<>();

		NodeList nodeList = doc.getElementsByTagName("entity");
		for (int i = 0; i < nodeList.getLength(); i++) {

			Node n = nodeList.item(i);

			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				Loadable entity = (new XMLEntityParser(e)).getEntity();

				if (isCollider(entity)) {
					colliders.add((Collider) entity);
				}

				if (isCollidable(e)) {
					collidableEntities.add((Entity) entity);
				}

				int layer = Integer.valueOf(e.getAttribute("layer"));

				if (layers.get(layer) == null) {
					List<Entity> layerList = new ArrayList<Entity>();
					layerList.add((Entity) entity);
					layers.put(layer, layerList);
				} else {
					layers.get(layer).add((Entity) entity);
				}
			}
		}

		colliders.stream().forEach(c -> c.setCollidableEntities(collidableEntities));

		return cachedScene = new Scene(layers, start, backgroundColor);
	}

	private static Document getSceneDocument(InputStream sceneInputStream) throws SceneLoadingException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new SceneLoadingException("xml document builder creation failed", e);
		}
		Document doc = null;
		try {
			doc = dBuilder.parse(sceneInputStream);
		} catch (SAXException | IOException e) {
			throw new SceneLoadingException("parsing scene file failed", e);
		}
		doc.getDocumentElement().normalize();

		return doc;
	}

	private static boolean isCollider(Loadable entity) {
		return entity instanceof Collider;
	}

	private static boolean isCollidable(Element e) {
		return e.hasAttribute("collidable") && e.getAttribute("collidable").equals("true");
	}
}