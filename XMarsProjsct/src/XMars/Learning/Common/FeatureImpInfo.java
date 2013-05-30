package XMars.Learning.Common;

public class FeatureImpInfo implements java.lang.Comparable<FeatureImpInfo>
{
	public long FeatureId;
	public int ImpCount;
	public FeatureImpInfo(long featureId,int impCount)
	{
		FeatureId = featureId;
		ImpCount = impCount;
		
	}
	
	@Override
	public int compareTo(FeatureImpInfo o) {
		
		return ImpCount - o.ImpCount ;
	} 
}