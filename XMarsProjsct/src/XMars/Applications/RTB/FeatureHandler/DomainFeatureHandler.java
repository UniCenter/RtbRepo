package XMars.Applications.RTB.FeatureHandler;
import XMars.Learning.Common.*;
import XMars.Util.NumericalUtil;
import XMars.Applications.RTB.AppConf;
import XMars.Applications.RTB.RTBInstance;
import XMars.FileUtil.*;
import XMars.FeatureExtractor.Framework.*;

import java.util.*;
import java.io.*;

public class DomainFeatureHandler implements IFeatureHandler 
{
	private static FeatureSetInfo _featureSet;
	private Hashtable<String, Integer> _featureMapping;
	private static String _domainFileName = AppConf.getDomain();
	
	public DomainFeatureHandler(String dataDir, int featureSetId) throws IOException
	{
		_featureSet = new FeatureSetInfo(featureSetId,"DomainFeature");
		String dataPath = dataDir + "/" + _domainFileName;
		File dataFile = new File(dataPath);
		BufferedReader reader = new BufferedReader(new FileReader(dataFile));
		_featureMapping = HashSerializer.LoadStringHashtable(reader, "\t", 0, 1);
	}
	
	@Override
	public Vector<Feature> ExtractFeature(DataInstance dataInst) 
	{
		RTBInstance rtbInst = (RTBInstance)dataInst;
		Vector<Feature> features = new Vector<Feature>();
		String domainHash = rtbInst.getDomain();
		if(_featureMapping.containsKey(domainHash));
		{
			long localId = NumericalUtil.getLocalId(_featureSet.GetFeatureSetId(), domainHash);
			features.add(new Feature(_featureSet, localId, 1));
		}
		return features;
	}

	@Override
	public FeatureSetInfo GetSupportedFeatureSet() 
	{
		return _featureSet;
	}

}
