package XMars.Learning.Common;
public class LinearEncoder implements IFeatureEncoder
{
	int _slope = 1;
	public LinearEncoder(int slope)
	{
		_slope = slope;
	}

	@Override
	public EncodedFeature EncodeFeature(Feature feature) 
	{
		EncodedFeature newFeature = new EncodedFeature();
		newFeature.FeatureId = feature.getFeatureSet().GetFeatureSetId() *_slope + feature.getLocalFeatureId();
		newFeature.FeatureValue = feature.getFeatureValue();
		return newFeature;
	}


}
