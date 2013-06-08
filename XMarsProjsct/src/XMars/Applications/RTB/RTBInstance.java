package XMars.Applications.RTB;
import XMars.FileUtil.*;
import XMars.FeatureExtractor.Framework.*;

public class RTBInstance extends DataInstance
{
	private String domain;
	private String cookieId;
	private String timestamp;
	private String userAgent;
	private String ip;
	private String url;
	private String anonymousURLID;
	private String adSlotID;
	private int slotType;
	private int visualType;
	private int region;
	private int city;
	private int adExchange;
	private int adSlotWidth;
	private int adSlotHeight;
	private int adSlotVisibility;
	private int adSlotFormat;
	private int adSlotFloorPrice;
	private String creativeID;
	private String homePage;
	private int targetValue;
	
	public static RTBInstance NewInstance(TSVLine tsvLine)
	{
		RTBInstance rtbInst = new RTBInstance();
		rtbInst.cookieId = tsvLine.GetColumnValue("ipinyouId");
		rtbInst.userAgent = tsvLine.GetColumnValue("userAgent");
		rtbInst.ip = tsvLine.GetColumnValue("ip");
		rtbInst.region = Integer.parseInt(tsvLine.GetColumnValue("region"));
		rtbInst.city = Integer.parseInt(tsvLine.GetColumnValue("city"));
		rtbInst.adExchange = Integer.parseInt(tsvLine.GetColumnValue("adExchange"));
		rtbInst.domain = tsvLine.GetColumnValue("domain");
		rtbInst.url = tsvLine.GetColumnValue("url");
		rtbInst.anonymousURLID = tsvLine.GetColumnValue("anonymousURLID");
		rtbInst.adSlotID = tsvLine.GetColumnValue("adSlotID");
		rtbInst.adSlotWidth = Integer.parseInt(tsvLine.GetColumnValue("adSlotWidth"));
		rtbInst.adSlotHeight = Integer.parseInt(tsvLine.GetColumnValue("adSlotHeight"));
		rtbInst.adSlotVisibility = Integer.parseInt(tsvLine.GetColumnValue("adSlotVisibility"));
		rtbInst.adSlotFormat = Integer.parseInt(tsvLine.GetColumnValue("adSlotFormat"));
		rtbInst.adSlotFloorPrice = Integer.parseInt(tsvLine.GetColumnValue("adSlotFloorPrice"));
		rtbInst.creativeID = tsvLine.GetColumnValue("creativeID");
		rtbInst.homePage = tsvLine.GetColumnValue("homePage");
		rtbInst.adSlotFloorPrice = Integer.parseInt(tsvLine.GetColumnValue("adSlotFloorPrice"));
		rtbInst.targetValue = Integer.parseInt(tsvLine.GetColumnValue("target"));

		return rtbInst;
	}

	public int getTargetValue() {
		return targetValue;
	}

	public String getDomain() {
		return domain;
	}

	public String getCookieId() {
		return cookieId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public String getIp() {
		return ip;
	}

	public String getUrl() {
		return url;
	}

	public String getAnonymousURLID() {
		return anonymousURLID;
	}

	public String getAdSlotID() {
		return adSlotID;
	}

	public int getSlotType() {
		return slotType;
	}

	public int getVisualType() {
		return visualType;
	}

	public int getRegion() {
		return region;
	}

	public int getCity() {
		return city;
	}

	public int getAdExchange() {
		return adExchange;
	}

	public int getAdSlotWidth() {
		return adSlotWidth;
	}

	public int getAdSlotHeight() {
		return adSlotHeight;
	}

	public int getAdSlotVisibility() {
		return adSlotVisibility;
	}

	public int getAdSlotFormat() {
		return adSlotFormat;
	}

	public int getAdSlotFloorPrice() {
		return adSlotFloorPrice;
	}

	public String getCreativeID() {
		return creativeID;
	}

	public String getHomePage() {
		return homePage;
	}

}
