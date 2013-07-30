package com.mycompany;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestHandler;

/**
 *
 */
public class JsonRequestTarget extends AjaxRequestHandler
{
	/**
	 * Constructor
	 *
	 * @param page the currently active page
	 */
	public JsonRequestTarget(Page page)
	{
		super(page);
	}

	@Override
	public void add(Component... components)
	{
		for (Component component : components)
		{
			boolean processed = false;
			Component cursor = component;
			while (cursor instanceof Page == false)
			{
				Object modelObject = cursor.getDefaultModelObject();
				if (modelObject instanceof RactiveObject) {

					String json = Json.toJson(cursor);

					appendJavaScript(String.format("Wicket.Ractive['%s'].set(%s)", cursor.getMarkupId(), json));
					processed = true;
					break;
				}
				cursor = cursor.getParent();
			}
			if (processed == false)
			{
				super.add(component);
			}
		}
	}
}
