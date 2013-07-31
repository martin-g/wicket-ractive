package com.mycompany;

import static com.mycompany.ractive.Ractive.ractive;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.model.Address;
import com.mycompany.model.Person;
import com.mycompany.panels.CsvCarsPanel;
import com.mycompany.panels.PersonPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		final Person person = new Person();
		person.firstName = "Martin";
		person.lastName = "Grigorov";
		person.email = "mgrigorov@apache.org";

		Address address = new Address();
		person.address = address;
		address.apartment = "ap. 2";
		address.city = "My City";
		address.number = 34;
		address.phone = "(359) 123456789";
		address.postcode = "12345";
		address.street = "My Street";
		address.companyName = "My Company, Inc.";

		List<String> cars = new ArrayList<String>();
		person.cars = cars;
		cars.add("Opel");
		cars.add("VW");

		final PersonPanel personPanel = new PersonPanel("demo", person);
		add(personPanel);

		add(new AjaxLink<Void>("link") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				person.address.street = person.address.street + " Updated";
				person.cars.add("BMW");

				// only the model data is updated without changes in the
				// template, so we can use the optimal re-render with Ractive
				ractive(target, personPanel);
			}
		});

		add(new AjaxLink<Void>("replace") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				CsvCarsPanel carsPanel = new CsvCarsPanel("cars");
				personPanel.replace(carsPanel);

				// since we change the Ractive template (by replacing components)
				// we need to use normal Ajax update that will re-initialize Ractive
				// for the person panel
				target.add(personPanel);
			}
		});
    }
}
