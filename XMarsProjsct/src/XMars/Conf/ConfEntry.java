package XMars.Conf;

public class ConfEntry {
	private String resourceName;
	private String resourceFileName;
	private String resourceSize;
	private String resourceValue;
	private String resourceType;
	
	public String getResourceSize() {
		return resourceSize;
	}
	public void setResourceSize(String resourceSize) {
		this.resourceSize = resourceSize;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getResourceFileName() {
		return resourceFileName;
	}
	public void setResourceFileName(String resourceFileName) {
		this.resourceFileName = resourceFileName;
	}
	public String getResourceValue() {
		return resourceValue;
	}
	public void setResourceValue(String resourceValue) {
		this.resourceValue = resourceValue;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
}
