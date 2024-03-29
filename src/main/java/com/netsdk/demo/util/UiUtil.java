package com.netsdk.demo.util;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

public class UiUtil {
	
	public static void setBorderEx(JComponent object, String title, int width) {
		Border innerBorder = BorderFactory.createTitledBorder(title);
		Border outerBorder = BorderFactory.createEmptyBorder(width, width, width, width);	
		object.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
	}

}
