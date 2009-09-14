
package com.quesoware.web.components;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.corelib.base.AbstractTextField;

public class textFieldDisable extends AbstractTextField
{
    @Override
    protected void writeFieldTag(MarkupWriter writer, String value)
    {
        writer.element("input",

                       "type", "hidden",

                       "name", getControlName(),

                       "id", getClientId(),

                       "value", value,
                                              
                       "size", getWidth());
    }

    final void afterRender(MarkupWriter writer)
    {
        writer.end(); // input
    }

	
	
}