package XMars.Learning.Common;
import java.util.*;
public class FeatureOverview 
{
	public Vector<FeatureSetView> FeatureViews = new Vector<FeatureSetView>();
	
	public void DumpToFile(String filePath){}
	
	static public FeatureOverview CreateFromFile(String filePath)
	{
		return new FeatureOverview();
	}
}
