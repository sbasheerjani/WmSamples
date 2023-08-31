package util;

import com.wm.app.b2b.server.*;
import com.wm.util.*;

public class Log {
    public static final void log(String message)
        throws ServiceException
    {
        log(message, null, null);
    }
    
    public static final void log(String message, String function)
        throws ServiceException 
    {
        log(message, function, null);
    }

    public static final void log(String message, String function, String level)
        throws ServiceException 
    {
        Values vv = new Values();
        if(message != null) vv.put("message", message);
        if(function != null) vv.put("function", function);
        if(level != null) vv.put("level", level);

        try {
            Service.doInvoke("pub.flow", "debugLog", vv);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
