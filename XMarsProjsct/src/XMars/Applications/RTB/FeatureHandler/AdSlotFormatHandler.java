package XMars.Applications.RTB.FeatureHandler;

import java.util.Hashtable;
import java.util.Vector;

import XMars.Applications.RTB.AppConf;
import XMars.Applications.RTB.RTBInstance;
import XMars.FeatureExtractor.Framework.DataInstance;
import XMars.FeatureExtractor.Framework.IFeatureHandler;
import XMars.Learning.Common.Feature;
import XMars.Learning.Common.FeatureSetInfo;
import XMars.Util.NumericalUtil;

public class AdSlotFormatHandler implements IFeatureHandler {
	private static FeatureSetInfo _featureSet;

	public AdSlotFormatHandler(int featureSetId) {
		_featureSet = new FeatureSetInfo(featureSetId,"AdSlotFormatFeature");
	}
	
	@Override
	public Vector<Feature> ExtractFeature(DataInstance dataInst) {
		RTBInstance rtbInst = (RTBInstance)dataInst;
		Vector<Feature> features = new Vector<Feature>();
		long localId = NumericalUtil.getLocalId(_featureSet.GetFeatureSetId(), "" + rtbInst.getAdSlotFormat());
		features.add(new Feature(_featureSet, localId, 1));
		
		return features;
	}

	@Override
	public FeatureSetInfo GetSupportedFeatureSet() {
		return _featureSet;
	}

}
