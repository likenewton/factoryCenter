package cn.yunovo.iov.factory.framework.dac;

import java.util.Map;

import com.github.ore.boot.context.SpringContext;
import com.github.pagehelper.PageHelper;

import cn.yunovo.iov.factory.biz.dac.brand.model.BrandResourceDO;
import cn.yunovo.iov.factory.biz.dac.brand.service.BrandResourceService;
import cn.yunovo.iov.factory.biz.dac.channel.model.ChannelResourceDO;
import cn.yunovo.iov.factory.biz.dac.channel.service.ChannelResourceService;
import cn.yunovo.iov.factory.biz.dac.factory.model.FactoryResourceDO;
import cn.yunovo.iov.factory.biz.dac.factory.service.FactoryResourceService;
import cn.yunovo.iov.factory.biz.dac.flogistics.model.FlogisticsResourceDO;
import cn.yunovo.iov.factory.biz.dac.flogistics.service.FlogisticsResourceService;
import cn.yunovo.iov.factory.biz.dac.resource.model.DataResourceDO;
import cn.yunovo.iov.factory.biz.dac.resource.service.DataResourceService;
import cn.yunovo.iov.factory.biz.dac.user.model.DacUserDTO;
import cn.yunovo.iov.factory.biz.dac.user.model.DacUserQuery;
import cn.yunovo.iov.factory.biz.dac.user.service.DacUserService;
import cn.yunovo.iov.factory.framework.Contants;
import cn.yunovo.iov.factory.framework.dac.bean.DataResource;
import cn.yunovo.iov.factory.framework.dac.bean.LoginUser;
import cn.yunovo.iov.factory.framework.dac.parser.DacByParser;

public class DacHelper {

	private static final ThreadLocal<DataResource> PROVIDER_LOCAL = new ThreadLocal<DataResource>();

	private static ThreadLocal<LoginUser> USERID_LOCAL = new ThreadLocal<LoginUser>();

	public void clearUser() {
		USERID_LOCAL.remove();
	}

	public boolean skip(String master, Integer userType) {

		LoginUser user = USERID_LOCAL.get();
		if (null == user || master.equals(user.getUserId()) || userType == user.getUserType()) {
			return true;
		}
		return false;

	}

	public boolean skipQuery(String sql, Map<String, String> dataProviderMap, String master, Integer userType) {
		String tableName = DacByParser.getTablesNames(sql);
		if (dataProviderMap.containsKey(tableName)) {
			LoginUser user = USERID_LOCAL.get();
			if (null != user && (master.equals(user.getUserId()) || user.getUserType() == userType)) {
				return true;
			}

			if (1 == user.getUserType()) {
				// 机构用户
				DataResource dac = DataResource.create().mapperBy(Contants.TABLE_DAC_BRAND).providerBy(tableName).userId(user.getUserId());
				DacHelper.dataAuthorityControl(dac, master);
			} else if (2 == user.getUserType()) {
				// 工厂用户
				DataResource dac = DataResource.create().mapperBy(Contants.TABLE_DAC_FACTORY).providerBy(tableName).userId(user.getUserId());
				DacHelper.dataAuthorityControl(dac, master);
			} else if (3 == user.getUserType()) {
				// 渠道用户
				DataResource dac = DataResource.create().mapperBy(Contants.TABLE_DAC_CHANNEL).providerBy(tableName).userId(user.getUserId());
				DacHelper.dataAuthorityControl(dac, master);
			} else if (4 == user.getUserType()) {
				// 物流用户
				DataResource dac = DataResource.create().mapperBy(Contants.TABLE_DAC_FLOGISTICS).providerBy(tableName).userId(user.getUserId());
				DacHelper.dataAuthorityControl(dac, master);
			} else {
				// 平台用户
				DataResource dac = DataResource.create().mapperBy(Contants.TABLE_DAC_RESOURCE).providerBy(tableName).userId(user.getUserId());
				DacHelper.dataAuthorityControl(dac, master);
			}

			return false;
		}
		return true;
	}

	public boolean skipInsert(String sql, Map<String, String> dataProviderMap, String master, Integer dataId, Integer userType) {
		String tableName = DacByParser.getTablesNames(sql);
		if (dataProviderMap.containsKey(tableName)) {
			LoginUser user = USERID_LOCAL.get();
			if (null != user && (master.equals(user.getUserId()) || user.getUserType() == userType)) {
				return true;
			}

			if (userType == user.getUserType()) {
				// 平台用户
				insertDataResource(tableName, dataId, user.getUserId(), null);
			} else if (1 == user.getUserType()) {
				// 机构品牌用户
				insertBrandResource(tableName, dataId, user.getUserId(), null, null);
			} else if (2 == user.getUserType()) {
				// 工厂用户
				insertFactoryResource(tableName, dataId, user.getUserId(), null, null, null);
			} else if (3 == user.getUserType()) {
				// 渠道用户
				insertChannelResource(tableName, dataId, user.getUserId(), null, null);
			} else if (4 == user.getUserType()) {
				// 物流用户
				insertFlogisticsResource(tableName, dataId, user.getUserId(), null, null);
			}

			clearUser();
			return false;
		}
		return true;

	}

