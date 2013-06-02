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
			Feature localFeature = dataItem.Features.get(i);
			long localFeatureId = localFeature.getLocalFeatureId();
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
				featureSetView.MaxFeatureId = localFeatureId;
			}
			if(localFeatureId < featureSetView.MinFeatureId)
			{
				featureSetView.MinFeatureId =  localFeatureId;
			}
			featureSetView.TotalFeatureCount++;
			if(!featureSetView.FeatureCount.containsKey(localFeatureId))
			{
				
				featureSetView.FeatureCount.put(localFeatureId, new FeatureImpInfo(localFeatureId, 1));
			}
			else
			{
				FeatureImpInfo imp = featureSetView.FeatureCount.get(localFeatureId) ;
				imp.ImpCount = imp.ImpCount+1;
				featureSetView.FeatureCount.put(localFeatureId, imp);
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
