package cn.com.jnpc.meeting.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseServlet extends HttpServlet {

    private static final long     serialVersionUID = 1L;
    protected static final String BASE_JSP         = "/WEB-INF/views/";
    protected HttpSession         session;
    protected HttpServletRequest  request;
    protected HttpServletResponse response;
    protected String              error            = "";
    protected String              userid           = "";               // 用户id
    protected String              u_org            = "";               // 用户部门
    protected Vector<String>      vec              = null;             // 用户权限

    @SuppressWarnings("unchecked")
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        this.request = request;
        this.response = response;
        session = request.getSession();
        userid = (String) session.getAttribute("userid");
        u_org = (String) session.getAttribute("u_org");
        vec = (Vector<String>) session.getAttribute("vec");
        if (vec == null) {
            error = "系统session超时，请重新登录！";
            toErrorPage(error);
        } else {
            try {
                String ctrl = request.getParameter("ctrl");
                Method method = this.getClass().getMethod(ctrl);
                String forward = (String) method.invoke(this);
                if (forward != null && !"".equals(forward)) {
                    String r = "redirect:";
                    if (forward.startsWith(r)) {
                        response.sendRedirect(forward.substring(r.length()));
                    } else {
                        request.getRequestDispatcher(forward).forward(request, response);
                    }
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw new RuntimeException("An illegal or incorrect parameter：" + e.getMessage());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                throw new RuntimeException("No such method found：" + e.getMessage());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("The method is private,but is request a public method：" + e.getMessage());
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException("InvocationTargetException：" + e.getMessage());
            }
        }
    }

    protected String toErrorPage(String error) {
        request.setAttribute("error", error);
        try {
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    protected String getParameter(String param) {
        String value = request.getParameter(param);
        request.setAttribute(param, value);
        return value;
    }

    protected <T> void writeListToPage(List<T> list) {
        PrintWriter out = null;
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            String json = gson.toJson(list);
            out = response.getWriter();
            out.print(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    protected <T> void writeObjToPage(Object obj) {
        PrintWriter out = null;
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            String json = gson.toJson(obj);
            out = response.getWriter();
            out.print(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    protected boolean isEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }
}