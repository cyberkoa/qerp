
package com.quesofttech.web.components;


import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * a expand-/pin-able side panel.
 *
 * @author <a href="mailto:homburgs@googlemail.com">S.Homburg</a>
 * @version $Id: SidePanel.java 694 2008-06-12 09:01:22Z homburgs $
 */
@SupportsInformalParameters
@IncludeJavaScriptLibrary(value = {"${commons.scripts}/Cookie.js", "js/SidePanelQERP.js"})
@IncludeStylesheet(value = {"${commons.styles}/SidePanel.css"})
public class SidePanelQERP implements ClientElement
{
    /**
     * The id used to generate a page-unique client-side identifier for the component. If a component renders multiple
     * times, a suffix will be appended to the to id to ensure uniqueness.
     */
    @Parameter(value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
    private String _clientId;

    /**
     * if the panel height should be dynamic, you can place
     * here the id  of the html element, on wich deliver size
     * to the panel.
     */
    @Parameter(required = false, defaultPrefix = BindingConstants.LITERAL)
    private String _sizeElement;

    /**
     * display the panel initialy in closed state if set "true".
     * even the user pinned the panel.
     */
    @Parameter(required = false, defaultPrefix = BindingConstants.PROP, value = "false")
    private boolean forceClosed;

    @Environmental
    private RenderSupport _pageRenderSupport;

    @Inject
    private ComponentResources _resources;

    private String _assignedClientId;

    void setupRender()
    {
        _assignedClientId = _pageRenderSupport.allocateClientId(_clientId);
    }

    void beginRender(MarkupWriter writer)
    {
        writer.element("div", "id", getClientId(), "class", "tap5c_sidepanel");
        _resources.renderInformalParameters(writer);
        writer.element("div", "class", "tap5c_sidepanel-panel");
        writer.element("div", "class", "tap5c_sidepanel-toggler");
        writer.end();
        writer.end();
        writer.element("div", "class", "tap5c_sidepanel-content", "style", "display: none;");
        writer.element("div", "class", "tap5c_sidepanel-pinner-bar");
        writer.element("div", "class", "tap5c_sidepanel-pinner");
        writer.end();
        writer.end();
    }

    void afterRender(MarkupWriter writer)
    {
        writer.end();
        writer.end();
        _pageRenderSupport.addScript("new SidePanel('%s','%s',%s);", getClientId(), _sizeElement, forceClosed);
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
