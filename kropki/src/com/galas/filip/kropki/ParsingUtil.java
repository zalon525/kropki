package com.galas.filip.kropki;

import java.awt.Color;
import java.awt.Point;

public class ParsingUtil {

	private ParsingUtil() {

	}

	public static Point parsePoint(String str) {
		str = str.trim();
		String strPoint[] = str.split(" ");
		int x = Integer.parseInt(strPoint[0]);
		int y = Integer.parseInt(strPoint[1]);
		return new Point(x, y);
	}

	public static Color parseColor(String str) {
		str = str.trim();
		String strColor[] = str.split(" ");
		int r = Integer.parseInt(strColor[0]);
		int g = Integer.parseInt(strColor[1]);
		int b = Integer.parseInt(strColor[2]);
		return new Color(r, g, b);
	}

}
