package sample.mime;

// -----( B2B Java Code Template v1.2
// -----( CREATED: Thu May 24 10:23:38 EDT 2001
// -----( ON-HOST: ffxdhcp59-252.east.webmethods.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<B2B-START-IMPORTS>> ---
import java.io.*;
import java.lang.*;
import com.wm.data.*;
import com.wm.util.*;
// --- <<B2B-END-IMPORTS>> ---

public final class helpers
{
	// ---( internal utility methods )---

	final static helpers _instance = new helpers();

	static helpers _newInstance() { return new helpers(); }

	static helpers _cast(Object o) { return (helpers)o; }

	// ---( server methods )---




	public static final void loadResourceAsStream (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(loadResourceAsStream)>> ---
		// @sigtype java 3.5
		// [o] object:0:required imageFile
		/*
			Loads "packages/WmSamples/pub/mime/b2b.gif" from the filesystem and creates a ByteArrayInputStream
		*/
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		InputStream is = null;

		try {
			// open the file and read all of the data
			// placing it into a temporary outputstream
			is = new FileInputStream("packages/WmSamples/pub/mime/b2b.gif");

			byte[] data = new byte[2048];

			int len = 0;

			len = is.read(data);

			while (len > 0) {
				bos.write(data, 0, len);
				len = is.read(data);
			}

		} catch (EOFException eof) {

			// do nothing
		} catch (IOException ioe) {

			throw new ServiceException(ioe);
		} finally {

			try {
				if (is != null) is.close();
			} catch (IOException ioe) {}

		}

		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());

		IDataCursor dc = pipeline.getCursor();

		dc.insertAfter("imageFile", bis);

		dc.destroy();
		// --- <<B2B-END>> ---
	}



	public static final void streamToString (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(streamToString)>> ---
		// @sigtype java 3.5
		// [i] object:0:required stream
		// [o] field:0:required string
		IDataCursor idhc = pipeline.getCursor();

		InputStream is=null;

		while (idhc.next() ) {
			if ( idhc.getKey().equals("stream") ) {

				is = (InputStream)idhc.getValue();

				break;
			}
		}

		if (is != null ) {
			byte b[] = new byte[8192];

			ByteOutputBuffer out = new ByteOutputBuffer();

			int read;

			try {
				while ( (read = is.read(b)) > 0 ) {

					//	System.err.print((char)read);

				   out.write(b, 0, read);

				}

				String string = out.toString();

				idhc.last();

				idhc.insertAfter("string", string);

			} catch ( IOException ioe ) {

				throw new ServiceException(ioe.getMessage() );

			}

			idhc.destroy();
		} else {

			throw new ServiceException("No content");
		}

		// --- <<B2B-END>> ---


	}



	public static final void stringToStream (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(stringToStream)>> ---
		// @sigtype java 3.5
		// [i] field:0:required string
		// [o] object:0:required stream
		IDataCursor pipelineCursor = pipeline.getCursor();

		pipelineCursor.first("string");

		String string = (String)pipelineCursor.getValue();

		pipelineCursor.destroy();

		StringBufferInputStream output = new StringBufferInputStream(string);

		pipelineCursor = pipeline.getCursor();

		while (pipelineCursor.first("stream")){

			pipelineCursor.delete();

		}

		pipelineCursor.last();

		Object inputStream = output;

		pipelineCursor.insertAfter("stream", inputStream );

		pipelineCursor.destroy();
		// --- <<B2B-END>> ---

	}
}

