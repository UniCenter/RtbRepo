package XMars.FileUtil;

import java.io.InputStream;

public class FileUtil {
	/**
 	 * read file(from jar files or not) to inputstream
 	 * there are two ways of loading config files,you can choose any one you like. 	
 	 * 1.ClassLoader.getResourceAsStream("some/package/your.config")
 	 * 2.Class.getResourceAsStream("/some/package/your.config")
 	 */
	public InputStream read(String filename) {
		InputStream resourceAsStream = Thread.currentThread()
				.getContextClassLoader().getResourceAsStream(filename);
		if (resourceAsStream == null) {
			resourceAsStream = getClass().getResourceAsStream(
					"/".concat(filename));
			if (resourceAsStream == null) {
				throw new RuntimeException("read file error ");
			}
		}
		return resourceAsStream;
	}
}
