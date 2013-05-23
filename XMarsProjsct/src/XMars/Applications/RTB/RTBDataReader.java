package XMars.Applications.RTB;

public class RTBDataReader 
{
	public RTBDataReader(String filePath)
	{
	
	}
	public boolean EndOfFile()
	{
		return false;
	}
	public RTBInstance ReadRecord()
	{
		return new RTBInstance();
	}
	public void Close()
	{
	
	}

}
