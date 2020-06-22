package cn.yunovo.iov.factory.framework;

import com.github.ore.framework.web.api.ResultMessage;

public class Contants extends ResultMessage {

	/**
	 * 自定义业务返回码：BIZ_2XXXX,BIZ_3XXXX,BIZ_4XXXX,BIZ_5XXXX 等
	 */

	public static final String BIZ_20001 = "20001^请求参数不能为空";

	public static final String BIZ_20002 = "20002^还存在引用";

	public static final String BIZ_20003 = "20003^已经存在该名称";

	public static final String BIZ_20004 = "20004^Json格式错误";

	public static final String BIZ_20006 = "20006^上传文件格式错误,只能上传文件格式为:xls,xlsx!";

	public static final String BIZ_20007 = "20007^定时表达式错误";

	public static final String BIZ_20008 = "20008^通过$url$获取用户信息失败!";

	public static final String BIZ_20009 = "20009^您暂未分配该功能使用权限,如需使用,请联系管理员进行权限分配!";

	public static final String BIZ_20010 = "20010^上传文件内容格式错误!";
	
	public static final String BIZ_20011 = "20011^填写的贴片厂不存在!";
	
	public static final String BIZ_20012 = "20012^填写的组装厂不存在!";
	
	public static final String BIZ_20013 = "20013^请填写组装厂!";
	
	public static final String BIZ_20014 = "20014^请输入正确的云智码!";

	public static final String TABLE_DAC_BRAND = "t_dac_brand";
	public static final String TABLE_DAC_CHANNEL = "t_dac_channel";
	public static final String TABLE_DAC_FACTORY = "t_dac_factory";
	public static final String TABLE_DAC_RESOURCE = "t_dac_resource";
	public static final String TABLE_DAC_FLOGISTICS = "t_dac_flogistics";
	
	public static final String TABLE_STATISTICS_PASTER = "t_statistics_paster";
	public static final String TABLE_STATISTICS_ASSEMBLE = "t_statistics_assemble";
	public static final String TABLE_STATISTICS_SHIPPING = "t_statistics_shipping";
	public static final String TABLE_CHANNEL_INFO = "t_channel_info";
	public static final String TABLE_SHIPPING_LIST = "t_shipping_list";

}
