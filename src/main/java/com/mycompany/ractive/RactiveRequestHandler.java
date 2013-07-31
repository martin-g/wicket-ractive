package com.mycompany.ractive;

import java.util.Deque;
import java.util.LinkedList;

import com.mycompany.json.Json;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestHandler;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;

/**
 *
 */
public class RactiveRequestHandler extends AjaxRequestHandler
{
	/**
	 * Constructor
	 *
	 * @param page the currently active page
	 */
	public RactiveRequestHandler(Page page)
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
			Deque<String> paths = new LinkedList<String>();
			while (cursor instanceof Page == false)
			{
				Object modelObject = cursor.getDefaultModelObject();
				if (modelObject instanceof RactiveObject)
				{
					// TODO optimize => do not iterate 'paths' twice (in getChildPath and getChildJson(
					CharSequence childPath = getChildPath(paths);
					JSONObject json = Json.toJsonObject(cursor);
					JSONObject childJson;
					try
					{
						childJson = getChildJson(json, paths);
					} catch (JSONException e)
					{
						throw new WicketRuntimeException(e);
					}

					appendJavaScript(String.format("Wicket.Ractive['%s'].set('w%s', %s)", cursor.getMarkupId(), childPath, childJson));
					processed = true;
					break;
				}
				paths.addFirst(cursor.getId());
				cursor = cursor.getParent();
			}
			if (processed == false)
			{
				super.add(component);
			}
		}
	}

	private CharSequence getChildPath(Deque<String> paths)
	{
		StringBuilder result = new StringBuilder();
		if (paths.isEmpty() == false)
		{
			for (String path : paths)
			{
				result.append('.').append(path);
			}
		}

		return result;
	}

	private JSONObject getChildJson(JSONObject json, Deque<String> paths) throws JSONException
	{
		if (paths.isEmpty())
		{
			return json;
		}
		else
		{
			String path = paths.removeFirst();
			return getChildJson(json.getJSONObject(path), paths);
		}
	}
}
