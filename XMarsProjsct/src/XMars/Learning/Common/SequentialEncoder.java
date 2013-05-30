package XMars.Learning.Common;

public class SequentialEncoder implements IFeatureEncoder 
{

	@Override
	public EncodedFeature EncodeFeature(Feature feature) 
	{
		EncodedFeature encodedFeature = new EncodedFeature();
		encodedFeature.FeatureId = (int) feature.getLocalFeatureId();
		return encodedFeature;
	}

}
