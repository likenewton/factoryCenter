package cn.yunovo.iov.device.zen.biz.dac.user.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.dac.user.manager.DacUserManager;
import cn.yunovo.iov.device.zen.biz.dac.user.model.DacUserVO;
import cn.yunovo.iov.device.zen.biz.dac.user.model.DacUserDO;
import cn.yunovo.iov.device.zen.biz.dac.user.model.DacUserDTO;
import cn.yunovo.iov.device.zen.biz.dac.user.model.DacUserQuery;
import cn.yunovo.iov.device.zen.biz.dac.user.service.DacUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class DacUserServiceImpl implements DacUserService {

	@Autowired
	private DacUserManager dacUserManager;

	@Override
	public DacUserDTO getDacUserById(Integer id) {
		return dacUserManager.getDacUserById(id);
	}
	
	@Override
	public DacUserDTO queryDacUser(DacUserQuery dacUserQuery) {
		return dacUserManager.queryDacUser(dacUserQuery);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[]信息")
	public Object selectDacUser(DacUserQuery dacUserQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;

		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误,Mybatis的分页拦截器和排序拦截器冲突]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<DacUserDTO> dacUserBOList = dacUserManager.selectDacUser(dacUserQuery, conditionMap);
		List<DacUserVO> dacUserVOList = BeanMapper.mapList(dacUserBOList, DacUserVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return dacUserVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(dacUserVOList);
			return pInfo;
		}

		// 没符合条件查询
		return dacUserVOList;
	}
	@Override
	public Integer insertDacUser(DacUserDO dacUserDO) {
		return dacUserManager.insertDacUser(dacUserDO);
	}

	@Override
	public Integer deleteDacUserById(Integer id) {
		return dacUserManager.deleteDacUserById(id);
	}

	@Override
	public Integer updateDacUser(DacUserDO dacUserDO) {
		return dacUserManager.updateDacUser(dacUserDO);
	}
	
	@Override
	public Integer deleteDacUser(DacUserDO dacUserDO){
		return dacUserManager.deleteDacUser(dacUserDO);
	}
}
