package com.mycompany.ractive;

import com.mycompany.json.Json;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestHandler;

/**
 *
 */
public class RactiveRequestTarget extends AjaxRequestHandler
{
	/**
	 * Constructor
	 *
	 * @param page the currently active page
	 */
	public RactiveRequestTarget(Page page)
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

					String json = Json.stringify(cursor);

					appendJavaScript(String.format("Wicket.Ractive['%s'].set('w', %s)", cursor.getMarkupId(), json));
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
