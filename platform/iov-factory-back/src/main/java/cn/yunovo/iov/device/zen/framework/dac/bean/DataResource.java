package cn.yunovo.iov.device.zen.framework.dac.bean;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DataResource {
	
	private String mapperBy;

	private String providerBy;
	
	private String userId;
	
	public static DataResource create() {
		DataResource dac = new DataResource();
		return dac;
	}
	
	public DataResource mapperBy(String mapperBy) {
		this.mapperBy = mapperBy;
		return this;
	}
	
	public DataResource providerBy(String providerBy) {
		this.providerBy = providerBy;
		return this;
	}
	
	public DataResource userId(String userId) {
		this.userId = userId;
		return this;
	}
	
	
}
