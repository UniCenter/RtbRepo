package XMars.Conf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class Conf {
	private HashMap<String, ConfEntry> _conf = new HashMap<String, ConfEntry>(1024);
	private String _confPath = "conf/";
	private String _confFile = "XMars.conf";
	
	public Conf() {
		String confFileName = this._confPath + this._confFile; 
		try {
			if (!this.loadConf(confFileName)) {
				System.out.println("[Error][loadConf failed]");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Conf(String confPath, String confFile) {
		this._confPath = confPath;
		this._confFile = confFile;
		String confFileName = this._confPath + this._confFile; 
		try {
			if (!this.loadConf(confFileName)) {
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
		while ((line=reader.readLine()) != null) {
			if (line.matches("^#.*") || line.trim().equals("")) {
				continue;
			}
			
			String fields[] = line.split(":");
			if (fields.length != 2) {
				System.out.println("[Error][Invalid conf entry " + line.trim() + "]");
				continue;
			}
			
			ConfEntry entry = new ConfEntry();
			entry.setName(fields[0].trim());
			entry.setValue(fields[1].trim());
				
			if (!this.checkConfEntry(entry)) {
				System.out.println("[Error][check conf entry failed" + line);
				return false;
			}
			else {
				this._conf.put(entry.getName(), entry);
			}
		}
		
		return true;
	}
	
	private boolean checkConfEntry(ConfEntry entry) {
		if (entry.getName().equals("")) {
			System.out.println("[Error][Empty Name is not Allowed!]" + entry.toString());
			return false;
		}
		
		if (this._conf.containsKey(entry.getName())) {
			System.out.println("[Error][Conf Name Conflict]" + entry.toString());
			return false;
		}
		
		//TODO, sometimes you need to check conf entry value range
		return true;
	}
	
	public ConfEntry getConfEntry(String name) {
		if (this._conf.containsKey(name)) {
			return this._conf.get(name);
		}
		
		System.out.print("[Error][Unexist Conf Entry]"  + name);
		return null;
	}
	
	public Short getConfShort(String name) {
		if (!this._conf.containsKey(name)) {
			System.out.println("[Error][Unexist Conf Entry]" + name);
			return null;
		}
		return Short.parseShort(this._conf.get(name).getValue());
	}
	
	public Integer getConfInt(String name) {
		if (!this._conf.containsKey(name)) {
			System.out.println("[Error][Unexist Conf Entry]" + name);
			return null;
		}
		return Integer.parseInt(this._conf.get(name).getValue());
	}
	
	public Double getConfDouble(String name) {
		if (!this._conf.containsKey(name)) {
			System.out.println("[Error][Unexist Conf Entry]" + name);
			return null;
		}
		return Double.parseDouble(this._conf.get(name).getValue());
	}
	
	public Long getConfLong(String name) {
		if (!this._conf.containsKey(name)) {
			System.out.println("[Error][Unexist Conf Entry]" + name);
			return null;
		}
		return Long.parseLong(this._conf.get(name).getValue());
	}
	
	public String getConfString(String name) {
		if (!this._conf.containsKey(name)) {
			System.out.println("[Error][Unexist Conf Entry]" + name);
			return null;
		}
		return this._conf.get(name).getValue();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		Iterator<String> itr = this._conf.keySet().iterator();
		while (itr.hasNext()) {
			String name = (String)itr.next();
			sb.append(name + " : " + this._conf.get(name).getValue() + "\n");
		}
		
		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		Conf conf = new Conf();
		System.out.println(conf.toString());
	}

}
