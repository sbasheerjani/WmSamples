package sample;

// -----( B2B Java IM Code Template v1.2
// -----( CREATED: Tue May 08 16:21:47 MDT 2001
// -----( ON-HOST: cr221437-a

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<B2B-START-IMPORTS>> ---
import com.wm.app.b2b.server.ServerAPI;
import com.wm.app.b2b.server.Session;
import com.wm.app.b2b.server.InvokeState;
import com.wm.app.b2b.server.User;
import com.wm.app.b2b.server.Service;
// --- <<B2B-END-IMPORTS>> ---

public final class serverInfo
{
	// ---( internal utility methods )---

	final static serverInfo _instance = new serverInfo();

	static serverInfo _newInstance() { return new serverInfo(); }

	static serverInfo _cast(Object o) { return (serverInfo)o; }

	// ---( server methods )---




	public static final void getServerInformation (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(getServerInformation)>> ---
		// @sigtype java 3.5
		// [o] field:0:required serverName
		// [o] field:0:required currentPort
	IDataCursor idcPipeline = pipeline.getCursor();

	//Output variable
	String strServerName = ServerAPI.getServerName();
	int intCurrentPort = ServerAPI.getCurrentPort();

	//insert the serverName into the pipeline	
	idcPipeline.insertAfter("serverName", strServerName);

	//insert the currentPort into the pipeline	
	idcPipeline.insertAfter("currentPort", String.valueOf(intCurrentPort));

    // Clean up IData cursors
	idcPipeline.destroy();

		// --- <<B2B-END>> ---

                
	}



	public static final void getUser (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(getUser)>> ---
		// @sigtype java 3.5
		// [o] field:0:required username
	//Get the current session
	Session currentSession = Service.getSession();
	
	//Get current user
	User user = currentSession.getUser();

	//Assign the username value to strUsername
	String strUsername = user.getName();

	//insert the username into the pipeline
	IDataCursor idcPipeline = pipeline.getCursor();
	idcPipeline.insertAfter("username", strUsername);
		// --- <<B2B-END>> ---

                
	}
}

