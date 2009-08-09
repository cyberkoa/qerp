package com.quesofttech.web.services;


import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.BindingSource;

import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.ioc.annotations.InjectService;
import java.io.IOException;



public class AppModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(IBusinessServicesLocator.class, BusinessServicesLocator.class);
		binder.bind(CountryNames.class);
	}

	public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration) {
		// For now, use lower case for country code, eg. "en_us", not "en_US"; until
		// https://issues.apache.org/jira/browse/TAPESTRY-1997 is resolved.
		configuration.add("tapestry.supported-locales", "en_us,en_gb,fr");
	}

	// Based on http://wiki.apache.org/tapestry/Tapestry5HowToAddBindingPrefix
	// We use the "list:" binding prefix in at least one place - UserSearch.tml.
	
	public static void contributeBindingSource(MappedConfiguration<String, BindingFactory> configuration,
			BindingSource bindingSource) {
		//configuration.add("list", new ListBindingFactory(bindingSource));
		
	}

    public RequestFilter buildUtf8Filter(
            @InjectService("RequestGlobals") final RequestGlobals requestGlobals)
        {
            return new RequestFilter()
            {
                public boolean service(Request request, Response response, RequestHandler handler)
                    throws IOException
                {
                    requestGlobals.getHTTPServletRequest().setCharacterEncoding("UTF-8");
                    return handler.service(request, response);
                }
            };
        }
	
	
}
