package cn.yunovo.iov.factory.framework.dac;

import com.github.ore.boot.context.SpringContext;

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

public class DacResourceHelper {

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

	
	public static void deleteFlogisticsResource(Integer dataId, String provider, String userId) {
		FlogisticsResourceService flogisticsResourceService = SpringContext.getBean(FlogisticsResourceService.class);
		FlogisticsResourceDO flogisticsResourceDO = new FlogisticsResourceDO();
		flogisticsResourceDO.setDataId(dataId);
		flogisticsResourceDO.setDataProvider(provider);
		flogisticsResourceDO.setCreatorId(userId);
		flogisticsResourceService.deleteFlogisticsResource(flogisticsResourceDO);
	}

	public static void deleteChannelResource(Integer dataId, String provider, String userId) {
		ChannelResourceService channelResourceService = SpringContext.getBean(ChannelResourceService.class);
		ChannelResourceDO channelResourceDO = new ChannelResourceDO();
		channelResourceDO.setDataId(dataId);
		channelResourceDO.setDataProvider(provider);
		channelResourceDO.setCreatorId(userId);
		channelResourceService.deleteChannelResource(channelResourceDO);
	}

	public static void deleteFactoryResource(Integer dataId, String provider, String userId) {
		FactoryResourceService factoryResourceService = SpringContext.getBean(FactoryResourceService.class);
		FactoryResourceDO factoryResourceDO = new FactoryResourceDO();
		factoryResourceDO.setDataId(dataId);
		factoryResourceDO.setDataProvider(provider);
		factoryResourceDO.setCreatorId(userId);
		factoryResourceService.deleteFactoryResource(factoryResourceDO);

	}
	
	public static boolean deleteDataResource(Integer dataId, String provider, String userId) {
		DataResourceService dataResourceService = SpringContext.getBean(DataResourceService.class);
		DataResourceDO dataResourceDO = new DataResourceDO();
		dataResourceDO.setDataId(dataId);
		dataResourceDO.setDataProvider(provider);
		dataResourceDO.setCreatorId(userId);
		dataResourceService.deleteDataResource(dataResourceDO);
		return false;
	}

	public static boolean deleteBrandResource(Integer dataId, String provider, String userId) {
		BrandResourceService brandResourceService = SpringContext.getBean(BrandResourceService.class);
		BrandResourceDO brandResourceDO = new BrandResourceDO();
		brandResourceDO.setDataId(dataId);
		brandResourceDO.setDataProvider(provider);
		brandResourceDO.setCreatorId(userId);
		brandResourceService.deleteBrandResource(brandResourceDO);
		return false;
	}

	public static void insert(String tableName, Integer dataId, String userId, String sourceCreatorId, Boolean insertBrand, Boolean insertFactory, Boolean insertChannel, Boolean insertFlogistics) {
		if (insertBrand) {
			insertBrandResource(tableName, dataId, userId, sourceCreatorId);
		}
		if (insertFactory) {
			insertFactoryResource(tableName, dataId, userId, sourceCreatorId);
		}

		if (insertChannel) {
			insertChannelResource(tableName, dataId, userId, sourceCreatorId);

		}
		if (insertFlogistics) {
			insertFlogisticsResource(tableName, dataId, userId, sourceCreatorId);
		}
	}
}
