
package com.quesofttech.web.components;



import java.util.List;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Environment;

/**
 * accordion component.
 *
 * @author <a href="mailto:homburgs@googlemail.com">S.Homburg</a>
 * @version $Id: Accordion.java 682 2008-05-20 22:00:02Z homburgs $
 */
@SupportsInformalParameters
@IncludeJavaScriptLibrary(value = {"${commons.scripts}/Accordion.js"})
@IncludeStylesheet(value = {"${commons.styles}/Accordion.css"})
public class AccordionQERP implements ClientElement
{
    /**
     * The id used to generate a page-unique client-side identifier for the component. If a component renders multiple
     * times, a suffix will be appended to the to id to ensure uniqueness.
     */
    @Parameter(value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
    private String _clientId;

    /**
     * array of strings to show as panels subject.
     */
    @Parameter(required = true)
    private List<?> _subjects;

    /**
     * array of strings to show as details text.
     */
    @Parameter(required = true)
    private List<?> _details;

    /**
     * output raw markup to the client if true.
     */
    @Parameter(value = "false", required = false)
    private boolean _renderDetailsRaw;

    @Inject
    private ComponentResources _resources;

    @Environmental
    private RenderSupport _pageRenderSupport;

    @Inject
    private Environment _environment;

    private String _assignedClientId;

    void setupRender()
    {
        _assignedClientId = _pageRenderSupport.allocateClientId(_clientId);
    }

    void beginRender(MarkupWriter writer)
    {
        writer.element("div", "id", getClientId());
        _resources.renderInformalParameters(writer);


        Object[] subjectsArray = _subjects.toArray();
        Object[] detailsArray = _details.toArray();

        for (int i = 0; i < subjectsArray.length; i++)
        {
            String subject = subjectsArray[i].toString();
            String detail = "";
            if (detailsArray.length >= i + 1)
                detail = detailsArray[i].toString();

            writer.element("div", "id", getClientId() + "_toggle_" + i, "class", "tap5c_accordionToggle");
            writer.write(subject);
            writer.end();

            writer.element("div", "id", getClientId() + "_content_" + i, "class", "tap5c_accordionContent", "style", "display: none;");

            writer.element("div");
            if (_renderDetailsRaw)
                writer.writeRaw(detail);
            else
                writer.write(detail);
            writer.end(); // overViewContent

            writer.end(); // overViewContent
        }
    }

    void afterRender(MarkupWriter writer)
    {
        writer.end(); // main div
        _pageRenderSupport.addScript("new accordion('%s');", getClientId());
    }

    /**
     * Returns a unique id for the element. This value will be unique for any given rendering of a
     * page. This value is intended for use as the id attribute of the client-side element, and will
     * be used with any DHTML/Ajax related JavaScript.
     */
    public String getClientId()
    {
        return _assignedClientId;
    }
}
