package XMars.Learning.Common;
import java.util.*;
public class FeatureAnalyzer 
{
	Hashtable<Integer,Integer> _featureSetMapping = new Hashtable<Integer,Integer>();
	
	FeatureOverview _overview = new FeatureOverview();
	
	public FeatureAnalyzer()
	{
	}

	public void OnItemExtracted(DataItem dataItem) 
	{
		boolean counted = false;
		for(int i = 0; i < dataItem.Features.size(); i++)
		{		
			FeatureSetInfo featureSet = dataItem.Features.get(i).getFeatureSet();
			int featureSetId = featureSet.GetFeatureSetId() ;
			
			if(!_featureSetMapping.containsKey(featureSetId))
			{
				_featureSetMapping.put(featureSetId, _overview.FeatureViews.size());
				_overview.FeatureViews.add(new FeatureSetView(featureSet));
			}
			FeatureSetView featureSetView = _overview.FeatureViews.get(_featureSetMapping.get(featureSetId));
			int localFeatureId = dataItem.Features.get(i).getLocalFeatureId();
			int featureValue = dataItem.Features.get(i).getFeatureValue();
			if(featureValue > featureSetView.MaxFeatureValue)
			{
				featureSetView.MaxFeatureValue = featureValue;
			}
			if(featureValue < featureSetView.MinFeatureValue)
			{
				featureSetView.MinFeatureValue = featureValue;
			}
			if(localFeatureId > featureSetView.MaxFeatureId)
			{
				featureSetView.MaxFeatureValue = featureValue;
			}
			if(localFeatureId < featureSetView.MinFeatureId)
			{
				featureSetView.MaxFeatureValue =  featureValue;
			}
			featureSetView.TotalFeatureCount++;
			if(!featureSetView.FeatureCount.containsKey(localFeatureId))
			{
				featureSetView.FeatureCount.put(localFeatureId, 1);
			}
			else
			{
				featureSetView.FeatureCount.put(localFeatureId, featureSetView.FeatureCount.get(localFeatureId) + 1);
			}
			if(!counted)
			{
				featureSetView.ItemCount++;
				counted = true;
			}
			
		}

	}

	public FeatureOverview GetOverview() 
	{
		return _overview;
	}

}
