// Class is instantiated by the HandlerFactory_IAS class and implements the ContentHandler
// interface.  Functionality needs to be provided for three methods:  getInputValues(),
// putOutputValues(), and getContentType.  The getInputValues method has two inputs:  the
// InputStream (java.io.InputStream) and the InvokeState (com.wm.app.b2b.server.InvokeState).
// This example converts the InputStream into ByteArrayOutputStream which is then converted
// to a byte array and placed into the pipeline for the service.

// Any service that has a document HTTP posted to it with a content type of "application/ias"
// will have a variable called "bytes" as its input.

package sample;

import java.io.*;
import java.util.*;
import com.wm.util.*;
import com.wm.util.Values;
import com.wm.util.coder.*;
import com.wm.app.b2b.server.InvokeState;
import com.wm.app.b2b.server.ContentHandler;
import com.wm.app.b2b.server.ContentHandler_CGI;
import com.wm.app.b2b.server.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ContentHandler_Sample implements ContentHandler
{
	public Values getInputValues(InputStream is, InvokeState state) throws IOException
	{
		Values out = new Values();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// Try to convert the input stream to a byte array or whatever you would like
		// in the below try/catch block.
		try
		{
			int bytes_read;
			byte buffer[] = new byte[4096];

			while ((bytes_read = is.read(buffer))!=-1)
			{
				baos.write(buffer, 0, bytes_read);
			}
			byte bytes[] = new byte[baos.size()];
			bytes = baos.toByteArray();

			// Once you are ready to put your data into the pipeline of the service
			// you can name the input variable of the service that you are calling
			// whatever you would like.
			// If you are not ready to send the data to your service, you can manipulate it
			// here before sending it to the service.  Say you want to create a document list
			// and put that on the pipeline.  You would create your document list and then
			// do the same thing:  out.put("variableName", documentListName).  This would cause your
			// service to have a input of a document list called "variableName".

			out.put("whateverYouWant", bytes);
		}
		catch (IOException e)
		{
            try {
                util.Log.log("Exception occurred while handling input file: " + e, "ContentHandler_Sample");
            } catch (ServiceException xxx) { /* oh well */ }
		}
		finally
		{
			//Always ensure your ByteArrayOutputStream is closed before ending the process
			if (baos != null)
			{
				try
				{ baos.close(); }
				catch (IOException e)
				{}
			}
		}

		return out;
	}

	public void putOutputValues(OutputStream os, Values out, InvokeState state) throws IOException
	{
	}

	public String getContentType()
	{
		return "application/ias";
	}

}
