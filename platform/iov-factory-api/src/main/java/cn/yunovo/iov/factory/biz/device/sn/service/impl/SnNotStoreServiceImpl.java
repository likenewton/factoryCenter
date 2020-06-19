package cn.yunovo.iov.factory.biz.device.sn.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.device.sn.manager.SnNotStoreManager;
import cn.yunovo.iov.factory.biz.device.sn.model.SnNotStoreVO;
import cn.yunovo.iov.factory.biz.device.sn.model.SnNotStoreDO;
import cn.yunovo.iov.factory.biz.device.sn.model.SnNotStoreDTO;
import cn.yunovo.iov.factory.biz.device.sn.model.SnNotStoreQuery;
import cn.yunovo.iov.factory.biz.device.sn.service.SnNotStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class SnNotStoreServiceImpl implements SnNotStoreService {

	@Autowired
	private SnNotStoreManager snNotStoreManager;

	@Override
	public SnNotStoreDTO getSnNotStoreById(Integer id) {
		return snNotStoreManager.getSnNotStoreById(id);
	}
	
	@Override
	public SnNotStoreDTO querySnNotStore(SnNotStoreQuery snNotStoreQuery) {
		return snNotStoreManager.querySnNotStore(snNotStoreQuery);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[测试上报数据时，SN未入库记录]信息")
	public Object selectSnNotStore(SnNotStoreQuery snNotStoreQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;

		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误,Mybatis的分页拦截器和排序拦截器冲突]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<SnNotStoreDTO> snNotStoreBOList = snNotStoreManager.selectSnNotStore(snNotStoreQuery, conditionMap);
		List<SnNotStoreVO> snNotStoreVOList = BeanMapper.mapList(snNotStoreBOList, SnNotStoreVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return snNotStoreVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(snNotStoreVOList);
			return pInfo;
		}

		// 没符合条件查询
		return snNotStoreVOList;
	}
	@Override
	public Integer insertSnNotStore(SnNotStoreDO snNotStoreDO) {
		return snNotStoreManager.insertSnNotStore(snNotStoreDO);
	}

	@Override
	public Integer deleteSnNotStoreById(Integer id) {
		return snNotStoreManager.deleteSnNotStoreById(id);
	}

	@Override
	public Integer updateSnNotStore(SnNotStoreDO snNotStoreDO) {
		return snNotStoreManager.updateSnNotStore(snNotStoreDO);
	}
	
	@Override
	public Integer deleteSnNotStore(SnNotStoreDO snNotStoreDO){
		return snNotStoreManager.deleteSnNotStore(snNotStoreDO);
	}
}
