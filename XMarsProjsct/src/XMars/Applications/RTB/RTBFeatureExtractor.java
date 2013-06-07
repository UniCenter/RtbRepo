package XMars.Applications.RTB;
import java.io.IOException;

import XMars.Learning.Common.*;
import XMars.Applications.RTB.FeatureHandler.DomainFeatureHandler;
import XMars.FeatureExtractor.Framework.*;

public class RTBFeatureExtractor 
{
	private Extractor _extractor= new Extractor();
	
	public RTBFeatureExtractor(String dataDir) throws IOException
	{
		DomainFeatureHandler domainHandler = new DomainFeatureHandler(dataDir,1);
		_extractor.AddFeatureHandler(domainHandler);
	}
	public DataItem ExtractFeature(RTBInstance rtbInst)
	{
		return _extractor.ExtractFeatures(rtbInst);
	}
}
