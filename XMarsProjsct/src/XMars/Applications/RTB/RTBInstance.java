package XMars.Applications.RTB;
import XMars.FileUtil.*;
import XMars.FeatureExtractor.Framework.*;

public class RTBInstance extends DataInstance
{
	private String _domainHash;
	private String _userHash;
	private String _slotHash;
	private int _slotType;
	private int _visualType;
	private int _regionCode;
	private String _adHash;
	//public String AdHash
	public String GetDomainHash() 
	{
		return _domainHash;
	}
	public String GetUserHash() 
	{
		return _userHash;
	}
	public String GetSlotHash() 
	{
		return _slotHash;
	}
	public int GetSlotType() 
	{
		return _slotType;
	}
	public int GetRegionCode() 
	{
		return _regionCode;
	}
	public int GetVisualType() 
	{
		return _visualType;
	}
	public String GetAdHash() 
	{
		return _adHash;
	}

	public static RTBInstance NewInstance(TSVLine tsvLine)
	{
		RTBInstance rtbInst = new RTBInstance();
		rtbInst._domainHash = tsvLine.GetColumnValue("domain");
		rtbInst._userHash = tsvLine.GetColumnValue("ipinyouId");
		rtbInst._slotHash = tsvLine.GetColumnValue("adSlotID");
		rtbInst._adHash = tsvLine.GetColumnValue("creativeID");
		rtbInst._slotType = Integer.parseInt(tsvLine.GetColumnValue("adSlotFormat"));
		rtbInst._visualType = Integer.parseInt(tsvLine.GetColumnValue("adSlotVisibility"));
		rtbInst._regionCode = Integer.parseInt(tsvLine.GetColumnValue("region"));
		return rtbInst;
	}
}
