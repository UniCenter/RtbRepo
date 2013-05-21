package XMars.Learning.Common;

public final class SimplePredictor 
{
	//private IFeatureEncoder _featureEncoder;
	private IPredictor _predictor;
	private LearningModel _model;
	private ItemEncoder _itemEncoder = new ItemEncoder();
	public SimplePredictor(IFeatureEncoder featureEncoder,IPredictor predictor, LearningModel model)
	{
		_itemEncoder.SetFeatureEncoder(featureEncoder);
		_predictor = predictor;
		_model = model;
	}
	public double Predict(DataItem item)
	{
		EncodedItem encodedItem = _itemEncoder.EncodeItem(item);
		return _predictor.Predict(encodedItem,_model);
	}

}
