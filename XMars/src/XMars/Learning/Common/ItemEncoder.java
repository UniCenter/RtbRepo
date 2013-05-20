package XMars.Learning.Common;

public class ItemEncoder 
{
	private IFeatureEncoder _featureEncoder;
	public void SetFeatureEncoder(IFeatureEncoder featureEncoder)
	{
		_featureEncoder = featureEncoder;
	}
	public EncodedItem EncodeItem(DataItem item) 
	{
		EncodedItem newItem = new EncodedItem();
		newItem.ItemId = item.ItemId;
		for(int i = 0; i< item.Features.size(); i++)
		{
			Feature originFeature = item.Features.get(i);
			EncodedFeature feature = _featureEncoder.EncodeFeature(originFeature);
			newItem.Features.add(feature);
		}
		return newItem;
	}
}
