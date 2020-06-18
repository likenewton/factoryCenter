package cn.yunovo.iov.factory.framework.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunshine.dcda.basecomponent.enums.StatusEnum;
import org.sunshine.dcda.system.service.model.SystemResourceQueryBean;
import org.sunshine.dcda.system.service.model.SystemResourceVo;
import org.sunshine.dcda.view.system.viewcomponent.ISystemResourceViewComponent;

import com.github.ore.framework.web.api.ResultEntity;
import com.github.ore.framework.web.utils.ResultMessageUtils;
import com.google.gson.Gson;

import cn.yunovo.iov.boot.autoconfigure.cas.h5.bean.LoginInfo;
import cn.yunovo.iov.factory.framework.Contants;
import cn.yunovo.iov.factory.framework.LoginInfoUtil;

@Component
@WebFilter(urlPatterns = { "/*", "" }, filterName = "authFilter")
public class AuthFilter implements Filter {
	private Logger logger = LoggerFactory.getLogger(AuthFilter.class);
	
	private static String SITECODE = "FACTORY";
	
	private static String ALLRESOURCEMAP = "allResourceMap";
	
	@Autowired
	private LoginInfoUtil loginInfoUtil;

	@Resource
	private ISystemResourceViewComponent systemResourceViewComponent;

	private List<SystemResourceVo> allResourceBySiteCode(String site_code) {

		SystemResourceQueryBean queryBean = new SystemResourceQueryBean();
		queryBean.setSiteCode("FACTORY");
		queryBean.setStatus(StatusEnum.USING.getKey());
		queryBean.setPageNo(1);
		queryBean.setPageSize(999999);

		List<SystemResourceVo> resourceList = null;
		try {
			resourceList = systemResourceViewComponent.querySystemResourceList(queryBean);
		} catch (Exception e) {
			logger.error("[allResourceBySiteCode][exception]params={site_code:{}},exception:{}", site_code);
		}

		return resourceList;
	}

	private void returnJson(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain, String uri, Map<String, SystemResourceVo> allResourceMap, Map<String, SystemResourceVo> existResourceMap)
			throws IOException, ServletException {
		
		if (null != allResourceMap.get(uri)) {
			if (null == existResourceMap.get(uri)) {
				ResultEntity<Integer> result = new ResultEntity<Integer>();
				result.setCode(ResultMessageUtils.splitCode(Contants.BIZ_20009));
				result.setMsg(ResultMessageUtils.splitMsg(Contants.BIZ_20009));

				PrintWriter writer = null;
				servletResponse.setCharacterEncoding("UTF-8");
				servletResponse.setContentType("text/html; charset=utf-8");
				try {
					Gson gson = new Gson();
					writer = servletResponse.getWriter();
					writer.print(gson.toJson(result));

				} catch (IOException e) {
					logger.error("response error", e);
				} finally {
					if (writer != null)
						writer.close();
				}
			} else {
				filterChain.doFilter(servletRequest, servletResponse);
			}
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

	private void extracted(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain, HttpServletRequest request, String uri) {
		LoginInfo info = loginInfoUtil.getLoginBaseInfo();
		try {
			
			Object allResourceObj = request.getSession().getAttribute(ALLRESOURCEMAP);
			if(null == allResourceObj) {
				//allResourceObj = request.getServletContext().getAttribute(ALLRESOURCEMAP);
			}
			
			Object existResourceObj = request.getSession().getAttribute(info.getId());
			if(null == existResourceObj) {
				//existResourceObj = request.getServletContext().getAttribute(info.getId());
			}
			
			if (null == existResourceObj) {
				
				Map<String, SystemResourceVo> allResourceMap = new HashMap<String, SystemResourceVo>();
				List<SystemResourceVo> allResourceList = allResourceBySiteCode(SITECODE);
				for (SystemResourceVo vo : allResourceList) {
					allResourceMap.put(vo.getResUrl(), vo);
				}
				
				Map<String, SystemResourceVo> existResourceMap = new HashMap<String, SystemResourceVo>();
				List<SystemResourceVo> existResourceList = systemResourceViewComponent.querySystemResourceByUserIdAndSiteCode(info.getId(), SITECODE);
				for (SystemResourceVo vo : existResourceList) {
					existResourceMap.put(vo.getResUrl(), vo);
				}
				
				request.getServletContext().setAttribute(ALLRESOURCEMAP, allResourceMap);
				request.getSession().setAttribute(ALLRESOURCEMAP, allResourceMap);
				
				request.getServletContext().setAttribute(info.getId(), existResourceMap);
				request.getSession().setAttribute(info.getId(), existResourceMap);
				
				returnJson(servletRequest, servletResponse, filterChain, uri, allResourceMap, existResourceMap);

			} else {
				@SuppressWarnings("unchecked")
				Map<String, SystemResourceVo> allResourceMap = (Map<String, SystemResourceVo>) allResourceObj;
				
				@SuppressWarnings("unchecked")
				Map<String, SystemResourceVo> existResourceMap = (Map<String, SystemResourceVo>) existResourceObj;
				
				returnJson(servletRequest, servletResponse, filterChain, uri, allResourceMap, existResourceMap);
			}

		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String uri = request.getRequestURI();
		if (0 < uri.indexOf("/sso")) {
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			extracted(servletRequest, servletResponse, filterChain, request, uri);
		}
	}

}
