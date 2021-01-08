package pers.under2hump.springboot.configure.core.convert;

import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * @description 简述类的功能
 * @author jiao.zhaojun
 * @date 2020/12/25
 */
@Component
public class ArrayOfSimpleValueConvert extends ArgMatchConvert<Object[]> {

    @Override
    public Object[] doHandleConvert(String source) throws Exception {
        if(!StringUtils.hasText(source)){
            return new Object[0];
        }

        source = source.replaceAll("\\[","").replaceAll("]", "");
        if(!StringUtils.hasText(source)){
            return new Object[0];
        }
        if(!source.contains(",")){
            return new Object[]{source};
        }

        return source.split(",");
    }

    @Override
    public boolean match(Class<?> target) {
        return target.isArray() && (ClassUtils.isPrimitiveOrWrapper(target.getComponentType()) || CharSequence.class.isAssignableFrom(target.getComponentType()));
    }
}
