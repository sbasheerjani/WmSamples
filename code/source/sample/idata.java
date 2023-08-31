package sample;

// -----( B2B Java IM Code Template v1.2
// -----( CREATED: Mon Jun 04 11:01:44 EDT 2001
// -----( ON-HOST: johnd

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<B2B-START-IMPORTS>> ---
import java.util.Date;
import com.wm.util.coder.IDataCodable;
// --- <<B2B-END-IMPORTS>> ---

public final class idata
{
	// ---( internal utility methods )---

	final static idata _instance = new idata();

	static idata _newInstance() { return new idata(); }

	static idata _cast(Object o) { return (idata)o; }

	// ---( server methods )---


	public static final void createNestedIData (IData pipeline)
		throws ServiceException
	{
		// --- <<B2B-START(createNestedIData)>> ---
		// @sigtype java 3.5
		// [o] record:0:required document
		// [o] - record:0:required myDoc
		// [o] -- record:1:required myDocumentArray
		// [o] --- field:0:required myString

		// an IData can be created using Flow Maps, but that is easy,
		// lets make them by hand.

		/* Sending the following document to loadXMLNode and documentToXMLString will produce similar results.
		<myDoc>
			<myDocumentArray>
			  <myString>myStringData1</myString>
			</myDocumentArray>
			<myDocumentArray>
			  <myString>myStringData2</myString>
			</myDocumentArray>
			<myDocumentArray>
			  <myString>myStringData3</myString>
			</myDocumentArray>
		</myDoc>
		*/

			IData[] myDocumentArray = new IData[3];
			myDocumentArray[0] = IDataFactory.create();
			IDataCursor id = myDocumentArray[0].getCursor();
			IDataUtil.put( id, "myString", "myStringData1");
			id.destroy();

			// use another type of constructor just for fun
			Object o[][] = {{ "myString", "myStringData2" } };
			myDocumentArray[1] = IDataFactory.create(o);

			myDocumentArray[2] = IDataFactory.create();
			id = myDocumentArray[2].getCursor();
			IDataUtil.put( id, "myString", "myStringData3");
			id.destroy();

			IData myDoc = IDataFactory.create();
			id = myDoc.getCursor();
			IDataUtil.put( id, "myDocumentArray", myDocumentArray);
			id.destroy();

			// put the document into the document container
			IData document = IDataFactory.create();
			IDataCursor idDocument = document.getCursor();
			IDataUtil.put( idDocument, "myDoc", myDoc);

			// put the document container into the pipeline

			IDataCursor idPipeline = pipeline.getCursor();
			IDataUtil.put( idPipeline, "document", document);

			// Always destroy cursors, but only once
			idDocument.destroy();
			idPipeline.destroy();
		// --- <<B2B-END>> ---
		return;
	}



	public static final void readAndWriteDocument (IData pipeline)
		throws ServiceException
	{
		// --- <<B2B-START(readAndWriteDocument)>> ---
		// @sigtype java 3.5
		// [i] record:0:required documentIn
		// [i] - field:0:required string1
		// [i] - field:0:required string2
		// [i] - field:0:required string3
		// [o] record:0:required documentOut
		// [o] - field:0:required string1-out
		// [o] - field:0:required string2-out
		// [o] - field:0:required string3-out

		// get IDataCursor to manipulate pipeline
		IDataCursor idcPipeline = pipeline.getCursor();

		// retrieve documentIn. Null is returned in no object exists with key, "documentIn"
		IData documentIn = IDataUtil.getIData( idcPipeline, "documentIn" );

		if ( documentIn == null ) {
			idcPipeline.destroy();
			// Print to standard output an error message and exit processing
			util.Log.log("Input could not be found in pipeline!", "sample.idata");
			return;
		}

		// In order to populate the documentOut object, we need to access the data in
		// the documentIn object.  To do that we must first create a cursor on the documentIn
		// object.
		IDataCursor idcDocumentIn = documentIn.getCursor();

		// Next we need to create an IData object for document out and get a cursor on that
		// IData object for data manipulation.
		IData documentOut = IDataFactory.create();
		IDataCursor idcDocumentOut = documentOut.getCursor();

		// Loop over the documentIn object while there are values
		while ( idcDocumentIn.next() )
		{
			// getKey gets the name in the name/value pair.
			String key = idcDocumentIn.getKey();
			String keyOut = key + "-out";

			// next get the value out of the name/value pair
			String value = IDataUtil.getString( idcDocumentIn );

			// insert it into the documentOut cursor
			IDataUtil.put( idcDocumentOut, keyOut, value );
		}

		// insert documentOut into the pipeline
		IDataUtil.put( idcPipeline, "documentOut", documentOut );

		// Always destroy cursors
		idcDocumentIn.destroy();
		idcDocumentOut.destroy();
		idcPipeline.destroy();
		// --- <<B2B-END>> ---
		return;
	}


