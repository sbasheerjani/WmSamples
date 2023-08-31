package sample;

// -----( B2B Java IM Code Template v1.2
// -----( CREATED: Tue May 08 14:34:54 EST 2001
// -----( ON-HOST: MKLUG.webmethods.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<B2B-START-IMPORTS>> ---
import java.io.*;
import java.util.*;
import com.wm.app.b2b.server.*;
import com.wm.util.coder.*;
// --- <<B2B-END-IMPORTS>> ---

public final class propertyFile
{
	// ---( internal utility methods )---

	final static propertyFile _instance = new propertyFile();

	static propertyFile _newInstance() { return new propertyFile(); }

	static propertyFile _cast(Object o) { return (propertyFile)o; }

	// ---( server methods )---




	public static final void getKeys (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(getKeys)>> ---
		// @sigtype java 3.5
		// [o] field:1:required Keys
	// get cursor on pipeline
	IDataCursor idcPipeline = pipeline.getCursor();

	// initialize variables
	Enumeration keys = null;
	Vector v = new Vector();

	// check to see if properties object is null
	if (properties == null)
	{
		loadProperties( null );
	}

	// get key names from properties object
	keys = properties.propertyNames();

	// build a vector of the keys
	while (keys.hasMoreElements())
	{
		String id = (String) keys.nextElement();
		v.addElement(id);
	}

	// put the keys in a string list
    int idCount = v.size();
	if (idCount >0)
	{
		String[] astrKeys = new String[idCount];
		v.copyInto(astrKeys);

		// Put data into pipeline
		idcPipeline.insertAfter("Keys", astrKeys);
	}


	// always destroy your cursor
	idcPipeline.destroy();
		// --- <<B2B-END>> ---


	}



	public static final void getProperties (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(getProperties)>> ---
		// @sigtype java 3.5
		// [i] field:1:required Keys
		// [o] record:0:required Properties

	// get Cursor on pipeline
	IDataCursor pipelineCursor = pipeline.getCursor();

	// Initialize variables
	String[] strKey   = null;
	String   strValue = null;
	int i;
	IData id = IDataFactory.create();
	IDataCursor idc = id.getCursor();

	// Get data from pipeline
	if ( pipelineCursor.first( "Keys" ) )
	{
	 	strKey = (String[]) pipelineCursor.getValue();
	}
	else
	{
		// Input not found
		util.Log.log("Service: sample.propertyFile:getProperties | required parameter 'Keys' missing", "sample.propertyFile");
		return;
	}

	// loop over the keys and get the property values
	for (i = 0; i < strKey.length; i++)
	{
		// Get property from memory
		strValue = getProperty(strKey[i]);
		util.Log.log("Service: sample.propertyFile:getProperties | " + strKey + ": " + strValue, "sample.propertyFile");

		// insert the key and value into the IData object
		idc.insertAfter(strKey[i], strValue);
	}

	// Put output into pipeline
	pipelineCursor.last();
	pipelineCursor.insertAfter( "Properties", id );

	// Always destroy your cursor
	pipelineCursor.destroy();
	idc.destroy();
		// --- <<B2B-END>> ---


	}



	public static final void loadProperties (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(loadProperties)>> ---
		// @sigtype java 3.5

    /*
        Retrieves values from configuration file named "yourCustomConfig.cnf" from
        directory [server]/packages/WmSamples/config
    */

	// build the location of the config file
    String configFile = "packages" + File.separator + PACKAGE + File.separator +
                        "config" + File.separator + CONFIG_FILE;

	util.Log.log("*********************************", "sample.propertyFile");
    util.Log.log("sample.propertyFile:loadProperties: configFile= " + configFile, "sample.propertyFile");

    try
	{
		// read in the file stream and convert it to a properties object
        FileInputStream configFileInputStream = new FileInputStream( configFile );

		// properties object is already defined in the Shared code
        properties = new Properties();
        properties.load( configFileInputStream );
		propertiesLoaded = true;

        util.Log.log("*********************************", "sample.propertyFile");
        util.Log.log("sample.propertyFile:loadProperties", "sample.propertyFile");
        util.Log.log(properties.toString(), "sample.propertyFile");
        util.Log.log("*********************************", "sample.propertyFile");
    }
	catch ( FileNotFoundException e )
	{
        // print error to standard out and throw an error
        util.Log.log( "sample.propertyFile:loadProperties error: Cannot find the config file: " + configFile , "sample.propertyFile");
		Service.throwError("Error finding property file: "+e);
    }
	catch ( IOException e )
	{
        // print error to standard out and throw an error
        util.Log.log( "b2bStartup.loadProperties error: Cannot read the config file: " + configFile , "sample.propertyFile");
		Service.throwError("Error reading yourCustomConfig.cnf property file: "+e);
    }
		// --- <<B2B-END>> ---


	}

	// --- <<B2B-START-SHARED>> ---
	// initialize static variables
	private static String PACKAGE = "WmSamples";
	private static String CONFIG_FILE = "yourCustomConfig.cnf";

    private static boolean propertiesLoaded = false;
    private static Properties properties = null;

    /** gets a property from the config file **/
    public static String getProperty( String key )
    {
		// check to see if the properties have been loaded
        if (!propertiesLoaded) {
			try
			{
				util.Log.log("Shared sample.propertyFile.getProperty() method properties have not been loaded", "sample.propertyFile");
            	loadProperties( null );
			}
			catch (Exception e)
			{
                try {
                    util.Log.log("Shared sample.propertyFile.getProperty() method error in call to loadProperties: " + e.toString(), "sample.propertyFile");
                } catch (ServiceException xxx) { /* ignore */ }
			}
        }
        if ( properties == null )
            return( null );
        else
            return( properties.getProperty( key ) );
    }
	// --- <<B2B-END-SHARED>> ---
}

