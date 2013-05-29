package XMars.Learning.Common;

import java.util.HashMap;

public abstract class LearningModel 
{
	public HashMap<Integer, Double> _model = new HashMap<Integer, Double>();
	public abstract void LoadFromFile(String filePath);
}

