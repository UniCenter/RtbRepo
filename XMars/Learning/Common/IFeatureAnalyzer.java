package XMars.Learning.Common;

public interface IFeatureAnalyzer 
{
	public abstract void OnItemExtracted(DataItem dataItem);
	public abstract FeatureSetView GetFeatureSetView();
}