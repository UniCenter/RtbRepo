package XMars.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtil {

	public static String getTimeStr()
	{
		return  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
	}
	
}
