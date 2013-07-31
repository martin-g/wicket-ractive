package com.mycompany.panels;

import static com.mycompany.ractive.Ractive.ractive;

import com.mycompany.model.Address;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.GenericPanel;

/**
 *
 */
public class AddressPanel extends GenericPanel<Address>
{
	public AddressPanel(String id)
	{
		super(id);

		add(new AjaxLink<Void>("link") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				Address address = AddressPanel.this.getModelObject();
				address.city = address.city + " Updated";
				ractive(target, AddressPanel.this);
			}
		});
	}
}