	public static final void readAndWriteDocumentList (IData pipeline)
		throws ServiceException
	{
		// --- <<B2B-START(readAndWriteDocumentList)>> ---
		// @sigtype java 3.5
		// [i] record:1:required documentListIn
		// [i] - field:0:required string1
		// [i] - field:0:required string2
		// [i] - field:0:required string3
		// [o] record:1:required documentListOut
		// [o] - field:0:required string1-out
		// [o] - field:0:required string2-out
		// [o] - field:0:required string3-out

		// Get IDataCursor to manipulate pipeline
		IDataCursor idcPipeline = pipeline.getCursor();

		// Get input objects from pipeline.  If no such key/value pair exists, returns null.
		// If more than one exists, returns the value part of the first pair.
		// If the value is not an IData[], returns null.
		IData[] documentListIn = IDataUtil.getIDataArray( idcPipeline, "documentListIn" );
		int i, j;

		if ( documentListIn == null )
		{
			idcPipeline.destroy();
			// Print to standard output an error message and exit processing
			util.Log.log("Input could not be found in pipeline!", "sample.idata");
			return;
		}

		// Instantiate array of documentListOut IData objects equal to the length of
		// the documentListIn object.  Now you have an array of documentListOut IData objects.
		IData[] documentListOut = new IData[documentListIn.length];

		// Loop over the input document list (documentListIn).  If you were building a
		// document list from a vector, you would loop over the enumeration object.
		for (i = 0; i < documentListIn.length; i++)
		{
			// Each individual IData object in the array of documentListOut[] needs
			// to be created.
			documentListOut[i] = IDataFactory.create();

			// Need to get cursors on each individual document in the array
			IDataCursor idcDocumentListIn = documentListIn[i].getCursor();
			IDataCursor idcDocumentListOut = documentListOut[i].getCursor();

			// Loop over the documentListIn object while there are values
			while ( idcDocumentListIn.next() )
			{
				// getKey gets the name in the name/value pair.
				String key = idcDocumentListIn.getKey();
				String keyOut = key + "-out";

				// Next get the value out of the name/value pair.  Note:  This is a big
				// assumption that all elements in documentListIn are strings.
				String value = IDataUtil.getString( idcDocumentListIn );

				// insert it into the documentOut cursor
				IDataUtil.put( idcDocumentListOut, keyOut, value);
			}

			// Always destroy cursors
			idcDocumentListIn.destroy();
			idcDocumentListOut.destroy();
		}

		// Always destroy cursors
		IDataUtil.put(idcPipeline, "documentListOut", documentListOut);
		idcPipeline.destroy();
		// --- <<B2B-END>> ---
		return;
	}



	public static final void readAndWriteString (IData pipeline)
		throws ServiceException
	{
		// --- <<B2B-START(readAndWriteString)>> ---
		// @sigtype java 3.5
		// [i] field:0:required stringIn
		// [o] field:0:required stringOut

		// Get IDataCursor to manipulate pipeline
		IDataCursor idcPipeline = pipeline.getCursor();

		// Retrieves object with key "stringIn".  If no such key/value pair exists,
		// returns null.  If more than one exists, returns the value part of the
		// first pair.  If the value is not a string, returns value.toString().
		String stringIn = IDataUtil.getString( idcPipeline, "stringIn" );

		if ( stringIn == null ) {
			idcPipeline.destroy();
			// Print to standard output an error message and exit processing
			util.Log.log("Input could not be found in pipeline!", "sample.idata");
			return;
		}

		// Manipulate the string as you wish
		String stringOut = stringIn.toUpperCase();

		// Populate output string object
		IDataUtil.put( idcPipeline, "stringOut", stringOut );

		// Always destroy your cursor
		idcPipeline.destroy();
		// --- <<B2B-END>> ---

	}



