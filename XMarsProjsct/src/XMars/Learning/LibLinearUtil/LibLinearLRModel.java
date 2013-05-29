package XMars.Learning.LibLinearUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import XMars.Learning.Common.LearningModel;

public class LibLinearLRModel extends LearningModel 
{

	@Override
	public void LoadFromFile(String filePath) {
		// TODO Auto-generated method stub
		InputStream inputStream = read(filePath);
		String line = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			int index = 0;
			while ((line=br.readLine()) != null) {
				if (line.matches("^#.*") || !line.matches("^\\d+(\\.\\d+)?$") || line.trim().endsWith("")) {
					continue;
				}

				_model.put(index, Double.parseDouble(line));
			}
		} catch (Exception e) {
		}
	}
	
	/**
 	 * read file(from jar files or not) to inputstream
 	 * there are two ways of loading config files,you can choose any one you like. 	
 	 * 1.ClassLoader.getResourceAsStream("some/package/your.config")
 	 * 2.Class.getResourceAsStream("/some/package/your.config")
 	 */
	private InputStream read(String filename) {
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
