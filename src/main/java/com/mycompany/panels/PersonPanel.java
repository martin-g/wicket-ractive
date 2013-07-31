package com.mycompany.panels;

import com.mycompany.RactiveBehavior;
import com.mycompany.model.Person;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.ajax.json.JsonFunction;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 *
 */
public class PersonPanel extends GenericPanel<Person>
{
	public PersonPanel(String id, Person person)
	{
		super(id, new CompoundPropertyModel<Person>(person));

		add(new RactiveBehavior()
		{
			@Override
			protected JSONObject createData() throws JSONException
			{
				JSONObject data = super.createData();

				data.put("sort", new JsonFunction("function(cars) {return cars.sort();}"));

				return data;
			}
		});

		add(new AddressPanel("address"));

		add(new CarsPanel("cars"));
	}
}
