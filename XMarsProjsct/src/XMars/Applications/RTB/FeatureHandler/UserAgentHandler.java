package XMars.Applications.RTB.FeatureHandler;

import java.util.Vector;

import XMars.Applications.RTB.RTBInstance;
import XMars.FeatureExtractor.Framework.DataInstance;
import XMars.FeatureExtractor.Framework.IFeatureHandler;
import XMars.Learning.Common.Feature;
import XMars.Learning.Common.FeatureSetInfo;
import XMars.Util.NumericalUtil;

public class UserAgentHandler implements IFeatureHandler {
	private static FeatureSetInfo _featureSet;
	
	public UserAgentHandler(int featureSetId) {
		_featureSet = new FeatureSetInfo(featureSetId,"UserAgentFeature");
	}
	
	@Override
	public Vector<Feature> ExtractFeature(DataInstance dataInst) {
		RTBInstance rtbInst = (RTBInstance)dataInst;
		Vector<Feature> features = new Vector<Feature>();
		String userAgent = rtbInst.getUserAgent();
		String browser = "";
		if (userAgent.indexOf("Chrome") > 0) {
			browser = "Chrome";
		} 
		else if (userAgent.indexOf("QQDownload") > 0) {
			browser = "QQDownload";
		}
		else if (userAgent.indexOf("MSIE") > 0) {
			browser = "MSIE";
		}
		else {
			browser = "others";
		}
		long localId = NumericalUtil.getLocalId(_featureSet.GetFeatureSetId(), "" + browser);
		features.add(new Feature(_featureSet, localId, 1));
		return features;
	}

	@Override
	public FeatureSetInfo GetSupportedFeatureSet() {
		return _featureSet;
	}

}
