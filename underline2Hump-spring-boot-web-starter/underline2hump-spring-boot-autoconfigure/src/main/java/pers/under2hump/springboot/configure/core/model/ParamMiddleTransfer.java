package pers.under2hump.springboot.configure.core.model;

/**
 * @description 处理body参数的过程中，用户处理数据的中间对象
 * @author jiao.zhaojun
 * @date 2020/12/25
 */
public class ParamMiddleTransfer {

    private String paramName;

    private Object value;

    private String parent;

    public ParamMiddleTransfer(String paramName, Object value, String parent) {
        this.paramName = paramName;
        this.value = value;
        this.parent = parent;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "ParamSoftDo{" +
                "paramName='" + paramName + '\'' +
                ", value=" + value +
                ", parent='" + parent + '\'' +
                '}';
    }
}
