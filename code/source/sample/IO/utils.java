package sample.IO;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2003-07-22 17:24:03 EDT
// -----( ON-HOST: ffxeng22-195.east.webmethods.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.*;
import java.lang.SecurityException;
import java.util.Properties;
import wcard.WCardDirFilter;
// --- <<IS-END-IMPORTS>> ---

public final class utils

{
	// ---( internal utility methods )---

	final static utils _instance = new utils();

	static utils _newInstance() { return new utils(); }

	static utils _cast(Object o) { return (utils)o; }

	// ---( server methods )---




	public static final void closeFileWriter (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(closeFileWriter)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] object:0:required fileWriter
		// [o] field:0:required successFlag
		//define input variables 
		BufferedWriter fileWriter = null;
		IDataCursor idcPipeline = pipeline.getCursor();
					
		//Output Variables 
		String successFlag = "false";
		
		// Check to see if the fileWriter object is in the pipeline
		if (idcPipeline.first("fileWriter"))
		{	
			//get the BufferedWriter stream out of the pipeline					
			fileWriter = (BufferedWriter) idcPipeline.getValue();
		}
		//if it is not in the pipeline, then handle the error
		else
		{
			util.Log.log("Error executing sample.io.utils.fileWriter:closeFileWriter: Required parameter 'fileWriter' missing.", "sample.IO");
			successFlag = "false";	
		
			//insert the successFlag into the pipeline
			idcPipeline.insertAfter("successFlag", successFlag);
		
			//Always destroy cursurs that you created
			idcPipeline.destroy();	
		
			return;
		}
		
		// Try to flush the fileWriter object.  Handle the exception if it fails.
		try			
		{
			fileWriter.close();
			successFlag = "true";						
		}
		catch (Exception e)
		{
			//Set the success flag because the service failed
			successFlag = "false";
		
			//Print the exception out to standard output
			util.Log.log("Error executing sample.io.utils.fileWriter:closeFileWriter:" + e.toString(), "sample.IO");	
		}
		
			//remove any other successFlags from pipline
		if(idcPipeline.first("successFlag"))
		{
				idcPipeline.delete();
		}
		
		//insert the successFlag into the pipeline
		idcPipeline.insertAfter("successFlag", successFlag);
		
