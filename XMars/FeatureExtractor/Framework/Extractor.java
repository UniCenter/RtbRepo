package XMars.FeatureExtractor.Framework;

import XMars.Learning.Common.*;

import java.util.*;

public class Extractor 
{
	private static int _defaultHandlerCapacity = 1000;
	Vector<IFeatureHandler> _featureHandlers = new Vector<IFeatureHandler>(_defaultHandlerCapacity);
	public void AddFeatureHandler(IFeatureHandler featureHandler)
	{
		_featureHandlers.add(featureHandler);
	}
	public DataItem ExtractFeatures(DataInstance instance)
	{
		DataItem item = new DataItem(); 
		for(int i = 0; i< _featureHandlers.size(); i++)
		{
			item.AddFeatures(_featureHandlers.get(i).ExtractFeature(instance));
		}
		return item;
	}
}
