package XMars.Learning.Common;

import java.util.Vector;

public final class EncodedItem 
{
	private static int _defaultSize =1000;
	public int ItemId;
	public int TargetValue;
	public int PredictValue;
	public Vector<EncodedFeature> Features = new Vector<EncodedFeature>(_defaultSize);

	public void SortFeature()
	{
		
	}
}