		//Always destroy cursors that you created
		idcPipeline.destroy();	
		// --- <<IS-END>> ---

                
	}



	public static final void deleteFile (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(deleteFile)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required filename
		// [i] field:0:required directory
		// [o] field:0:required successFlag
		
		//define input variables
		IDataCursor idcPipeline = pipeline.getCursor();
		String longFilename,filename,directory ;
		
		  
		//Output Variables
		String successFlag = "false";
		
		// Check to see if the filename object is in the pipeline
		if (idcPipeline.first("filename"))
		{
			//get the filename string object out of the pipeline
			filename = (String) idcPipeline.getValue();
		}
		//if it is not in the pipeline, then handle the error
		else
		{
			System.out.println("Error executing sample.io.utils:deleteFile: Required parameter 'filename' missing.");
			successFlag="false";
		
			//insert the successFlag into the pipeline
			idcPipeline.insertAfter("successFlag", successFlag);
		
			//Always destroy cursors that you created
			idcPipeline.destroy();
		
			return;
		}
		
		
		// Check to see if the directory object is in the pipeline
		if (idcPipeline.first("directory"))
		{
			//get the directory string object out of the pipeline
			directory = (String) idcPipeline.getValue();
		}
		//if it is not in the pipeline, then handle the error
		else
		{
			System.out.println("Error executing sample.io.utils:deleteFile: Required parameter 'directory' missing.");
			successFlag="false";
		
			//insert the successFlag into the pipeline
			idcPipeline.insertAfter("successFlag", successFlag);
		
			//Always destroy cursors that you created
			idcPipeline.destroy();
		
			return;
		}
		
		//Check if a directory was entered
		if (directory == null)
		{
				longFilename = (filename);
		}
		else
		{
			longFilename = (directory + File.separator + filename);
		}
		
		//Assign full path name to a file object
		File localFile = new File(longFilename);
		
		//Try to delete the file
		try
		{
			//Check is a directory was entered
			if (localFile.isDirectory())
				{
					System.out.println("Error executing sample.io.utils:deleteFile: Can't delete a directory" );
					successFlag = "false";
		
				}
		
			//Check if the file doesn't exist
			else if (!localFile.exists())
				{
					System.out.println("Error executing sample.io.utils:deleteFile: File does not exist" );
					successFlag = "false";
		
				}
			//check if you can write to file
			else if (!localFile.canWrite()) {
					System.out.println("Error executing sample.io.utils:deleteFile: File not writeable");
					successFlag = "false";
		
			}
			//File can be deleted
			else
			{
			localFile.delete();
			successFlag = "true";
			}
		}
		//Catch any other error's
		catch (Exception e)
		{
			System.out.println("Error executing sample.io.utils:deleteFile:" + e.toString());
			successFlag = "false";
		}
		
		//insert the successFlag into the pipeline
		idcPipeline.insertAfter("successFlag", successFlag);
		
		//Always destroy cursors that you created
		idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void fileType (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(fileType)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required filename
		// [o] field:0:required successFlag
		// [o] field:0:required exists
		// [o] field:0:required fileType
		
		//define input variables
		IDataCursor idcPipeline = pipeline.getCursor();
		String filename = null ;
		
		//Output Variables
		String successFlag = "false";
		String exists = null;
		String fileType = null;
		
		// Check to see if the filename object is in the pipeline
		if (idcPipeline.first("filename"))
		{
			//get the filename string object out of the pipeline
			filename = (String) idcPipeline.getValue();
		}
		//if it is not in the pipeline, then handle the error
		else
		{
			System.out.println("Error executing sample.io.fileExists: Required parameter 'filename' missing.");
			successFlag="false";
		
			//insert the successFlag into the pipeline
			idcPipeline.insertAfter("successFlag", successFlag);
		
			//Always destroy cursors that you created
			idcPipeline.destroy();
		
			return;
		}
		
		//Assign file or directory name to a file object
		File fileOrDir = new File(filename);
		
		//Check if the object exists
		if (fileOrDir.exists())
		{
			// Check if the filename is an actual directory
		 	if (fileOrDir.isDirectory())
			{
				successFlag = "true";
				exists = "true";
				fileType = "directory";
			}
		
			//The filename is a name of an actual file
			else
			{
				successFlag = "true";
				exists = "true";
				fileType = "file";
			}
		}
		//File doesn't exist
		else
		{
			successFlag = "true";
			exists = "false";
			fileType = null;
		}
		
		
		//insert the successFlag into the pipeline
		idcPipeline.insertAfter("successFlag", successFlag);
		
		//insert the exists of file into the pipline
		idcPipeline.insertAfter("exists", exists);
		
		//insert the type of file into the pipline
		idcPipeline.insertAfter("fileType", fileType);
		
		//Always destroy cursors that you created
		idcPipeline.destroy();
		
		// --- <<IS-END>> ---

                
	}



	public static final void flushFileWriter (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(flushFileWriter)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] object:0:required fileWriter
		// [o] field:0:required successFlag
		//define input variables 
		BufferedWriter fileWriter = null;
		IDataCursor idcPipeline = pipeline.getCursor();
					
		//Output Variables 
		String successFlag = "false";
		
		// Check to see if the fileWriter object is in the pipeline
		if (idcPipeline.first("fileWriter"))
		{	
			//get the BufferedWriter stream out of the pipeline					
			fileWriter = (BufferedWriter) idcPipeline.getValue();
		}
		//if it is not in the pipeline, then handle the error
		else
		{
			util.Log.log("Error executing sample.io.utils.fileWriter:flushFileWriter: Required parameter 'fileWriter' missing.", "sample.IO");
			successFlag = "false";	
		
			//insert the successFlag into the pipeline
			idcPipeline.insertAfter("successFlag", successFlag);
		
			//Always destroy cursurs that you created
			idcPipeline.destroy();	
		
			return;
		}
		
		// Try to flush the fileWriter object.  Handle the exception if it fails.
		try			
		{
			fileWriter.flush();
			successFlag = "true";						
		}
		catch (Exception e)
		{
			//Set the success flag because the service failed
			successFlag = "false";
		
			//Print the exception out to standard output
			util.Log.log("Error executing sample.io.utils.fileWriter:flushFileWriter:" + e.toString(), "sample.IO");	
		}
		
		//remove any other successFlags from pipline
		if(idcPipeline.first("successFlag"))
		{
				idcPipeline.delete();
		}
		
		//insert the successFlag into the pipeline
		idcPipeline.insertAfter("successFlag", successFlag);
		
		//Always destroy cursors that you created
		idcPipeline.destroy();	
		// --- <<IS-END>> ---

                
	}



	public static final void listFilesInDirectory (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(listFilesInDirectory)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required targetDirectory
		// [i] field:0:optional filenamePattern
		// [o] field:1:optional filenameList
		// [o] field:0:required successFlag

	//define input variables
	IDataCursor idcPipeline = pipeline.getCursor();
	String successFlag = "false";
	String targetDirectory = null;

	// Check to see if the targetDirectory object is in the pipeline
	if (idcPipeline.first("targetDirectory"))
	{
			//get the targetDirectory out of the pipeline
			targetDirectory = (String)idcPipeline.getValue();
	}
	//if it is not in the pipeline, then handle the error
	else
	{
		System.out.println("Error executing sample.io.utils:listFilesInDirectory: Required parameter 'targetDirectory' missing");
		successFlag = "false";

   		//insert the successFlag into the pipeline
		idcPipeline.insertAfter("successFlag", successFlag);

		//Always destroy cursors that you created
		idcPipeline.destroy();
		return;
	}

	String filenamePattern = null;

	// Check to see if the filenamePattern object is in the pipeline
	if (idcPipeline.first("filenamePattern"))
	{
			//get the filenamePattern out of the pipeline
			filenamePattern = (String)idcPipeline.getValue();
	}

	File targetDirectoryPath = new File (targetDirectory);

	if (!targetDirectoryPath.isDirectory () || !targetDirectoryPath.exists())
	{
		System.out.println("Error executing sample.io.utils:listFilesInDirectory: Error reading directory!");
		successFlag = "false";

   		//insert the successFlag into the pipeline
		idcPipeline.insertAfter("successFlag", successFlag);

		//Always destroy cursors that you created
		idcPipeline.destroy();
		return;
	}

		idcPipeline.last ();
	String [] filenameList;


	//Check to see if filenamePattern is empty,
	if ( (filenamePattern == null) || ((filenamePattern.trim ()).length () == 0) )
	{

		//Load the direcotory listing
		filenameList = targetDirectoryPath.list ();
		successFlag ="true";

		//Check to see if directory empty
		if ( (filenameList == null) || (filenameList.length <= 0) )
		{
			filenameList=null;
			successFlag ="true";
		}
	}
	else
	{
		//Load wild card filter which is located in: /packages/WmSamples/code/jars
		WCardDirFilter dirFilter = new WCardDirFilter (filenamePattern);

		//Search for matching filenames
		filenameList = targetDirectoryPath.list (dirFilter);
		successFlag ="true";

	}

		//insert the filenameList into the pipeline
		idcPipeline.insertAfter("filenameList", filenameList);


		//insert the successFlag into the pipeline
		idcPipeline.insertAfter("successFlag", successFlag);

		//Always destroy cursors that you created
		idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void openFileWriter (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(openFileWriter)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required filename
		// [i] field:0:required overwriteFlag {"overwrite","append"}
		// [o] object:0:required fileWriter
		// [o] field:0:required successFlag
			//define input variables 
		IDataCursor idcPipeline = pipeline.getCursor();
		String strFilename = null;
		String strOverwriteFlag = null;
		
		//Output Variables 
		String successFlag = "false";
		BufferedWriter fileWriter = null;
		
		// Check to see if the filename object is in the pipeline
		if (idcPipeline.first("filename"))
		{
				//get the filename out of the pipeline						
				strFilename = (String)idcPipeline.getValue();	
		
		}
		//if it is not in the pipeline, then handle the error
		else
		{
			util.Log.log("Error executing sample.io.utils.fileWriter:openFileWriter: required parameter 'filename' missing.", "sample.IO");
			successFlag="false";
				
			//insert the successFlag into the pipeline
			idcPipeline.insertAfter("successFlag", successFlag);	
		
			//Always destroy cursors that you created
			idcPipeline.destroy();	
		
			return;
		}
		
		
		// Check to see if the overwriteFlag object is in the pipeline
		if (idcPipeline.first("overwriteFlag"))	
		{
				//get the overwriteFlag out of the pipeline						
				strOverwriteFlag = (String)idcPipeline.getValue();	 
		}
		//if it is not in the pipeline, then handle the error
		else
		{
			util.Log.log("Error executing sample.io.utils.fileWriter:openFileWriter: required parameter 'overwriteFlag' missing.", "sample.IO");
			successFlag="false";
				
			//insert the successFlag into the pipeline
			idcPipeline.insertAfter("successFlag", successFlag);	
		
			//Always destroy cursors that you created
			idcPipeline.destroy();	
		
			return;
		}
		
		// Try to create a BufferedWriter object.  Handle the exception if it fails.
		try
		{
				// Check to see if the overwriteFlag was set to overwrite
				if (strOverwriteFlag.equals("overwrite")) 			
				{
					//Create a new BufferedWriter object that will overwrite the old file
					fileWriter = new BufferedWriter(new FileWriter(strFilename, false));
				}
				// Check to see if the overwriteFlag was set to append
				else
				{
				//Create a new BufferedWriter object that will append to the old file
				fileWriter = new BufferedWriter(new FileWriter(strFilename, true));
				}
			//Set the success flag because the service was successful
				successFlag = "true";
		
		}
		catch (Exception e)
		{
			//Set the success flag because the service failed
			successFlag = "false";
		
			//Print the exception out to standard output
			util.Log.log("Error executing sample.io.utils.fileWriter:openFileWriter:" + e.toString(), "sample.IO");	
		}
		
		//insert the fileWriter into the pipeline
		idcPipeline.insertAfter("fileWriter", fileWriter);	
		
		//insert the successFlag into the pipeline
		idcPipeline.insertAfter("successFlag", successFlag);	
		
		//Always destroy cursors that you created
		idcPipeline.destroy();	
		// --- <<IS-END>> ---

                
	}



	public static final void writeFileWriter (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(writeFileWriter)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] object:0:required fileWriter
		// [i] field:0:required fileContent
		// [o] object:0:required fileWriter
		// [o] field:0:required successFlag
		//define input variables 
		IDataCursor idcPipeline = pipeline.getCursor();
		String 	strFileContent = null;
		
		//Output Variables 
		String successFlag = "false";
		BufferedWriter fileWriter = null;
		
		// Check to see if the fileWriter object is in the pipeline
		if (idcPipeline.first("fileWriter"))
		{	
			//get the BufferedWriter stream out of the pipeline					
			fileWriter = (BufferedWriter) idcPipeline.getValue();
		}
		//if it is not in the pipeline, then handle the error
		else
		{
			util.Log.log("Error:  fileWriter object is not in the pipeline!!", "sample.IO");
			return;
		}
		
		
		// Check to see if the fileContent object is in the pipeline
		if (idcPipeline.first("fileContent"))	
		{
				//get the fileContent out of the pipeline						
				strFileContent = (String) idcPipeline.getValue();
		}
		//if it is not in the pipeline, then handle the error
		else
		{
			util.Log.log("Error:  fileContent object is not in the pipeline!!", "sample.IO");
			return;
		}
		
		// Try to write to the BufferedWriter object.  Handle the exception if it fails.
		try
		{
			fileWriter.write(strFileContent);
		
			//Set the success flag because the service was successful
			successFlag = "true";
		
		}
		catch (Exception e)
		{
			//Set the success flag because the service failed
			successFlag = "false";
		
		}
		
		//remove any other successFlags from pipline
		if(idcPipeline.first("successFlag"))
		{
				idcPipeline.delete();
		}
		
		
		//insert the successFlag into the pipeline
		idcPipeline.insertAfter("successFlag", successFlag);	
		
		//Always destroy cursurs that you created
		idcPipeline.destroy();	
		// --- <<IS-END>> ---

                
	}
}

