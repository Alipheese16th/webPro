package com.kist.portal.sysconf.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.security.core.context.SecurityContextHolder;

import com.kist.portal.common.login.dto.UserDetailDto;
import com.kist.portal.common.utils.ObjectUtil;

@Intercepts({
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class,
				ResultHandler.class }),
		@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class })
})
public class MybatisExecuteInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// log.debug(" MybatisExecuteInterceptor query Interceptor ");
		Map<String, Object> properties = new HashMap<String, Object>();
		Object[] args = invocation.getArgs();
		Object param = (Object) args[1];

		// 세션존재 확인
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			return invocation.proceed();
		}

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetailDto userSession = (UserDetailDto) principal;

		if (userSession == null) {
			return invocation.proceed();
		}
		Map<String, Object> sessionMap = ObjectUtil.convertObjectToMap(userSession);
		// sessionMap.put("pageId", ContextHolder.getContext().getPageId());
		// sessionMap.put("dataServiceId", ContextHolder.getContext().getDataServiceId());
		properties.put("session", sessionMap);
		MappedStatement statement = (MappedStatement) args[0];
		BoundSql boundSql = statement.getBoundSql(args[1]);
		String sql = boundSql.getSql().toUpperCase();
		List<ParameterMapping> paramMappings = boundSql.getParameterMappings();

		if (param == null) {
			args[1] = new HashMap<String, Object>();
			((Map<String, Object>) args[1]).putAll(properties);
		} else if (param instanceof Map) {
			((Map<String, Object>) param).putAll(properties);
		} else if (param instanceof String) {
			String temp = (String) param;
			String paramName = "";
			for (ParameterMapping paramMapping : paramMappings) {
				String property = paramMapping.getProperty();
				if (!property.startsWith("session.")) {
					paramName = property;
					break;
				}
			}
			args[1] = new HashMap<String, Object>();
			((Map<String, Object>) args[1]).put(paramName, temp);
			((Map<String, Object>) args[1]).putAll(properties);
		} else if (param instanceof Object) {
			args[1] = new HashMap<String, Object>();
			Map<String, Object> paramMap = ObjectUtil.convertObjectToMap(param);
			((Map<String, Object>) args[1]).putAll(paramMap);
			((Map<String, Object>) args[1]).putAll(properties);
		}

		// 조회 조건중에서 % 제거, trim
		if ("query".equals(invocation.getMethod().getName())) {
			if (sql.indexOf("PERCENT_CHAR_ALLOWED") < 0) {
				for (Map.Entry<String, Object> entry : ((Map<String, Object>) args[1]).entrySet()) {
					if (entry.getValue() != null && entry.getValue() instanceof String) {
						((Map<String, Object>) args[1]).put(entry.getKey(),
								entry.getValue().toString().replace("%", "").trim());
					}
				}
			}
		}

		Object returnObj = invocation.proceed();
		// 확인 필요 (ConstantConfig 확인 불가능.)
		// if (sql.indexOf("MAX_QUERY_SIZE_IGNORE") < 0) {
		// if (returnObj instanceof List) {
		// if (((List) returnObj).size() > ConstantConfig.MAX_QUERY_SIZE) {
		// throw new MaxQuerySizeExceedException("");
		// }
		// }
		// }
		return returnObj;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

}
