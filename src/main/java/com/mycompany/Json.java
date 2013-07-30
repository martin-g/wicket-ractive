package com.mycompany;

import com.google.gson.Gson;
import org.apache.wicket.Component;

/**
 *
 */
public class Json
{
	public static String toJson(Component component) {
		Object modelObject = component.getDefaultModelObject();
		Gson gson = new Gson();
		String json = gson.toJson(modelObject);
		return json;
	}
}
