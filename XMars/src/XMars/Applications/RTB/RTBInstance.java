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
		rtbInst._domainHash = tsvLine.GetColumnValue("Domain");
		rtbInst._userHash = tsvLine.GetColumnValue("PinyouId");
		rtbInst._slotHash = tsvLine.GetColumnValue("Slot");
		rtbInst._adHash = tsvLine.GetColumnValue("Adid");
		rtbInst._slotType = Integer.parseInt(tsvLine.GetColumnValue("SlotType"));
		rtbInst._visualType = Integer.parseInt(tsvLine.GetColumnValue("VisualType"));
		rtbInst._regionCode = Integer.parseInt(tsvLine.GetColumnValue("Region"));
		return rtbInst;
	}
}
