package XMars.Learning.Common;

public class FeatureImpInfo implements java.lang.Comparable<FeatureImpInfo>
{
	public int FeatureId;
	public int ImpCount;
	public FeatureImpInfo(int featureId,int impCount)
	{
		FeatureId = featureId;
		ImpCount = impCount;
		
	}
	
	@Override
	public int compareTo(FeatureImpInfo o) {
		
		return ImpCount - o.ImpCount ;
	} 
}