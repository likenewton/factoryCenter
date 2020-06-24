package cn.yunovo.iov.factory.framework.dac;

import org.springframework.stereotype.Component;

import com.github.ore.boot.context.SpringContext;

import cn.yunovo.iov.boot.autoconfigure.dac.DacHelper;
import cn.yunovo.iov.boot.autoconfigure.dac.bean.DataResource;
import cn.yunovo.iov.boot.autoconfigure.dac.bean.UserInfo;
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
import cn.yunovo.iov.factory.framework.Contants;

@Component
public class DacResourceHelper extends DacHelper {

	public static void insertFlogisticsResource(String tableName, Integer dataId, String userId, String sourceCreatorId) {
		FlogisticsResourceService flogisticsResourceService = SpringContext.getBean(FlogisticsResourceService.class);
		FlogisticsResourceDO flogisticsResourceDO = new FlogisticsResourceDO();
		flogisticsResourceDO.setDataId(dataId);
		flogisticsResourceDO.setDataProvider(tableName);
		flogisticsResourceDO.setCreatorId(userId);
		flogisticsResourceDO.setSourceCreatorId(sourceCreatorId);
		flogisticsResourceService.insertFlogisticsResource(flogisticsResourceDO);
	}

	public static void insertChannelResource(String tableName, Integer dataId, String userId, String sourceCreatorId) {
		ChannelResourceService channelResourceService = SpringContext.getBean(ChannelResourceService.class);
		ChannelResourceDO channelResourceDO = new ChannelResourceDO();
		channelResourceDO.setDataId(dataId);
		channelResourceDO.setDataProvider(tableName);
		channelResourceDO.setCreatorId(userId);
		channelResourceDO.setSourceCreatorId(sourceCreatorId);
		channelResourceService.insertChannelResource(channelResourceDO);
	}

	public static void insertFactoryResource(String tableName, Integer dataId, String userId, String sourceCreatorId) {
		FactoryResourceService factoryResourceService = SpringContext.getBean(FactoryResourceService.class);
		FactoryResourceDO factoryResourceDO = new FactoryResourceDO();
		factoryResourceDO.setDataId(dataId);
		factoryResourceDO.setDataProvider(tableName);
		factoryResourceDO.setCreatorId(userId);
		factoryResourceDO.setSourceCreatorId(sourceCreatorId);
		factoryResourceService.insertFactoryResource(factoryResourceDO);

	}

	public static void insertBrandResource(String tableName, Integer dataId, String userId, String sourceCreatorId) {
		BrandResourceService brandResourceService = SpringContext.getBean(BrandResourceService.class);
		BrandResourceDO brandResourceDO = new BrandResourceDO();
		brandResourceDO.setDataId(dataId);
		brandResourceDO.setDataProvider(tableName);
		brandResourceDO.setCreatorId(userId);
		brandResourceDO.setSourceCreatorId(sourceCreatorId);
		brandResourceService.insertBrandResource(brandResourceDO);
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

	private void deleteFlogisticsResource(Integer dataId, String provider, String userId) {
		FlogisticsResourceService flogisticsResourceService = SpringContext.getBean(FlogisticsResourceService.class);
		FlogisticsResourceDO flogisticsResourceDO = new FlogisticsResourceDO();
		flogisticsResourceDO.setDataId(dataId);
		flogisticsResourceDO.setDataProvider(provider);
		flogisticsResourceDO.setCreatorId(userId);
		flogisticsResourceService.deleteFlogisticsResource(flogisticsResourceDO);
	}

	private void deleteChannelResource(Integer dataId, String provider, String userId) {
		ChannelResourceService channelResourceService = SpringContext.getBean(ChannelResourceService.class);
		ChannelResourceDO channelResourceDO = new ChannelResourceDO();
		channelResourceDO.setDataId(dataId);
		channelResourceDO.setDataProvider(provider);
		channelResourceDO.setCreatorId(userId);
		channelResourceService.deleteChannelResource(channelResourceDO);
	}

	private void deleteFactoryResource(Integer dataId, String provider, String userId) {
		FactoryResourceService factoryResourceService = SpringContext.getBean(FactoryResourceService.class);
		FactoryResourceDO factoryResourceDO = new FactoryResourceDO();
		factoryResourceDO.setDataId(dataId);
		factoryResourceDO.setDataProvider(provider);
		factoryResourceDO.setCreatorId(userId);
		factoryResourceService.deleteFactoryResource(factoryResourceDO);

	}

	private boolean deleteDataResource(Integer dataId, String provider, String userId) {
		DataResourceService dataResourceService = SpringContext.getBean(DataResourceService.class);
		DataResourceDO dataResourceDO = new DataResourceDO();
		dataResourceDO.setDataId(dataId);
		dataResourceDO.setDataProvider(provider);
		dataResourceDO.setCreatorId(userId);
		dataResourceService.deleteDataResource(dataResourceDO);
		return false;
	}

	private boolean deleteBrandResource(Integer dataId, String provider, String userId) {
		BrandResourceService brandResourceService = SpringContext.getBean(BrandResourceService.class);
		BrandResourceDO brandResourceDO = new BrandResourceDO();
		brandResourceDO.setDataId(dataId);
		brandResourceDO.setDataProvider(provider);
		brandResourceDO.setCreatorId(userId);
		brandResourceService.deleteBrandResource(brandResourceDO);
		return false;
	}

	@Override
	public void deleteDataAuthorityControl(String tableName, Integer dataId) {
		deleteBrandResource(dataId, tableName, null);
		deleteFactoryResource(dataId, tableName, null);
		deleteChannelResource(dataId, tableName, null);
		deleteFlogisticsResource(dataId, tableName, null);
		deleteDataResource(dataId, tableName, null);
	}

	@Override
	public void insertDataAuthorityControl(String tableName, Integer dataId) {
		UserInfo user = DacHelper.getUser();
		if (1 == user.getUserType()) {
			// 机构品牌用户
			insertBrandResource(tableName, dataId, user.getLoginName(), null);
		} else if (2 == user.getUserType()) {
			// 工厂用户
			insertFactoryResource(tableName, dataId, user.getLoginName(), null);
		} else if (3 == user.getUserType()) {
			// 渠道用户
			insertChannelResource(tableName, dataId, user.getLoginName(), null);
		} else if (4 == user.getUserType()) {
			// 物流用户
			insertFlogisticsResource(tableName, dataId, user.getLoginName(), null);
		} else {
			// 平台用户
			insertDataResource(tableName, dataId, user.getLoginName(), null);
		}

	}

	@Override
	public DataResource setDataAuthorityMapper(String tableName) {
		UserInfo user = DacHelper.getUser();
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
		return dataResource;
	}

	@Override
	public boolean isNotIntercept() {
		UserInfo user = DacHelper.getUser();
		if (0 == user.getUserType()) {
			return true;
		}
		return false;
	}
}
