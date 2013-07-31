package com.mycompany.ractive;

import com.mycompany.json.Json;
import com.mycompany.json.JsonObject;
import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.resource.JQueryPluginResourceReference;

/**
 *
 */
public class RactiveBehavior extends Behavior
{
	@Override
	public void bind(Component component)
	{
		super.bind(component);
		component.setOutputMarkupId(true);
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		response.render(JavaScriptHeaderItem.forReference(new JQueryPluginResourceReference(RactiveBehavior.class, "res/js/Ractive.js")));
		response.render(JavaScriptHeaderItem.forReference(new JQueryPluginResourceReference(RactiveBehavior.class, "res/js/wicket-ractive.js")));

		JSONObject data;
		String markupId = component.getMarkupId();
		JsonObject json = Json.toJsonObject(component);
		try
		{
			data = createData();
			data.put("w", json);
		} catch (JSONException e)
		{
			throw new WicketRuntimeException(e);
		}
		String javascript = String.format("Wicket.Ractive.register('%s', %s);", markupId, data);
		response.render(new PriorityHeaderItem(OnDomReadyHeaderItem.forScript(javascript)));
	}

	protected JSONObject createData() throws JSONException
	{
		return new JSONObject();
	}
}
