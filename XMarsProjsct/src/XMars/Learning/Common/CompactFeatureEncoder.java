package XMars.Learning.Common;

import java.util.*;

public class CompactFeatureEncoder implements IFeatureEncoder {

	Hashtable<Integer,Integer> _featureSetMapping = new Hashtable<Integer,Integer>();
	Vector<Hashtable<Long,Integer> > _featureMapping = new Vector<Hashtable<Long,Integer> >();
	public CompactFeatureEncoder(FeatureOverview overview)
	{
		int compactFeatureId = 1 ;
		for(int i = 0; i < overview.FeatureViews.size() ; i++)
		{
			FeatureSetView featureView = overview.FeatureViews.get(i);
			int featureSetId = featureView.GetFeatureSet().GetFeatureSetId();
			if(! _featureSetMapping.containsKey(featureSetId))
			{
				_featureSetMapping.put(featureSetId, _featureMapping.size());
				_featureMapping.add(new Hashtable<Long,Integer>() );
			}
			int featureSetIndex = _featureSetMapping.get(featureSetId);
			Hashtable<Long,Integer> featureMapping = _featureMapping.get(featureSetIndex);
			Long[] localFeatures = featureView.GetFeatureIdList();
			for(int j = 0;j < localFeatures.length; j++)
			{
				featureMapping.put(localFeatures[j],compactFeatureId);
				compactFeatureId++;
			}
			if(!featureMapping.containsKey(0))
			{
				featureMapping.put((long)0,compactFeatureId);
				compactFeatureId++;
			}
		}
	}
	@Override
	public EncodedFeature EncodeFeature(Feature feature) 
	{
		EncodedFeature encodedFeature = new EncodedFeature();
		int featureSetIndex = _featureSetMapping.get(feature.getFeatureSet().GetFeatureSetId());
		Hashtable<Long,Integer> featureMapping = _featureMapping.get(featureSetIndex);
		long localId = feature.getLocalFeatureId();
		if(!featureMapping.containsKey(localId))
		{
			localId =0;
		}
		encodedFeature.FeatureValue = feature.getFeatureValue();
		encodedFeature.FeatureId = featureMapping.get(localId);
	
		return encodedFeature;
	}

}
