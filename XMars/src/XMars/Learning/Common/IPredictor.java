package XMars.Learning.Common;

public interface IPredictor 
{
	//public double Predict(EncodedItem item,LearningModel model);

	public abstract double Predict(EncodedItem encodedItem, LearningModel model);
}
