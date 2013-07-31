package com.mycompany.json;

import com.mycompany.json.JsonObject;
import org.apache.wicket.Component;

/**
 *
 */
public class Json
{
	public static String stringify(Component component) {
		return toJsonObject(component).toString();
	}

	public static JsonObject toJsonObject(Component component) {
		Object modelObject = component.getDefaultModelObject();
		return new JsonObject(modelObject);
	}

}
