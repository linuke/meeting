package cn.com.jnpc.meeting.dao.util;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.regex.Pattern;

import cn.com.jnpc.meeting.bean.Meeting;

/**
 * 通过反射来动态调用 JavaBean 的 get 和 set 方法 .
 * 
 * @ClassName: SetMethodReflect
 * @Title:
 * @Author:linuke
 * @Since:2013-4-19下午2:26:04
 * @Version:1.0
 * @param <T>
 */
public class SetMethodReflect<T>
{
    
    /**
     * 传过来的对象
     */
    private T t;
    
    /**
     * 存放get方法
     */
    private Hashtable<String, Method> getMethods = null;
    
    /**
     * 存放set方法
     */
    private Hashtable<String, Method> setMethods = null;
    
    /**
     * 定义构造方法 -- 一般来说是个pojo
     * 
     * @param o 目标对象
     */
    public SetMethodReflect(T t)
    {
        this.t = t;
        initMethods();
    }
    
    /**
     * 
     * @desc 初始化
     */
    public void initMethods()
    {
        getMethods = new Hashtable<String, Method>();
        setMethods = new Hashtable<String, Method>();
        Class<?> cls = t.getClass();
        Method[] methods = cls.getMethods();
        // 定义正则表达式，从方法中过滤出getter / setter 函数.
        String gs = "get(\\w+)";
        Pattern getM = Pattern.compile(gs);
        String ss = "set(\\w+)";
        Pattern setM = Pattern.compile(ss);
        // 把方法中的"set" 或者 "get" 去掉
        String rapl = "$1";
        String param;
        for (int i = 0; i < methods.length; ++i)
        {
            Method m = methods[i];
            String methodName = m.getName();
            if (Pattern.matches(gs, methodName))
            {
                param = getM.matcher(methodName).replaceAll(rapl).toLowerCase();
                getMethods.put(param, m);
            }
            else if (Pattern.matches(ss, methodName))
            {
                param = setM.matcher(methodName).replaceAll(rapl).toLowerCase();
                setMethods.put(param, m);
            }
            else
            {
                // System.out.println(methodName + " 不是getter,setter方法！");
            }
        }
    }
    
    /**
     * 根据属性名调用set方法.
     * 
     * @Title: setMethodValue
     * @Author: linuke
     * @Since: 2013-4-19下午2:28:12
     * @param property JavaBean属性
     * @param value 需要set的值
     * @return 调用是否成功
     */
    public boolean setMethodValue(String property, Object value)
    {
        Method m = setMethods.get(property.toLowerCase());
        if (m != null)
        {
            try
            {
                m.invoke(t, value);// 调用t类的setter函数
                return true;
            }
            catch (Exception ex)
            {
                System.out.println("invoke getter on " + property + " error: " + ex.toString());
                return false;
            }
        }
        return false;
    }
    
    /**
     * 根据属性名调用get方法.
     * 
     * @Title: getMethodValue
     * @Author: linuke
     * @Since: 2013-4-19下午2:27:25
     * @param property
     * @return
     */
    public Object getMethodValue(String property)
    {
        Method m = getMethods.get(property.toLowerCase());
        if (m != null)
        {
            try
            {
                return m.invoke(t);// 调用t类的setter函数
            }
            catch (Exception ex)
            {
                System.out.println("invoke getter on " + property + " error: " + ex.toString());
                return null;
            }
        }
        return null;
    }
    
    // 测试方法
    public static void main(String args[])
    {
        Meeting m = new Meeting();
        m.setDepart("as");
        SetMethodReflect<Meeting> smr = new SetMethodReflect<Meeting>(m);
        System.out.println(smr.getMethodValue("depart"));
        
    }
}