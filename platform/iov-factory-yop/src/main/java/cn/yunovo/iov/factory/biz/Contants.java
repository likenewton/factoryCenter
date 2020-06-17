package cn.yunovo.iov.factory.biz;

public class Contants {

	public static String SOFTWARE_LIST = "SOFTWARE_LIST";

	/**
	 * 设备SN变更, 设备未入库
	 *
	 **/
	public static String DEVICE_SN_UPDATE_DEVICE_NOT_EXIST = "3010101";

	/**
	 * 设备SN变更, 变更SN 与 原SN 一样
	 *
	 **/
	public static String DEVICE_SN_UPDATE_SN_DUPLICATE = "3010102";

	/**
	 * 设备ICCID变更, 设备未入库
	 *
	 **/
	public static String DEVICE_INFO_UPDATE_DEVICE_NOT_EXIST = "3010201";

	/**
	 * 设备ICCID变更, 设备ICCID已存在
	 *
	 **/
	public static String DEVICE_INFO_UPDATE_ICCID_EXIST = "3010202";
	
	/**
	 * 设备SN变更, 设备SN已存在
	 *
	 **/
	public static String DEVICE_INFO_NOT_UPDATE_SN = "3010103";
}
