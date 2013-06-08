package XMars.Applications.RTB.FeatureHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import XMars.Applications.RTB.AppConf;
import XMars.Applications.RTB.RTBInstance;
import XMars.FeatureExtractor.Framework.DataInstance;
import XMars.FeatureExtractor.Framework.IFeatureHandler;
import XMars.FileUtil.HashSerializer;
import XMars.Learning.Common.Feature;
import XMars.Learning.Common.FeatureSetInfo;
import XMars.Util.NumericalUtil;

public class CookieHandler implements IFeatureHandler {
	private static FeatureSetInfo _featureSet;
	private Hashtable<String, Integer> _featureMapping;
	private static String _cookieFile = AppConf.getCookieFile();
	
	public CookieHandler(String dataDir, int featureSetId) throws IOException
	{
		_featureSet = new FeatureSetInfo(featureSetId,"CookieFeature");
		String dataPath = dataDir + _cookieFile;
		File dataFile = new File(dataPath);
		BufferedReader reader = new BufferedReader(new FileReader(dataFile));
		_featureMapping = HashSerializer.LoadStringHashtable(reader, "\t", 0, 1);
	}
	
	@Override
	public Vector<Feature> ExtractFeature(DataInstance dataInst) 
	{
		RTBInstance rtbInst = (RTBInstance)dataInst;
		Vector<Feature> features = new Vector<Feature>();
		String cookieID = rtbInst.getDomain();
		if(_featureMapping.containsKey(cookieID));
		{
			long localId = NumericalUtil.getLocalId(_featureSet.GetFeatureSetId(), cookieID);
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
