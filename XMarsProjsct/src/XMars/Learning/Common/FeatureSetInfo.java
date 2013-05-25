package XMars.Learning.Common;

public final class FeatureSetInfo 
{
	private int _featureSetId;
	private String _description;

	public int GetFeatureSetId()
	{
		return _featureSetId;
	}

	public String GetDescription()
	{
		return _description;
	}
	public FeatureSetInfo(int featureSetId,
							String description)
	{
		_featureSetId = featureSetId;
		//_offset = offset;
		//_maxFeatureCount = maxFeatureCount;
		_description = description;
		
		
	}

}
