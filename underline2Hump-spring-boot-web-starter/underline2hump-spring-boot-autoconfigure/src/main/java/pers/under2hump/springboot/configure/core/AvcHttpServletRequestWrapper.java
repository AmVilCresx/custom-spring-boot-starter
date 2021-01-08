package pers.under2hump.springboot.configure.core;

import org.springframework.util.CollectionUtils;
import pers.under2hump.springboot.configure.utils.ParameterUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @description 处理 String 或 基本类型及其包装类型的参数
 * @author AmVilCresx
 */
public class AvcHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, String[]> modifiableParameters;
    private Map<String, String[]> allParameters = null;

    public AvcHttpServletRequestWrapper(HttpServletRequest request, Map<String, String[]> additionalParams) {
        super(request);
        modifiableParameters = new TreeMap<>();
        modifiableParameters.putAll(additionalParams);
    }

    @Override
    public String getParameter(final String name) {
        String[] strings = getParameterMap().get(name);
        if (strings != null) {
            return strings[0];
        }
        return super.getParameter(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        if (allParameters == null) {
            allParameters = new TreeMap<>();
            // 兼容2种格式
            allParameters.putAll(super.getParameterMap());
            allParameters.putAll(dealUnderLineParam(super.getParameterMap()));
            allParameters.putAll(modifiableParameters);
        }

        return Collections.unmodifiableMap(allParameters);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(getParameterMap().keySet());
    }

    @Override
    public String[] getParameterValues(final String name) {
        return getParameterMap().get(name);
    }

    private Map<String, String[]> dealUnderLineParam(Map<String, String[]> parameterMap){
        Map<String, String[]> result = new HashMap<>();
        if(CollectionUtils.isEmpty(parameterMap)){
            return result;
        }
        parameterMap.forEach((k,v)->result.put(ParameterUtils.underLine2HumpForParameter(k), v));
        return result;
    }
}
