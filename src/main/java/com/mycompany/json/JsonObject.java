package com.mycompany.json;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;

/**
 * An extension of JSON2 JSONObject that supports
 * serializing a POJO to JSON by using reflection.
 *
 * Note: The reflection is not cached! For production usage
 * this should be improved!
 */
public class JsonObject extends JSONObject
{
	public JsonObject(Object object)
	{
		try
		{
			populate(object);
		} catch (Exception e)
		{
			throw new WicketRuntimeException(e);
		}
	}

	private void populate(Object object) throws IllegalAccessException, JSONException
	{
		Field[] fields = object.getClass().getFields();
		for (Field field : fields)
		{
			Class<?> fieldType = field.getType();
			String fieldName = field.getName();
			Object fieldValue = field.get(object);

			if (isSupportedType(fieldType))
			{
				put(fieldName, fieldValue);
			}
			else
			{
				put(fieldName, new JsonObject(fieldValue));
			}
		}
	}

	private boolean isSupportedType(Class<?> type)
	{
		return (String.class.equals(type) ||
				Boolean.TYPE.equals(type) || Boolean.class.equals(type) ||
				Character.TYPE.equals(type) || Character.class.equals(type) ||
				Integer.TYPE.equals(type) || Integer.class.equals(type) ||
				Float.TYPE.equals(type) || Float.class.equals(type) ||
				Byte.TYPE.equals(type) || Byte.class.equals(type) ||
				Float.TYPE.equals(type) || Float.class.equals(type) ||
				Long.TYPE.equals(type) || Long.class.equals(type) ||
				Map.class.isAssignableFrom(type) ||
				Collection.class.isAssignableFrom(type)
		);
	}
}