	public boolean skipDelete(String sql, Map<String, String> dataProviderMap, String master, Integer dataId, Integer userType) {
		String tableName = DacByParser.getTablesNames(sql);
		if (dataProviderMap.containsKey(tableName)) {
			LoginUser user = USERID_LOCAL.get();
			if (null != user && (userType == user.getUserType() || master.equals(user.getUserId()))) {
				return true;
			}
			DataResourceService dataResourceService = SpringContext.getBean(DataResourceService.class);
			DataResourceDO dataResourceDO = new DataResourceDO();
			dataResourceDO.setDataId(dataId);
			dataResourceDO.setDataProvider(tableName);
			dataResourceDO.setCreatorId(user.getUserId());
			dataResourceService.deleteDataResource(dataResourceDO);
			clearUser();
			return false;
		}
		return true;

	}

	public static void dataAuthorityControl(DataResource dataResource, String master) {
		if (!master.equals(dataResource.getUserId())) {
			PROVIDER_LOCAL.set(dataResource);
		}
	}

	public static void insertDataResource(String tableName, Integer dataId, String userId, String sourceCreatorId) {
		DataResourceService dataResourceService = SpringContext.getBean(DataResourceService.class);
		DataResourceDO dataResourceDO = new DataResourceDO();
		dataResourceDO.setDataId(dataId);
		dataResourceDO.setDataProvider(tableName);
		dataResourceDO.setCreatorId(userId);
		dataResourceDO.setSourceCreatorId(sourceCreatorId);
		dataResourceService.insertDataResource(dataResourceDO);
	}

	public static void insertBrandResource(String tableName, Integer dataId, String userId, String sourceCreatorId, Integer userType) {
		if (null != userType && 1 == userType) {
			return;
		}
		BrandResourceService brandResourceService = SpringContext.getBean(BrandResourceService.class);
		BrandResourceDO brandResourceDO = new BrandResourceDO();
		brandResourceDO.setDataId(dataId);
		brandResourceDO.setDataProvider(tableName);
		brandResourceDO.setCreatorId(userId);
		brandResourceDO.setSourceCreatorId(sourceCreatorId);
		brandResourceService.insertBrandResource(brandResourceDO);
	}

	public static void insertFlogisticsResource(String tableName, Integer dataId, String userId, String sourceCreatorId, Integer userType) {
		if (null != userType && 4 == userType) {
			return;
		}
		FlogisticsResourceService flogisticsResourceService = SpringContext.getBean(FlogisticsResourceService.class);
		FlogisticsResourceDO flogisticsResourceDO = new FlogisticsResourceDO();
		flogisticsResourceDO.setDataId(dataId);
		flogisticsResourceDO.setDataProvider(tableName);
		flogisticsResourceDO.setCreatorId(userId);
		flogisticsResourceDO.setSourceCreatorId(sourceCreatorId);
		flogisticsResourceService.insertFlogisticsResource(flogisticsResourceDO);
	}

	public static void insertChannelResource(String tableName, Integer dataId, String userId, String sourceCreatorId, Integer userType) {
		if (null != userType && 3 == userType) {
			return;
		}
		ChannelResourceService channelResourceService = SpringContext.getBean(ChannelResourceService.class);
		ChannelResourceDO channelResourceDO = new ChannelResourceDO();
		channelResourceDO.setDataId(dataId);
		channelResourceDO.setDataProvider(tableName);
		channelResourceDO.setCreatorId(userId);
		channelResourceDO.setSourceCreatorId(sourceCreatorId);
		channelResourceService.insertChannelResource(channelResourceDO);
	}

	public static void insertFactoryResource(String tableName, Integer dataId, String userId, String sourceCreatorId, Integer userType, String userMapper) {
		if (null != userType && 2 == userType) {
			return;
		}

		DacUserService dacUserService = SpringContext.getBean(DacUserService.class);
		DacUserQuery dacUserQuery = new DacUserQuery();
		dacUserQuery.setUserMapper(userMapper);
		dacUserQuery.setUserId(userId);
		dacUserQuery.setUserType(2);
		DacUserDTO dacUserDTO = dacUserService.queryDacUser(dacUserQuery);
		if (null != dacUserDTO) {
			FactoryResourceService factoryResourceService = SpringContext.getBean(FactoryResourceService.class);
			FactoryResourceDO factoryResourceDO = new FactoryResourceDO();
			factoryResourceDO.setDataId(dataId);
			factoryResourceDO.setDataProvider(tableName);
			factoryResourceDO.setCreatorId(dacUserDTO.getUserId());
			factoryResourceDO.setSourceCreatorId(sourceCreatorId);
			factoryResourceService.insertFactoryResource(factoryResourceDO);
		}

	}

	public static void setUser(LoginUser user) {
		USERID_LOCAL.set(user);
	}

	public DataResource getDataAuthorityControl() {
		return PROVIDER_LOCAL.get();
	}

	public void clearProvider() {
		PROVIDER_LOCAL.remove();
	}
}
