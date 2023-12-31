package sample;

// -----( B2B Java Code Template v1.2
// -----( CREATED: Wed Jun 13 15:42:04 EDT 2001
// -----( ON-HOST: johnd

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<B2B-START-IMPORTS>> ---
// --- <<B2B-END-IMPORTS>> ---

public final class timing
{
	// ---( internal utility methods )---

	final static timing _instance = new timing();

	static timing _newInstance() { return new timing(); }

	static timing _cast(Object o) { return (timing)o; }

	// ---( server methods )---




	public static final void endTiming (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(endTiming)>> ---
		// @sigtype java 3.5
		// [i] field:0:required startTime
		// [i] field:0:required sendTo {"Pipeline Only","System.out","debugLog"}
		// [i] field:0:optional msg
		// [i] field:0:optional divideBy
		// [i] field:0:optional debugLevel
		// [o] field:0:required resultTime
		// Grab the endTime before any other processing to get the most accurate timing.
		long			endTime = System.currentTimeMillis();
		
		IDataCursor		cursor = pipeline.getCursor();
		
		// Compute the result time (in ms)
		long			startTime = Long.parseLong( IDataUtil.getString( cursor, "startTime" ) );
		long			resultTime = endTime - startTime;
		
		// Divide by "divideBy" if appropriate
		int				divideBy = IDataUtil.getInt( cursor, "divideBy", -1 );
		if ( divideBy != -1 )
			resultTime /= divideBy;
		
		// Compose the output message
		String			msg = IDataUtil.getString( cursor, "msg" );
		StringBuffer	outputMsg = new StringBuffer( msg != null ? msg : "" );
		if ( msg != null )
			outputMsg.append( ": " );
		outputMsg.append( Long.toString(resultTime) );
		outputMsg.append( " ms" );
		msg = outputMsg.toString();
		
		// Always add the message to the pipeline
		IDataUtil.put( cursor, "resultTime", msg );
		
		// Output the message to System.out if appropriate
		String			sendTo = IDataUtil.getString( cursor, "sendTo" );
		if ( "System.out".equalsIgnoreCase( sendTo ) )
			util.Log.log( msg , "sample.timing");
		
		// Output the message to debugLog if appropriate
		if ( "debugLog".equalsIgnoreCase( sendTo ) )
		{
			// This is an example of how to invoke a service from Java
			// by creating a new little pipeline (pipelet).
			String		debugLevel = IDataUtil.getString( cursor, "debugLevel" );
			Object[][]	debugLogInput = {
							{ "message", msg },
							{ "function", "TIMING" },
							{ "level", debugLevel }
						};
			IData		pipelet = IDataFactory.create( debugLogInput );
			try {
				Service.doInvoke( "pub.flow", "debugLog", pipelet );
			}
			catch ( Exception x )
			{
				throw new ServiceException( x );
			}
		}
		
		cursor.destroy();
		// --- <<B2B-END>> ---

                
	}



	public static final void startTiming (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(startTiming)>> ---
		// @sigtype java 3.5
		// [o] field:0:required startTime
		IDataCursor		cursor = pipeline.getCursor();
		
		IDataUtil.put( cursor, "startTime", Long.toString(System.currentTimeMillis()));
		cursor.destroy();
		// --- <<B2B-END>> ---

                
	}
}

