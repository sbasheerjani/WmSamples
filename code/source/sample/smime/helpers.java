package sample.smime;

// -----( B2B Java IM Code Template v1.2
// -----( CREATED: Mon Feb 26 17:55:02 EST 2001
// -----( ON-HOST: 10.1.51.111

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<B2B-START-IMPORTS>> ---
import javax.activation.*;
import javax.mail.internet.*;
import javax.mail.Message.*;
import javax.mail.*;
import java.util.*;
import java.io.*;
import iaik.security.provider.IAIK;
import com.wm.util.*;
// --- <<B2B-END-IMPORTS>> ---

public final class helpers
{
	// ---( internal utility methods )---

	final static helpers _instance = new helpers();

	static helpers _newInstance() { return new helpers(); }

	static helpers _cast(Object o) { return (helpers)o; }

	// ---( server methods )---




	public static final void loadCertificateObjectList (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(loadCertificateObjectList)>> ---
		// @sigtype java 3.5
		// [i] field:0:required cert1
		// [i] field:0:optional cert2
		// [o] object:1:required certificates
		try {
			Object obj = ValuesEmulator.get(pipeline,"cert1");
			byte[] cert = null;
			byte[] cacert = null;
		    if (obj != null)
			{
				File certFile = new File((String)obj);
				cert = Files.readFully(certFile);
			//	iaik.x509.X509Certificate x509 = new iaik.x509.X509Certificate(cert);
			//	util.Log.log("CertInfo: "+x509.toString(), "sample.smime");
			}
			obj = ValuesEmulator.get(pipeline,"cert2");
			if (obj != null)
			{
				File cacertFile = new File((String)obj);
				cacert = Files.readFully(cacertFile);
			}
			Object[] certs2 = new Object[2];
			Object[] certs1 = new Object[1];
			Vector tmp = new Vector();
			if ( (cert != null) && (cacert != null) )
			{	tmp.addElement(cert); tmp.addElement(cacert);}
			else if ( (cert == null) && (cacert != null) )
				tmp.addElement(cacert);
			else if ( (cert != null) && (cacert == null) )
				tmp.addElement(cert);
			if (tmp.size() == 2)
			{
				tmp.copyInto(certs2);
			    ValuesEmulator.put(pipeline,"certificates",certs2);
			}
			else
			{
				tmp.copyInto(certs1);
			 	ValuesEmulator.put(pipeline,"certificates",certs1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// --- <<B2B-END>> ---
	}
}

