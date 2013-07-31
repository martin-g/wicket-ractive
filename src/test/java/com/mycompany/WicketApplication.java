package com.mycompany;

import com.mycompany.ractive.RactiveRequestHandler;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.IContextProvider;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 */
public class WicketApplication extends WebApplication
{    	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();

		getMarkupSettings().setStripWicketTags(true);

		setAjaxRequestTargetProvider(new IContextProvider<AjaxRequestTarget, Page>()
		{
			@Override
			public AjaxRequestTarget get(Page page)
			{
				return new RactiveRequestHandler(page);
			}
		});
	}
}
