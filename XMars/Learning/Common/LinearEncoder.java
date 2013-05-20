package XMars.Learning.Common;
public class LinearEncoder implements IFeatureEncoder
{

	@Override
	public EncodedFeature EncodeFeature(Feature feature) 
	{
		EncodedFeature newFeature = new EncodedFeature();
		newFeature.FeatureId = feature.getFeatureSet().GetFeatureSetId() *1000 + feature.getLocalFeatureId();
		newFeature.FeatureValue = feature.getFeatureValue();
		return newFeature;
	}


}
