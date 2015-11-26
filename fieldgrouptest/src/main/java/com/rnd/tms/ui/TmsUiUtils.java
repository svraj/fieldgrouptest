package com.rnd.tms.ui;

import java.util.Iterator;

import com.vaadin.ui.Component;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;

public class TmsUiUtils {

	public static void setTextFieldNullRepresentation(Layout layout) {
		Iterator<Component> componentIterator = layout.getComponentIterator();
		// Component component = (Component)componentIterator.next();
		while (componentIterator.hasNext()) {
			Component component = (Component) componentIterator.next();
			if (component instanceof TextField) {
				TextField field = (TextField) component;
				field.setNullRepresentation("");
			} else if (component instanceof Layout) {
				setTextFieldNullRepresentation((Layout) component);
			}
		}
	}
}
