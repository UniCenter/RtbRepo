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
	private static BufferedWriter _logFatalHandle = null;
	private static BufferedWriter _logDebugHandle = null;
	final private static int fatalLevel = 16;
	final private static int warningLevel = 8;
	final private static int noticeLevel = 4;
	final private static int traceLevel = 2;
	final private static int debugLevel = 1;
	
	
	public static boolean init(String path, String file, int maxSize, int logLevel){
		try {
			_logDebugHandle = new BufferedWriter(new FileWriter(path + file, true));
			_logFatalHandle = new BufferedWriter(new FileWriter(path + file + ".wf", true));
		} catch (IOException e) {
		}
		_maxSize = maxSize;
		_logLevel = logLevel;
		return true;
	}
	
	public static void close() throws IOException {
		if (_logDebugHandle != null) {
			_logDebugHandle.close();
		}
		if (_logFatalHandle != null) {
			_logFatalHandle.close();
		}
	}
	
	public static void fatal(String info) {
		String log = "FATAL" + ":\t" + TimeUtil.getTimeStr() + "\t" + info + "\n";
		write(log, fatalLevel);
	}
	
	public static void warning(String info) {
		String log = "WARNING" + ":\t" + TimeUtil.getTimeStr() + "\t" + info + "\n";
		write(log, warningLevel);
	}
	
	public static void notice(String info) {
		String log = "NOTICE" + ":\t" + TimeUtil.getTimeStr() + "\t" + info + "\n";
		write(log, noticeLevel);
	}
	
	public static void trace(String info) {
		String log = "TRACE" + ":\t" + TimeUtil.getTimeStr() + "\t" + info + "\n";
		write(log, traceLevel);
	}
	
	public static void debug(String info) {
		String log = "DEBUG" + ":\t" + TimeUtil.getTimeStr() + "\t" + info + "\n";
		write(log, debugLevel);
	}
	
	private static BufferedWriter getHandle(int logLevel) {
		if(8<=logLevel && logLevel <=16) {
			return _logFatalHandle;
		} 
		else {
			return _logDebugHandle;
		}
	}
	
	private static void write(String log, int logLevel) {
		BufferedWriter handle = getHandle(logLevel);
		if (logLevel <= _logLevel) {
			writeLog(log, handle);
		} 
	}
	
	private static boolean writeLog(String info, BufferedWriter handle){
		try {
			handle.write(info);
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Logger.init("log/", "rtb.log", 1024*1024, 16);
		Logger.debug("test");
		Logger.fatal("warning and fatal");
		Logger.warning("warning and fatal");
		Logger.close();
	}

}
