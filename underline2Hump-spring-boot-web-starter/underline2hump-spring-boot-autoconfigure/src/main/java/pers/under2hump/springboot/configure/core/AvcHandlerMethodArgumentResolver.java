package pers.under2hump.springboot.configure.core;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import pers.under2hump.springboot.configure.annotation.IgnoreToHump;
import pers.under2hump.springboot.configure.annotation.SubAttribute;
import pers.under2hump.springboot.configure.core.convert.ArgMatchConvert;
import pers.under2hump.springboot.configure.core.convert.ArgMatchConvertHolder;
import pers.under2hump.springboot.configure.utils.ParameterUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @description 将请求参数的下划线格式转为驼峰格式，目标方法参数必须是 【非基本类型及其包装类型】或【String】
 * @see AvcHttpServletRequestWrapper#getParameterMap() 处理 String 或 基本类型及其包装类型的参数
 * @author  AmVilCresx
 */
public class AvcHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> paramClassType = methodParameter.getParameterType();
        Method method = methodParameter.getMethod();
        if (Objects.isNull(method) || method.isAnnotationPresent(IgnoreToHump.class)){
            return false;
        }
        return !methodParameter.hasParameterAnnotation(RequestParam.class)
                && !(ClassUtils.isPrimitiveOrWrapper(paramClassType) || CharSequence.class.isAssignableFrom(paramClassType));
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Iterator<String> iterator = nativeWebRequest.getParameterNames();
        // Map<对象的属性名称, 值>
        Map<String, String> paramValues = new HashMap<>();
        while (iterator.hasNext()) {
            String param = iterator.next();
            String value = nativeWebRequest.getParameter(param);
            paramValues.put(ParameterUtils.underLine2HumpForParameter(param), value);
            // 兼容
            paramValues.put(param, value);
        }

        Class<?> paramClassType = methodParameter.getParameterType();
        Object obj = BeanUtils.instantiateClass(paramClassType);
        // 递归解析对象属性并赋值
        parseAttributes(obj, paramValues);
        return obj;
    }

    /**
     * 解析对象属性并赋值
     *
     * @param obj 顶层对象
     * @param paramValues <code>Map<属性名, 值></code>
     */
    private void parseAttributes(Object obj, Map<String, String> paramValues) {
        Field[] fields = obj.getClass().getDeclaredFields();
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        Stream.of(fields).forEach(field -> {
            String fName = field.getName();
            try {
                // 如果存在字段存在该注解，则说明需要对该属性就行内部解析
                SubAttribute subModel = field.getAnnotation(SubAttribute.class);
                if (Objects.nonNull(subModel)) {
                    Object tmp = BeanUtils.instantiateClass(field.getType());
                    parseAttributes(tmp, paramValues);
                    wrapper.setPropertyValue(fName, tmp);
                    return;
                }

                if (!CollectionUtils.isEmpty(paramValues) && paramValues.containsKey(fName)) {
                    String strValue = paramValues.get(fName);
                    List<ArgMatchConvert<?>> converts = ArgMatchConvertHolder.getConverts();
                    if(!CollectionUtils.isEmpty(converts)){
                        Optional<ArgMatchConvert<?>> optional = converts.stream().filter(convert -> convert.match(wrapper.getPropertyType(fName))).findFirst();
                        if (optional.isPresent()) {
                            wrapper.setPropertyValue(fName, optional.get().doHandleConvert(strValue));
                            return;
                        }
                    }
                    wrapper.setPropertyValue(fName, strValue);
                }
            } catch (Exception e) {
                // 记录异常日志
            }
        });
    }
}
