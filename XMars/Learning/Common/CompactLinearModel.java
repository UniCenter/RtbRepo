package XMars.Learning.Common;

public class CompactLinearModel extends LearningModel
{
	private double[] _weights;
	public double[] GetWeights()
	{
		return _weights;
	}
	public static CompactLinearModel CreateModelFromFile(String filePath)
	{
		CompactLinearModel model = new CompactLinearModel();
		return model;
	}
}
