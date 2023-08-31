package sample.threads;

// -----( B2B Java IM Code Template v1.2
// -----( CREATED: Wed May 30 12:28:32 MDT 2001
// -----( ON-HOST: cr221437-a

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<B2B-START-IMPORTS>> ---
import com.wm.app.b2b.server.ServiceThread;
// --- <<B2B-END-IMPORTS>> ---

public final class utils
{
	// ---( internal utility methods )---

	final static utils _instance = new utils();

	static utils _newInstance() { return new utils(); }

	static utils _cast(Object o) { return (utils)o; }

	// ---( server methods )---




	public static final void endServiceThread (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(endServiceThread)>> ---
		// @sigtype java 3.5
		// [i] object:0:required runningService
		// [o] record:0:required input
		// [o] field:0:required successFlag
			//define input variables 
			IDataCursor idcPipeline = pipeline.getCursor();
			String service = null;
			ServiceThread st;
		
			//Output Variables 
			String successFlag = "false";
			IData output;
			
			// Check to see if the runningService object is in the pipeline
			if (idcPipeline.first("runningService"))
			{
					//get the filename out of the pipeline						
					st = (ServiceThread)idcPipeline.getValue();	
		
			}
			//if it is not in the pipeline, then handle the error
			else
			{
				util.Log.log("Error executing the sample.threads.utils:endServiceThread: required parameter 'runningService' missing.", "sample.threads");
				successFlag="false";
					
				//insert the successFlag into the pipeline
				idcPipeline.insertAfter("successFlag", successFlag);	
		
				//Always destroy cursurs that you created
				idcPipeline.destroy();	
		
				return;
			}
		
			//Declare output IData object
		//	IData output;
		
			//Attempt to get the output from the thread
			try
			{
				output = st.getData();
				successFlag="true";
		
				//insert the successFlag into the pipeline
				idcPipeline.insertAfter("output", output);	
		
			}
			catch ( Exception e ) 
			{
				//Set the success flag because the service failed
				successFlag = "false";
		
		
				//Print the exception out to standard output
				util.Log.log("Error executing sample.threads.utils:endServiceThread" + e.toString(), "sample.threads");
			}
		
				//insert the successFlag into the pipeline
				idcPipeline.insertAfter("successFlag", successFlag);	
		
				//Always destroy cursurs that you created
				idcPipeline.destroy();	
		// --- <<B2B-END>> ---

                
	}



