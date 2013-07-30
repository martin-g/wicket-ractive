package com.mycompany;

import org.apache.wicket.Component;
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

		response.render(JavaScriptHeaderItem.forReference(new JQueryPluginResourceReference(RactiveBehavior.class, "Ractive.js")));
		response.render(JavaScriptHeaderItem.forReference(new JQueryPluginResourceReference(RactiveBehavior.class, "wicket-ractive.js")));

		String markupId = component.getMarkupId();
		String json = Json.toJson(component);
		String javascript = String.format("Wicket.Ractive.register('%s', %s);", markupId, json);
		response.render(new PriorityHeaderItem(OnDomReadyHeaderItem.forScript(javascript)));
	}
}
