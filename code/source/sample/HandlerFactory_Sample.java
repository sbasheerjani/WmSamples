// This class performs the functionality of creating a webMethods IS Content handler
// ContentHandlerFactory is a java class that is extended to create
// individual Content Handlers.
// The create method is called by the IS Server when the specified mime type (ie application/ias)
// is received. The mime type is determined by RegHandler, which specifies the mime type
// and identifies the Content Handler that will execute for that mime type.

package sample;

import com.wm.app.b2b.server.ContentHandler;
import com.wm.app.b2b.server.ContentHandlerFactory;

public class HandlerFactory_Sample extends ContentHandlerFactory
{
	public ContentHandler create()
	{
		return new ContentHandler_Sample();
	}
}
