package XMars.Conf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Conf {
	private HashMap<String, ConfEntry> _conf = new HashMap<String, ConfEntry>(1024);
	private String _confFileName = "conf/XMars.conf";
	
	public Conf() {
		try {
			if (!this.loadConf(this._confFileName)) {
				System.out.println("[Error][loadConf failed]");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Conf(String confPath, String confFileName) {
		this._confFileName = confPath + "/" + confFileName;
		try {
			if (!this.loadConf(this._confFileName)) {
				System.out.println("[Error][loadConf failed]");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean loadConf(String confFileName) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(confFileName));
		if (reader == null) {
			return false;
		}
		
		String line = "";
		String filenameLine = "";
		String sizeLine = "";
		String typeLine = "";
		String valueLine = "";
		while ((line=reader.readLine()) != null) {
			if (line.matches("^#.*") || line.trim().equals("")) {
				continue;
			}
			
			if (line.startsWith("[") && line.endsWith("]")) {
				ConfEntry entry = new ConfEntry();
				entry.setResourceName(line.trim().replace("[", "").replace("]", ""));
				
				filenameLine = reader.readLine();
				sizeLine = reader.readLine();
				typeLine = reader.readLine();
				valueLine = reader.readLine();
				
				String fields[] = filenameLine.split(":");
				if (fields.length != 2 || !fields[0].trim().equals("filename")) {
					System.out.println("[Error][loadConf][invalid line: " + filenameLine);
					return false;
				}
				entry.setResourceFileName(fields[1].trim());
				
				fields = sizeLine.split(":");
				if (fields.length != 2 || !fields[0].trim().equals("size")) {
					System.out.println("[Error][loadConf][invalid line: " + sizeLine);
					return false;
				}
				entry.setResourceSize(fields[1].trim());
				
				fields = typeLine.split(":");
				if (fields.length != 2 || !fields[0].trim().equals("type")) {
					System.out.println("[Error][loadConf][invalid line: " + typeLine);
					return false;
				}
				entry.setResourceType(fields[1].trim());
				
				fields = valueLine.split(":");
				if (fields.length != 2 || !fields[0].trim().equals("value")) {
					System.out.println("[Error][loadConf][invalid line: " + valueLine);
					return false;
				}
				entry.setResourceValue(fields[1].trim());
				
				if (!this.checkConfEntry(entry)) {
					return false;
				}
				else {
					this._conf.put(entry.getResourceName(), entry);
				}
			}
		}
		
		return true;
	}
	
	private boolean checkConfEntry(ConfEntry entry) {
		if (entry.getResourceName().trim().equals("")) {
			System.out.println("[Error][loadConf][resource name is empty]");
			return false;
		}
		
		if (this._conf.containsKey(entry.getResourceName())) {
			System.out.println("[Error][loadConf][resource name is already in conf]");
			return false;
		}
		
		if (entry.getResourceType() == "file" && entry.getResourceFileName()=="") {
			System.out.println("[Error][loadConf][rsource filename is illegal, not allow empty]");
			return false;
		}
		
		if (entry.getResourceType()=="text" && entry.getResourceValue()=="") {
			System.out.println("[Error][loadConf][rsource value is illegal, not allow empty]");
			return false;
		}
		return true;
	}
	
	public HashMap<String, ConfEntry> getConf() {
		return this._conf;
	}
	
	public ConfEntry getConfEntry(String name) {
		return this._conf.get(name);
	}
	
	public String toString() {
		return this._conf.toString();
	}

	public static void main(String[] args) throws IOException {
		Conf conf = new Conf();
		System.out.println(conf._conf.get("domain").getResourceFileName());
	}

}
