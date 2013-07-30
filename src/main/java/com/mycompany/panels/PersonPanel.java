package com.mycompany.panels;

import com.mycompany.RactiveBehavior;
import com.mycompany.model.Person;
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

		add(new RactiveBehavior());

		add(new AddressPanel("address"));

		add(new CarsPanel("cars"));
	}
}
