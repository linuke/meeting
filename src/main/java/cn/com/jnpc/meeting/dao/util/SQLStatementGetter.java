package cn.com.jnpc.meeting.dao.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.jnpc.meeting.bean.Meeting;
import cn.com.jnpc.utils.DateUtil;

public class SQLStatementGetter
{
    
    /**
     * 获取插入语句.
     * 
     * @param t 要插入的javabean对象
     * @param tblName javabean对应表名
     * @return
     * @throws SQLException
     */
    public static <T> String getInsertStatement(T t, String tblName)
    {
        
        Field[] fields = t.getClass().getDeclaredFields();
        List<String> names = new ArrayList<String>();
        List<String> colunmValues = new ArrayList<String>();
        Field.setAccessible(fields, true);
        SetMethodReflect<T> smr = new SetMethodReflect<T>(t);
        StringBuilder sb1 = new StringBuilder();
        sb1.append("insert into " + tblName + " (");
        StringBuilder sb2 = new StringBuilder();
        sb2.append(") values (");
        for (int i = 0, len = fields.length; i < len; i++)
        {
            Modifier.toString(fields[i].getModifiers());
            if (fields[i].getModifiers() != (Modifier.PRIVATE + Modifier.FINAL + Modifier.STATIC))
            {// 过滤掉 private static final 属性
                String colnumName = fields[i].getName();
                Class<?> clazz = fields[i].getType();
                Object colunmValue = smr.getMethodValue(colnumName);
                if (colunmValue != null && !"".equals(colunmValue + ""))
                {
                    if (clazz == Date.class)
                    {
                        colunmValues.add("to_date('" + DateUtil.dateToString((Date)colunmValue, "yyyy-MM-dd HH:mm")
                            + "','yyyy-mm-dd hh24:mi')");
                    }
                    else if (clazz == Integer.class || clazz == Long.class || clazz == Double.class
                        || clazz == Float.class)
                    {
                        colunmValues.add(colunmValue + "");
                    }
                    else if (clazz == String.class)
                    {
                        if ("id".equals(colnumName))
                        {
                            colunmValues.add(colunmValue + "");
                        }
                        else
                        {
                            colunmValues.add("'" + colunmValue + "'");
                        }
                    }
                    names.add(colnumName);
                }
            }
        }
        
        for (int i = 0, len = names.size(); i < len; i++)
        {
            String str = names.get(i);
            if (i != len - 1)
            {
                sb1.append(str + ",");
                sb2.append(colunmValues.get(i) + ",");
            }
            else
            {
                sb1.append(str);
                sb2.append(colunmValues.get(i));
            }
        }
        String sql = sb1.toString() + sb2.toString() + ")";
        return sql;
    }
    
    /**
     * 获取更新语句.
     * 
     * @param t 要更新的javabean对象
     * @param tblName javabean对应表名
     * @param pk 主键
     * @return
     * @throws SQLException
     */
    public static <T> String getUpdateStatement(T t, String tblName, String pk)
    {
        Field[] fields = t.getClass().getDeclaredFields();// 取得所有的属性
        List<String> names = new ArrayList<String>();
        List<String> colunmValues = new ArrayList<String>();
        Field.setAccessible(fields, true);
        SetMethodReflect<T> smr = new SetMethodReflect<T>(t);
        StringBuilder sb = new StringBuilder();
        sb.append("update " + tblName + " set ");
        for (int i = 0, len = fields.length; i < len; i++)
        {
            Modifier.toString(fields[i].getModifiers());
            if (fields[i].getModifiers() != (Modifier.PRIVATE + Modifier.FINAL + Modifier.STATIC))
            {// 过滤掉 private static final 属性
                String colnumName = fields[i].getName();
                if (!pk.equals(colnumName))
                {
                    Class<?> clazz = fields[i].getType();
                    Object colunmValue = smr.getMethodValue(colnumName);
                    if (colunmValue != null)
                    {
                        if (clazz == Date.class)
                        {
                            colunmValues.add("to_date('" + DateUtil.dateToString((Date)colunmValue, "yyyy-MM-dd HH:mm")
                                + "','yyyy-mm-dd hh24:mi')");
                        }
                        else if (clazz == Integer.class || clazz == Long.class || clazz == Double.class
                            || clazz == Float.class)
                        {
                            colunmValues.add(colunmValue + "");
                        }
                        else if (clazz == String.class)
                        {
                            if (!pk.equals(colnumName))
                            {
                                colunmValues.add("'" + colunmValue + "'");
                            }
                        }
                        names.add(colnumName);
                    }
                }
            }
        }
        for (int i = 0, len = names.size(); i < len; i++)
        {
            String str = names.get(i);
            if (i == len - 1)
            {
                sb.append(str + "=" + colunmValues.get(i) + " where " + pk + "='" + smr.getMethodValue(pk) + "'");
            }
            else
            {
                sb.append(str + "=" + colunmValues.get(i) + ",");
            }
            
        }
        String sql = sb.toString();
        return sql;
    }
    
    /**
     * 获取分页查询语句.
     *@Title:  getPageQueryStatement
     *@Author: linuke
     *@Since: 2013-4-23下午1:54:11
     *@param sql 表字段和条件,查询语句去掉select之后的语句
     *@param min 起始页
     *@param max 结束页
     *@param isDesc 排序 dsec 还是 asc
     *@param orderby 排序字段
     *@return
     */
    public static String getPageQueryStatement(String sql,int min,int max){
        StringBuilder sb = new StringBuilder();
        sb.append(" select * from (select view1.*,rownum rn from ("+sql+") view1 where rownum<="+max+") view2 where rn>="+min);
        return sb.toString();
    }
    
    public static void main(String[] args)
    {
        Meeting m = new Meeting();
        m.setId("123");
        m.setAddress("adfasf");
        m.setStarttime(new Date());
        System.out.println(getInsertStatement(m, "meeting"));
        System.out.println(getUpdateStatement(m, "meeting", "id"));
        
    }
    
}
