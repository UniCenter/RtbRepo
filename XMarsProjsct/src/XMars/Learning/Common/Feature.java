package XMars.Learning.Common;

public final class Feature 
{
	private FeatureSetInfo _featureSet;
	private LatentFeature _latentFeature;
	private int _localFeatureId;
	private int _featureValue;
    public Feature(FeatureSetInfo featureSet,
		    		int localFeatureId,
		    		int featureValue)
    {
    	_featureSet = featureSet;
    	_localFeatureId = localFeatureId;
    	_featureValue = featureValue;
    }
    
    public Feature(FeatureSetInfo featureSet,
    		LatentFeature latentFeature,
    		int localFeatureId,
    		int featureValue)
	{
		_featureSet = featureSet;
		_latentFeature = latentFeature;
		_localFeatureId = localFeatureId;
		_featureValue = featureValue;
	}
    
    public FeatureSetInfo getFeatureSet() {
		return _featureSet;
	}
    public LatentFeature getLatentFeature() {
    	return _latentFeature;
    }
	public int getLocalFeatureId() {
		return _localFeatureId;
	}
	public int getFeatureValue() {
		return _featureValue;
	}
}
