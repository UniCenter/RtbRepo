package XMars.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import XMars.Util.TimeUtil;

public class Logger {
	private static StringBuffer _sb = new StringBuffer();
	private static int maxSbSize = 100;
	private static int _maxSize;
	private static int _logLevel;
	private static BufferedWriter _logHandle = null;
	private static String[] logLevelName = {"FATAL", "WARNING", "NOTICE", "TRACE", "DEBUG"};
	
	public static boolean init(String path, String file, int maxSize, int logLevel) throws IOException {
		_logHandle = new BufferedWriter(new FileWriter(path + file));
		_maxSize = maxSize;
		_logLevel = logLevel;
		return true;
	}
	
	public static void close() throws IOException {
		if (_logHandle != null) {
			_logHandle.close();
		}
	}
	
	public static void LOG_FATAL(String info) {
		String log = logLevelName[0] + ":\t" + TimeUtil.getTimeStr() + "\t" + info + "\n";
		write(log, 16);
	}
	
	public static void LOG_WARNING(String info) {
		String log = logLevelName[1] + ":\t" + TimeUtil.getTimeStr() + "\t" + info + "\n";
		write(log, 16);
	}
	
	public static void LOG_NOTICE(String info) {
		String log = logLevelName[2] + ":\t" + TimeUtil.getTimeStr() + "\t" + info + "\n";
		write(log, 16);
	}
	
	public static void LOG_TRACE(String info) {
		String log = logLevelName[3] + ":\t" + TimeUtil.getTimeStr() + "\t" + info + "\n";
		write(log, 16);
	}
	
	public static void LOG_DEBUG(String info) {
		String log = logLevelName[4] + ":\t" + TimeUtil.getTimeStr() + "\t" + info + "\n";
		System.out.println("debug: " + log);
		write(log, 16);
	}
	
	private static boolean write(String info, int logLevel){
		if (logLevel > _logLevel) {
			return true;
		}
		
		if (_sb.length() + info.length() < maxSbSize) {
			_sb.append(info);
			System.out.println(_sb.toString());
		}
		else {
			try {
				System.out.println("write: " + _sb.toString());
				_logHandle.write(_sb.toString() + info);
			} catch (IOException e) {
				e.printStackTrace();
			}
			_sb.setLength(0);
		}
		
		return true;
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Logger.init("log/", "rtb.log", 1024*1024, 16);
		Logger.LOG_DEBUG("just a test main(Strinhrows");
		Logger.close();
	}

}
