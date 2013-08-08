package com.mycompany.ractive;

import java.util.Locale;

import com.mycompany.json.Json;
import com.mycompany.json.JsonObject;
import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
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

		JSONObject config;
		try
		{
			config = createConfig(component);
		} catch (JSONException e)
		{
			throw new WicketRuntimeException(e);
		}
		String javascript = String.format("Wicket.Ractive.register('%s', %s);", component.getMarkupId(), config);

		AjaxRequestTarget target = component.getRequestCycle().find(AjaxRequestTarget.class);
		if (target != null)
		{
			target.appendJavaScript(javascript);
		}
		else
		{
			response.render(new PriorityHeaderItem(OnDomReadyHeaderItem.forScript(javascript)));
		}
	}

	protected JSONObject createData(Component component) throws JSONException
	{
		JSONObject data = new JSONObject();
		JsonObject json = Json.toJsonObject(component);
		Object modelObject = component.getDefaultModelObject();
		String rootName = modelObject.getClass().getSimpleName().toLowerCase(Locale.ENGLISH);
		data.put(rootName, json);
		return data;
	}

	protected JSONObject createConfig(Component component) throws JSONException
	{
		String markupId = component.getMarkupId();
		JSONObject config = new JSONObject();
		config.put("el", markupId);
		config.put("template", "#" + markupId);
		config.put("data", createData(component));

		return config;
	}
}