	public static final void readAndWriteStringList (IData pipeline)
		throws ServiceException
	{
		// --- <<B2B-START(readAndWriteStringList)>> ---
		// @sigtype java 3.5
		// [i] field:1:required stringListIn
		// [o] field:1:required stringListOut
	// Get input objects from pipeline
	IDataCursor idcPipeline = pipeline.getCursor();

	// Get input objects from pipeline If no such key/value pair exists, returns null.
	// If more than one exists, returns the value part of the first pair. If
	// the value is not a String[], returns null.
	String[] stringListIn = IDataUtil.getStringArray( idcPipeline, "stringListIn" );
	int i, j;

	// Print to standard output an error message and exit processing if stringListIn
	// is null
	if ( stringListIn == null )
	{
		idcPipeline.destroy();
		util.Log.log("Input could not be found in pipeline!", "sample.idata");
		return;
	}

	// Instantiate stringListOut as the same array size as stringListIn
	String[] stringListOut = new String[stringListIn.length];

	// Populate output stringList object
	for (i = 0; i < stringListIn.length; i++)
	{
		stringListOut[i] = stringListIn[i].toUpperCase();
	}

	IDataUtil.put( idcPipeline, "stringListOut", stringListOut);
	idcPipeline.destroy();
		// --- <<B2B-END>> ---
	}



	public static final void walkAnIData (IData pipeline)
		throws ServiceException
	{
		// --- <<B2B-START(walkAnIData)>> ---
		// @sigtype java 3.5
		// [i] field:0:optional message

	IDataCursor id = pipeline.getCursor();

	// get the optional string parameter. only will use the first occurance
	// of the message element
	String msg = IDataUtil.getString( id, "message" );

	if ( msg == null ) {
		msg = "";
	}

	// I have completed all accessing of the pipeline, done with the cursor
	id.destroy();

	util.Log.log("--- START [" + new Date() + "] --- " + msg, "sample.idata");

	// see the Shared tab for dumpIData that walks the IData tree
	dumpIData(pipeline, 0);

	util.Log.log("--- END --- " + msg, "sample.idata");
		// --- <<B2B-END>> ---


	}

	// --- <<B2B-START-SHARED>> ---
	private static void dumpIData(IData in, int indent) throws ServiceException
	{
		IDataCursor idc = in.getCursor();
		for (int i=0; idc.next(); i++)
		{
			Object key = idc.getKey();
			Object val = idc.getValue();
			if (val instanceof IDataCodable)
			{
				val = ((IDataCodable)val).getIData();
			}
			if (val instanceof String[][])
			{
				String[][] st = (String[][])val;
				for (int k=0; k<st.length; k++)
				{
					for (int j=0; j<st[0].length; j++)
						util.Log.log(indent+" "+key + "["+k+"]["+j+"] = " + st[k][j], "sample.idata");
				}
			}
			else if (val instanceof String[])
			{
				String[] sa = (String[])val;
				for (int k=0; k<sa.length; k++)
				{
					util.Log.log(indent+" "+key + "["+k+"] = " + sa[k], "sample.idata");
				}
			}
			else if (val instanceof IData[])
			{
				IData[] ida = (IData[])val;
				for (int l=0; l<ida.length; l++)
				{
					util.Log.log(indent+" "+key + "["+l+"] => ", "sample.idata");
					dumpIData(ida[l], indent+1);
				}
			}
			else if (val instanceof IData)
			{
				util.Log.log(indent+" "+key + " => ", "sample.idata");
				dumpIData( (IData)val, indent+1);
			}
			else if (val instanceof IDataCodable[])
			{
				IDataCodable[] ida = (IDataCodable[])val;
				for (int l=0; l<ida.length; l++)
				{
					util.Log.log(indent+" "+key + "["+l+"] => ", "sample.idata");
					dumpIData( ida[l].getIData(), indent+1);
				}
			}
			else
				util.Log.log(indent+" "+key + " = " + val, "sample.idata");
		}
		idc.destroy();
	}
	// --- <<B2B-END-SHARED>> ---
}

