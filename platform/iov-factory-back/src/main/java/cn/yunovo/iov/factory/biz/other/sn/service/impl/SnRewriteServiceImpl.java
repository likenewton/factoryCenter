package cn.yunovo.iov.factory.biz.other.sn.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.other.sn.manager.SnRewriteManager;
import cn.yunovo.iov.factory.biz.other.sn.model.SnRewriteDO;
import cn.yunovo.iov.factory.biz.other.sn.model.SnRewriteDTO;
import cn.yunovo.iov.factory.biz.other.sn.model.SnRewriteQuery;
import cn.yunovo.iov.factory.biz.other.sn.model.SnRewriteVO;
import cn.yunovo.iov.factory.biz.other.sn.service.SnRewriteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class SnRewriteServiceImpl implements SnRewriteService {

	@Autowired
	private SnRewriteManager snRewriteManager;

	@Override
	public SnRewriteDTO getSnRewriteById(Integer id) {
		return snRewriteManager.getSnRewriteById(id);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[]信息")
	public Object selectSnRewrite(SnRewriteQuery snRewriteQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;

		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<SnRewriteDTO> snRewriteBOList = snRewriteManager.selectSnRewrite(snRewriteQuery, conditionMap);
		List<SnRewriteVO> snRewriteVOList = BeanMapper.mapList(snRewriteBOList, SnRewriteVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return snRewriteVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(snRewriteVOList);
			return pInfo;
		}

		// 没符合条件查询
		return snRewriteVOList;
	}
	@Override
	public Integer insertSnRewrite(SnRewriteDO snRewriteDO) {
		return snRewriteManager.insertSnRewrite(snRewriteDO);
	}

	@Override
	public Integer deleteSnRewriteById(Integer id) {
		return snRewriteManager.deleteSnRewriteById(id);
	}

	@Override
	public Integer updateSnRewrite(SnRewriteDO snRewriteDO) {
		return snRewriteManager.updateSnRewrite(snRewriteDO);
	}
}
