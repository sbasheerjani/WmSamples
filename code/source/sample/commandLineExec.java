package sample;

// -----( B2B Java IM Code Template v1.2
// -----( CREATED: Mon May 14 20:49:44 MDT 2001
// -----( ON-HOST: cr221437-a

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<B2B-START-IMPORTS>> ---
import java.io.*;
// --- <<B2B-END-IMPORTS>> ---

public final class commandLineExec
{
	// ---( internal utility methods )---

	final static commandLineExec _instance = new commandLineExec();

	static commandLineExec _newInstance() { return new commandLineExec(); }

	static commandLineExec _cast(Object o) { return (commandLineExec)o; }

	// ---( server methods )---




	public static final void fireCommandExec (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(fireCommandExec)>> ---
		// @sigtype java 3.5
		// [i] field:0:required fullCommand
		// [o] field:0:required successFlag
	//define input variables 
	IDataCursor idcPipeline = pipeline.getCursor();
	String 	strFileContent = null;

	//Output Variables 
	String successFlag = "false";
	String fullCommand ;

	// Check to see if the fullCommand object is in the pipeline
	if (idcPipeline.first("fullCommand"))
	{	
		//get the fullCommand stream out of the pipeline					
		fullCommand = (String) idcPipeline.getValue();
	}
	//if it is not in the pipeline, then handle the error
	else
	{
		util.Log.log("Error executing sample.io.commandLineExec.fileCommandExec: Required parameter 'fullCommand' missing.", "sample.commandLineExec");
		return;
	}

	//Run the command
	String execFile = (String) idcPipeline.getValue() ;
	
	//Execute the command. Handle the exception if it fails.
	try 
	{
 	  	Process child =   Runtime.getRuntime().exec(execFile);
		successFlag="true";
 	} 

	catch (IOException e) 
	{
		//Write the error to server.log
		util.Log.log("Error executing sample.io.commandLineExec.fileCommandExec: " + e.toString(), "sample.commandLineExec");
		successFlag="false";
 	}

	//insert the successFlag into the pipeline
	idcPipeline.insertAfter("successFlag", successFlag);	

	//Always destroy cursors that you created
	idcPipeline.destroy();
		// --- <<B2B-END>> ---

                
	}
}

