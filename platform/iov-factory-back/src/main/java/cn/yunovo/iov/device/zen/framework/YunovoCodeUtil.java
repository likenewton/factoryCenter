package cn.yunovo.iov.device.zen.framework;

public class YunovoCodeUtil {

	public static String getArea(String area) {
		int index = area.indexOf("省");
		if (0 < index) {
			area = area.substring(0, index + 1);
		}

		index = area.indexOf("自治区");
		if (0 < index) {
			area = area.substring(0, index + 3);
		}

		index = area.indexOf("特别行政区");
		if (0 < index) {
			area = area.substring(0, index + 5);
		}

		index = area.indexOf("北京");
		if (0 <= index) {
			area = area.substring(0, index + 2);
		}

		index = area.indexOf("天津");
		if (0 <= index) {
			area = area.substring(0, index + 2);
		}

		index = area.indexOf("上海");
		if (0 <= index) {
			area = area.substring(0, index + 2);
		}

		index = area.indexOf("重庆");
		if (0 <= index) {
			area = area.substring(0, index + 2);
		}
		return area;
	}
	
	public static void main(String[] args) {
		//String code = "LLA BC AB AB A 234 9J 116";
		String codes = "LLABCABABA2349J116";
		int length = codes.length();
		for(int i=0;i<length;i++) {
			char c = codes.charAt(i);
			System.out.println(c);
		}
	}
}
