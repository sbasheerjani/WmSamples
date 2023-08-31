package sample.errorHandling;

// -----( B2B Java IM Code Template v1.2
// -----( CREATED: Wed May 02 15:10:20 EST 2001
// -----( ON-HOST: MKLUG.webmethods.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<B2B-START-IMPORTS>> ---
import com.wm.lang.ns.NSName;
// --- <<B2B-END-IMPORTS>> ---

public final class util
{
	// ---( internal utility methods )---

	final static util _instance = new util();

	static util _newInstance() { return new util(); }

	static util _cast(Object o) { return (util)o; }

	// ---( server methods )---




	public static final void doSomethingThatCouldFail (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(doSomethingThatCouldFail)>> ---
		// @sigtype java 3.5
	// To access the data in the pipeline you must create a cursor
	// on the IData object.
	IDataCursor idcPipeline = pipeline.getCursor();

	//Initialize variables
	String strSucceed = null;
	String strStatus = null;

	if (idcPipeline.first("succeed"))
	{
		strSucceed = (String)idcPipeline.getValue();
	}

	if (idcPipeline.first("status"))
	{
		strStatus = (String)idcPipeline.getValue();
	}
	else
	{
		strStatus = "";
	}

	// If strSucceed value is set to true, then throw an exception.  This exception will
	// be caught in the SEQUENCE or in the catch block depending on which approach
	// you are using.
	if (!strSucceed.equals("true"))
	{
		throw new ServiceException("Service failed because 'succeed' was set to " + strSucceed);
	}
	else
	{
		// This bit of code is deleting the status element from the IData object and
		// replacing it with the new status value.
		idcPipeline.first("status");
		idcPipeline.delete();
		idcPipeline.insertAfter("status", strStatus + "  Service succeeded.");
	}	

	// Always destroy your cursors!!!
	idcPipeline.destroy();
		// --- <<B2B-END>> ---

                
	}



	public static final void invokeAndCatchErrors (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(invokeAndCatchErrors)>> ---
		// @sigtype java 3.5
	// To access the data in the pipeline you must create a cursor
	// on the IData object.
	IDataCursor idcPipeline = pipeline.getCursor();

	//Initialize variables
	String strServiceName = null;
	String strInterfaceName = null;

	// This try block performs multiple functions.  First it gets the data out of the 
	// pipeline.  It then builds a NSName object to be used as input to the
	// Service.doInvoke() method call.  The Service.doInvoke actually invokes the 
	// specified service and catches any errors that may be thrown in the process.
	try
	{	
		if (idcPipeline.first("service"))
		{
			strServiceName = (String)idcPipeline.getValue();
		}
		else
		{
			throw new ServiceException("Invalid service name");
		}

		if (idcPipeline.first("interface"))
		{
			strInterfaceName = (String)idcPipeline.getValue();
		}
		else
		{
			throw new ServiceException("Invalid service name");
		}
	
		if (strServiceName.length() == 0 || strInterfaceName.length() == 0)
			throw new ServiceException("Invalid service name");
		
		NSName nsName = NSName.create(strInterfaceName + ":" + strServiceName);

		//execute the service specified passing the entire pipeline
		IData results = Service.doInvoke(nsName, pipeline);
		IDataUtil.merge(results, pipeline);

	}
	catch(Exception e)
	{
		idcPipeline.insertAfter("error", e.toString());
		idcPipeline.insertAfter("errorMessage", e.getMessage());
	}
	finally
	{
		// Always destroy your cursors!!!
		idcPipeline.destroy();		
	}
		// --- <<B2B-END>> ---

                
	}



	public static final void throwError (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(throwError)>> ---
		// @sigtype java 3.5

	// To access the data in the pipeline you must create a cursor
	// on the IData object.
	IDataCursor idcPipeline = pipeline.getCursor();

	// Initialize variables
	String strErrorMessage = null;

	// Get data from pipeline
	if (idcPipeline.first("errorMessage"))
	{
		strErrorMessage = (String)idcPipeline.getValue();
	}

	// Always destroy your cursor

	idcPipeline.destroy();

	throw new ServiceException(strErrorMessage);
		// --- <<B2B-END>> ---

                
	}
}

