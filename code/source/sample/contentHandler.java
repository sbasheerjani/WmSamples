package sample;

// -----( B2B Java IM Code Template v1.2
// -----( CREATED: Sun May 27 18:38:22 MDT 2001
// -----( ON-HOST: cr221437-a

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<B2B-START-IMPORTS>> ---
import com.wm.app.b2b.server.ServerAPI;
// --- <<B2B-END-IMPORTS>> ---

public final class contentHandler
{
	// ---( internal utility methods )---

	final static contentHandler _instance = new contentHandler();

	static contentHandler _newInstance() { return new contentHandler(); }

	static contentHandler _cast(Object o) { return (contentHandler)o; }

	// ---( server methods )---




	public static final void registerContentHandler (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(registerContentHandler)>> ---
		// @sigtype java 3.5
		ServerAPI.registerContentHandler("YourContent/type", new HandlerFactory_Sample());
		// --- <<B2B-END>> ---

                
	}



	public static final void unregisterContentHandler (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(unregisterContentHandler)>> ---
		// @sigtype java 3.5
		ServerAPI.removeContentHandler("YourContent/type");
		// --- <<B2B-END>> ---

                
	}
}

