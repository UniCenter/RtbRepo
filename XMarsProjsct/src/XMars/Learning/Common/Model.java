package XMars.Learning.Common;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Model {
	public double _bias;
	public HashMap<Integer, Double> _model = new HashMap<Integer, Double>();
	public HashMap<Integer, ArrayList<Double>> _latentModel = new HashMap<Integer, ArrayList<Double>>();
	public abstract void LoadFromFile(String filePath);
	
	public double getFeatureWeight(int featureId) {
		if (_model.containsKey(featureId)) {
			return _model.get(featureId);
		}
		return 0.0;
	}
	
	/**
 	 * read file(from jar files or not) to inputstream
 	 * there are two ways of loading config files,you can choose any one you like. 	
 	 * 1.ClassLoader.getResourceAsStream("some/package/your.config")
 	 * 2.Class.getResourceAsStream("/some/package/your.config")
 	 */
	protected InputStream read(String filename) {
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
