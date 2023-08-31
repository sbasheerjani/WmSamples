package sample.complexMapping.largeDoc;

// -----( B2B Java IM Code Template v1.2
// -----( CREATED: Fri May 11 17:04:40 EDT 2001
// -----( ON-HOST: hartman.webmethods.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<B2B-START-IMPORTS>> ---
import com.wm.util.List;
// --- <<B2B-END-IMPORTS>> ---

public final class messageBuilder
{
	// ---( internal utility methods )---

	final static messageBuilder _instance = new messageBuilder();

	static messageBuilder _newInstance() { return new messageBuilder(); }

	static messageBuilder _cast(Object o) { return (messageBuilder)o; }

	// ---( server methods )---




	public static final void addToList (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(addToList)>> ---
		// @sigtype java 3.5
		// [i] object:0:required list
		// [o] object:0:required list
		List l = (List)ValuesEmulator.get(pipeline, "list");
		Object o = ValuesEmulator.get(pipeline, "LineItem");
		l.addElement(o);
		// --- <<B2B-END>> ---

                
	}



	public static final void createList (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(createList)>> ---
		// @sigtype java 3.5
		// [o] object:0:required list
		List l = new List(100);
		ValuesEmulator.put(pipeline, "list", l);
		// --- <<B2B-END>> ---

                
	}



	public static final void listToArray (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(listToArray)>> ---
		// @sigtype java 3.5
		// [i] object:0:required list
		List l = (List)ValuesEmulator.get(pipeline, "list");
		int size = l.size();
		IData[] output = new IData[size];
		for (int i=0; i < size; i++)
		{
		    output[i] = (IData)l.elementAt(i);
		}
		ValuesEmulator.put(pipeline, "LineItem", output);
		// --- <<B2B-END>> ---

                
	}
}

