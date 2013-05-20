package XMars.FeatureExtractor.Framework;
import XMars.Learning.Common.*;

import java.util.*;

public interface IFeatureHandler 
{
	public abstract Vector<Feature> ExtractFeature(DataInstance dataInst);
	public abstract FeatureSetInfo GetSupportedFeatureSet();
}
