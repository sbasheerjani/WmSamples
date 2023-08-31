package sample;

// -----( B2B Java IM Code Template v1.2
// -----( CREATED: Thu Sep 02 07:28:02 EDT 1999
// -----( ON-HOST: 10.1.1.158

import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
// --- <<B2B-START-IMPORTS>> ---
import com.wm.util.Values;
import java.util.*;
// --- <<B2B-END-IMPORTS>> ---

public final class nodeIterator
{
	// ---( internal utility methods )---

	final static nodeIterator _instance = new nodeIterator();

	static nodeIterator _newInstance() { return new nodeIterator(); }

	static nodeIterator _cast(Object o) { return (nodeIterator)o; }

	// ---( server methods )---



	public final static Values updateNodeTotals (Values in)
	{
		Values out = in;
		// --- <<B2B-START(updateNodeTotals)>> ---
		Values next = in.getValues("next");
		String name = next.getString("name");

		Values totals = in.getValues("totals");
		if(totals == null)
		    totals = new Values();

		String prevCountStr = totals.getString(name);
		if(prevCountStr == null)
		{
		    totals.put(name, "1");
		    out.put("totals", totals);
		}
		else
		{
		    try {
		        int count = Integer.parseInt(prevCountStr);
		        totals.put(name, Integer.toString(count + 1));
		    }
		    catch(Exception x) {
		        // won't happen; we're in complete control
		    }
		}
		// --- <<B2B-END>> ---
		return out;
	}
}

