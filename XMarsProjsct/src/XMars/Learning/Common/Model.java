package XMars.Learning.Common;

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
}
