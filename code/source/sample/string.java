package sample;

// -----( B2B Java IM Code Template v1.2
// -----( CREATED: Mon Jun 04 18:39:08 EDT 2001
// -----( ON-HOST: johnd

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<B2B-START-IMPORTS>> ---
import java.util.zip.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import com.wm.util.Debug;
import java.io.EOFException;
import java.io.InputStream;
import java.io.BufferedInputStream;
// --- <<B2B-END-IMPORTS>> ---

public final class string
{
	// ---( internal utility methods )---

	final static string _instance = new string();

	static string _newInstance() { return new string(); }

	static string _cast(Object o) { return (string)o; }

	// ---( server methods )---




	public static final void unzip (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(unzip)>> ---
		// @sigtype java 3.5
		// [i] object:0:required zippedBytes
		// [o] object:0:required bytes
		IDataCursor id = pipeline.getCursor();
		byte[] zippedBytes = null;
		if (id.first(PARAM_ZIPPEDBYTES))
		{
		    zippedBytes = (byte[])id.getValue();
		}
		else
			throw new ServiceException("Missing required parameter '"+PARAM_ZIPPEDBYTES+"'");
		
		ByteArrayInputStream is = new ByteArrayInputStream(zippedBytes);
		byte[] bytes = null;
		
		try {
			GZIPInputStream zippedStream = new GZIPInputStream(is);
		
		    ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		    int len = 0;
		    byte tempIn[] = new byte[2048];
		
		    while (len != -1)
		    {
		        try 
		        {
		            len = zippedStream.read(tempIn);
		        }
		        catch (EOFException e)
		        {
		            // catch this exception because some implementations throw
		            // it, rather than returning -1.
		            // we can assume that all the data was read in the previous
		            // read (and written to the output stream in the previous write).
		            len = -1;        
		        }
		        if (len > 0) os.write(tempIn, 0, len);
		    }
		    bytes = os.toByteArray();
		    os.close();
		    zippedStream.close();
		}
		catch (IOException e)
		{
			throw new ServiceException(e);
		}
		
		if (id.first(PARAM_BYTES))
		{
			id.setValue(bytes);
		}
		else
		{
			id.last();
			id.insertAfter(PARAM_BYTES, bytes);
		}
		id.destroy();
		// --- <<B2B-END>> ---

                
	}



	public static final void zip (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(zip)>> ---
		// @sigtype java 3.5
		// [i] object:0:required bytes
		// [o] object:0:required zippedBytes
		IDataCursor id = pipeline.getCursor();
		byte[] bytes = null;
		if (id.first(PARAM_BYTES))
		{
		    bytes = (byte[])id.getValue();
		}
		else
			throw new ServiceException("Missing required parameter '"+PARAM_BYTES+"'");
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		GZIPOutputStream oz = null;
		
		try {
			oz = new GZIPOutputStream(os);
			oz.write(bytes, 0, bytes.length);
		    oz.close();
		}
		catch (IOException e)
		{
			throw new ServiceException(e);
		}
		
		byte[] zippedBytes = zippedBytes = os.toByteArray();
		
		//
		// DEBUG
		//
		/*try {
			ByteArrayInputStream is = new ByteArrayInputStream(zippedBytes);
			GZIPInputStream iz = new GZIPInputStream(is);
			byte[] temp = new byte[5];
			iz.read(temp);
		    iz.close();
		}
		catch (Exception e1)
		{
		}
		*/
		//
		// end DEBUG
		//
		
		if (id.first(PARAM_ZIPPEDBYTES))
		{
			id.setValue(zippedBytes);
		}
		else
		{
			id.last();
			id.insertAfter(PARAM_ZIPPEDBYTES, zippedBytes);
		}
		id.destroy();
		// --- <<B2B-END>> ---

                
	}

	// --- <<B2B-START-SHARED>> ---
	static final String PARAM_BYTES = "bytes";
	static final String PARAM_ZIPPEDBYTES = "zippedBytes";
	// --- <<B2B-END-SHARED>> ---
}