	public static final void newServiceThread (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(newServiceThread)>> ---
		// @sigtype java 3.5
		// [i] field:0:required service
		// [i] field:0:required interface
		// [i] record:0:optional input
		// [i] field:0:required cloneType {"copyPipelineObjects","shallowClone","deepClone"}
		// [o] object:0:required runningService
		// [o] field:0:required successFlag
	//define input variables 
	IDataCursor idcPipeline = pipeline.getCursor();
	String folder, service, cloneType= null;
	IData input = null;


	//Output Variables 
	String successFlag = "false";

	// Check to see if the service object is in the pipeline
	if (idcPipeline.first("service"))
	{
			//get the filename out of the pipeline						
			service = (String)idcPipeline.getValue();	
	}
	//if it is not in the pipeline, then handle the error
	else
	{
		util.Log.log("Error executing sample.threads.utils:newServiceThread: required parameter 'service' missing.", "sample.threads");
		successFlag="false";
			
		//insert the successFlag into the pipeline
		idcPipeline.insertAfter("successFlag", successFlag);	

		//Always destroy cursors that you created
		idcPipeline.destroy();	

		return;
	}

	if (idcPipeline.first("interface"))
	{
			//get the folder out of the pipeline						
			folder = (String)idcPipeline.getValue();	

	}
	//if it is not in the pipeline, then handle the error
	else
	{
		util.Log.log("Error executing sample.threads.utils:newServiceThread: required parameter 'interface' missing.", "sample.threads");
		successFlag="false";
			
		//insert the successFlag into the pipeline
		idcPipeline.insertAfter("successFlag", successFlag);	

		//Always destroy cursors that you created
		idcPipeline.destroy();	

		return;
	}

	//cloneType is described in detail in the comments tab of the input/output section
	if (idcPipeline.first("cloneType"))
	{
			//get the cloneType object out of the pipeline						
			cloneType = (String)idcPipeline.getValue();	

	}
	//if it is not in the pipeline, then get the value from the pipeline
	else
	{
		util.Log.log("Error executing sample.threads.utils:newServiceThread: required parameter 'cloneType' missing.", "sample.threads");
		successFlag="false";
			
		//insert the successFlag into the pipeline
		idcPipeline.insertAfter("successFlag", successFlag);	

		//Always destroy cursors that you created
		idcPipeline.destroy();	

		return;	
	}

	//Some services need input parameters
	if (idcPipeline.first("input"))
	{
			//get the input object out of the pipeline						
			input = (IData)idcPipeline.getValue();

	}
	//if it is not in the pipeline, then get the value from the pipeline
	else
	{
			input = pipeline;		
	}


	//Delete the runningService object from the pipeline if it exists 
	if(idcPipeline.first("runningService"))
			idcPipeline.delete();


	
	// Spawn the new thread cloning the pipeline.  This needs to be done to pass
	// a copy of the pipeline as it is always changing in memory.

	ServiceThread st = null	;


		//No clone is needed
		if (cloneType.equals("copyPipelineObjects"))
		{
			//Create a new IData object that contains the necessary input
			IData idNewInput = IDataFactory.create();
			IDataCursor idcNewInput = idNewInput.getCursor();
			
			//Get a cursor on the input IData object and initialize variables
			String strKey = "";
			IData idValue = IDataFactory.create();
			IDataCursor idcOldInput = input.getCursor();
			if (idcOldInput.first("key"))
			{
				//get value
				strKey = (String) idcOldInput.getValue();
				util.Log.log("Key value: " + strKey, "sample.threads");
			}
			
			if (idcOldInput.first("value"))
			{
				//get value
				idValue = (IData) idcOldInput.getValue();
				
				//last time cursor is used - destroy it
				idcOldInput.destroy();
				util.Log.log("Data in value object: " + idValue.toString(), "sample.threads");
			}
			
			//put the data in the new input idata structure
			idcNewInput.insertAfter("value", idValue);
			idcNewInput.insertAfter("key", strKey);
			idcNewInput.destroy();
				
			st = Service.doThreadInvoke(folder, service, idNewInput);
	
			//Set the successFlag	
			successFlag="true";
		}

		//shallowClone is needed
		if (cloneType.equals("shallowClone"))
		{
			st = Service.doThreadInvoke(folder, service, IDataUtil.clone(input));
	
			//Set the successFlag	
			successFlag="true";
	
		}	

		//deepClone is needed
		if (cloneType.equals("deepClone"))
		{
				try
				{

					st = Service.doThreadInvoke(folder, service, IDataUtil.deepClone(input));
	
					//Set the successFlag	
					successFlag="true";

				}
				//Catch any error's in creating a new service thread
				catch (Exception e) 
				{
				util.Log.log("Error executing sample.threads.utils:newServiceThread:" + e.toString(), "sample.threads");
				successFlag = "false";
				}

		}	


	//Delete the successFlag object from the pipeline if it exists 
	if(idcPipeline.first("successFlag"))
			idcPipeline.delete();

	//insert the runningService into the pipeline
	idcPipeline.insertAfter("runningService", st);

	//insert the successFlag into the pipeline
	idcPipeline.insertAfter("successFlag", successFlag);	

		//Always destroy cursurs that you created
		idcPipeline.destroy();	
		// --- <<B2B-END>> ---

                
	}



	public static final void stall (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(stall)>> ---
		// @sigtype java 3.5
		// [i] field:0:required time
	//define input variables 
	IDataCursor idcPipeline = pipeline.getCursor();
	String time;

	if (idcPipeline.first("time"))
	{
			//get the time out of the pipeline						
			time= (String)idcPipeline.getValue();	

	}
	//if it is not in the pipeline, then handle the error
	else
	{
		util.Log.log("Error executing the sample.threads.utils.stall: required parameter 'time' missing.", "sample.threads");
			
		//Always destroy cursurs that you created
		idcPipeline.destroy();	
	
		return;
	}
	
	try
	{
	Thread.sleep( Integer.parseInt(time) *1000 );
	}
	catch ( InterruptedException e ) 
	{
		//Print the exception out to standard output
		util.Log.log("Error executing sample.threads.endSpawnedThread" + e.toString(), "sample.threads");
	}	
	
	util.Log.log("sample.threads.utils.stall:Stall finished" , "sample.threads");
		// --- <<B2B-END>> ---

                
	}
}

