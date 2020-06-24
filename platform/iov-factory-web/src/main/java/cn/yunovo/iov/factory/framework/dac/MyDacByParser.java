package cn.yunovo.iov.factory.framework.dac;

import org.springframework.stereotype.Component;

import cn.yunovo.iov.boot.autoconfigure.dac.bean.DataResource;
import cn.yunovo.iov.boot.autoconfigure.dac.parser.DefaultDacByParser;

@Component
public class MyDacByParser extends DefaultDacByParser {

	/**
	 * 可以继承 DacByParser 自定义实现SQL解析
	 */
	@Override
	public String queryConverSql(String sql, DataResource dataResource) {
		return super.queryConverSql(sql, dataResource);
	}
}
