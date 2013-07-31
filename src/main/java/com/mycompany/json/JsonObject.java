package com.mycompany.json;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.mycompany.model.Address;
import com.mycompany.model.Person;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;

/**
 *
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
			e.printStackTrace();
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

	public static void main(String[] args)
	{
		Person person = new Person();
		person.firstName = "Martin";
		List<String> cars = new ArrayList<String>();
		person.cars = cars;
		cars.add("Volvo");

		Address address = new Address();
		person.address = address;
		address.apartment = "app";
		address.city = "grad";
		address.number = 33;

		JsonObject json = new JsonObject(person);
		System.err.println(json);
	}
}
