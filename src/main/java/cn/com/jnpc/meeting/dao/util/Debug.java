/*
 * Debug.java
 *
 * Created on 2006年1月11日, 上午10:04
 */

package cn.com.jnpc.meeting.dao.util;

/**
 *
 * @author  houyy
 */
public class Debug {
    
    /** Creates a new instance of Debug */
   private Debug() {}

   private static String SysName = "会议通知管理系统:";
   public static void print_log(Exception e,String log) {
     System.out.println(SysName+log);
     e.printStackTrace();
   }
  public static void print_log(Exception e) {
     System.out.println(SysName);
     e.printStackTrace();
   }
  public static void print_log(String log) {
     System.out.println(SysName+log);
   }
 }

