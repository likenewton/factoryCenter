package cn.yunovo.iov.factory.framework.dac;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.factory.framework.Contants;
import cn.yunovo.iov.factory.framework.LoginInfoUtil;
import cn.yunovo.iov.factory.framework.dac.bean.DataResource;
import cn.yunovo.iov.factory.framework.dac.bean.LoginUser;
import cn.yunovo.iov.factory.framework.dac.metadata.DacProperties;

public class DacHelper {

	/**
	 * 数据权限实体
	 */
	private static final ThreadLocal<DataResource> PROVIDER_LOCAL = new ThreadLocal<DataResource>();

	/**
	 * 是否跳过数据权限拦截
	 */
	private static final ThreadLocal<Boolean> IS_SKIP_LOCAL = new ThreadLocal<Boolean>();

	/**
	 * 与数据权限关联的用户信息实体
	 */
	private static ThreadLocal<LoginUser> USER_LOCAL = new ThreadLocal<LoginUser>();

	private void setDataAuthorityControl(String master, Integer userType, String tableName) {
		LoginUser user = USER_LOCAL.get();
		DataResource dataResource = null;

		if (1 == user.getUserType()) {
			// 机构用户
			dataResource = DataResource.create().mapperBy(Contants.TABLE_DAC_BRAND).providerBy(tableName).userId(user.getLoginName());
		} else if (2 == user.getUserType()) {
			// 工厂用户
			dataResource = DataResource.create().mapperBy(Contants.TABLE_DAC_FACTORY).providerBy(tableName).userId(user.getLoginName());
		} else if (3 == user.getUserType()) {
			// 渠道用户
			dataResource = DataResource.create().mapperBy(Contants.TABLE_DAC_CHANNEL).providerBy(tableName).userId(user.getLoginName());
		} else if (4 == user.getUserType()) {
			// 物流用户
			dataResource = DataResource.create().mapperBy(Contants.TABLE_DAC_FLOGISTICS).providerBy(tableName).userId(user.getLoginName());
		} else {
			// 平台用户
			dataResource = DataResource.create().mapperBy(Contants.TABLE_DAC_RESOURCE).providerBy(tableName).userId(user.getLoginName());
		}

		PROVIDER_LOCAL.set(dataResource);

	}

	/**
	 * @return true 跳过数据权限，false 执行数据权限
	 */
	public boolean isIntercept() {

		// 系统运行定时查询的情况
		if (null == LoginInfoUtil.LOGINUSER_LOCAL.get()) {
			return true;
		}

		// 平台用户不拦截
		if (0 == LoginInfoUtil.LOGINUSER_LOCAL.get().getUserType()) {
			if (null != IS_SKIP_LOCAL.get() && !IS_SKIP_LOCAL.get()) {
				return false;
			}
			return true;
		}

		// 没有设置是否跳过拦截
		if (null == IS_SKIP_LOCAL.get()) {
			return false;
		}

		// 设置是否跳过拦截
		if (IS_SKIP_LOCAL.get()) {
			return true;
		}

		// 默认拦截
		return false;
	}

	public DataResource getDataAuthorityControl() {
		return PROVIDER_LOCAL.get();
	}

	public boolean containsProvider(DacProperties dacProperties, Map<String, String> dataProviderMap, List<String> tables) {
		for (String table : tables) {
			if (dataProviderMap.containsKey(table)) {
				if (null != LoginInfoUtil.LOGINUSER_LOCAL.get()) {
					USER_LOCAL.set(LoginInfoUtil.LOGINUSER_LOCAL.get());
					setDataAuthorityControl(dacProperties.getMaster(), dacProperties.getUserType(), table);
					return false;
				}

			}
		}

		return true;

	}

	public boolean delete(String tableName, String master, Integer dataId, Integer userType) {
		LoginUser user = USER_LOCAL.get();
		if (null != user && (master.equals(user.getLoginName()) || user.getUserType() == userType)) {
			return true;
		}

		if (1 == user.getUserType()) {
			// 机构品牌用户
			DacResourceHelper.deleteBrandResource(dataId, tableName, user.getLoginName());
		} else if (2 == user.getUserType()) {
			// 工厂用户
			DacResourceHelper.deleteFactoryResource(dataId, tableName, user.getLoginName());
		} else if (3 == user.getUserType()) {
			// 渠道用户
			DacResourceHelper.deleteChannelResource(dataId, tableName, user.getLoginName());
		} else if (4 == user.getUserType()) {
			// 物流用户
			DacResourceHelper.deleteFlogisticsResource(dataId, tableName, user.getLoginName());
		} else {
			// 平台用户
			DacResourceHelper.deleteDataResource(dataId, tableName, user.getLoginName());
		}
		clearUser();
		return false;

	}

	public boolean insert(String tableName, String master, Integer dataId, Integer userType) {
		LoginUser user = USER_LOCAL.get();
		if (null != user && (master.equals(user.getLoginName()) || user.getUserType() == userType)) {
			return true;
		}

		if (1 == user.getUserType()) {
			// 机构品牌用户
			DacResourceHelper.insertBrandResource(tableName, dataId, user.getLoginName(), null);
		} else if (2 == user.getUserType()) {
			// 工厂用户
			DacResourceHelper.insertFactoryResource(tableName, dataId, user.getLoginName(), null);
		} else if (3 == user.getUserType()) {
			// 渠道用户
			DacResourceHelper.insertChannelResource(tableName, dataId, user.getLoginName(), null);
		} else if (4 == user.getUserType()) {
			// 物流用户
			DacResourceHelper.insertFlogisticsResource(tableName, dataId, user.getLoginName(), null);
		} else {
			// 平台用户
			DacResourceHelper.insertDataResource(tableName, dataId, user.getLoginName(), null);
		}

		clearUser();
		return true;
	}

	public void clearUser() {
		USER_LOCAL.remove();
	}

	public void clearProvider() {
		PROVIDER_LOCAL.remove();
	}

	public void clearSkip() {
		IS_SKIP_LOCAL.remove();
	}

	public static void skip() {
		IS_SKIP_LOCAL.set(true);
	}

	/**
	 * @param isSkip true 跳过数据权限，false 执行数据权限
	 */
	public static void skip(Boolean isSkip) {
		IS_SKIP_LOCAL.set(isSkip);
	}

	/**
	 * 提供清除权限数据
	 */
	public static void clear() {
		PROVIDER_LOCAL.remove();
		IS_SKIP_LOCAL.remove();
		USER_LOCAL.remove();
	}
}
