package cn.com.jnpc.meeting.servlet;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.com.jnpc.meeting.dao.JNPC;
import cn.com.jnpc.meeting.dao.util.Right;

public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private JNPC              jnpc             = new JNPC();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        login(request, response);
        // test(request, response);
    }

    private void test(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userid = "20021273";
        String passwd = "8888";
        HttpSession session = request.getSession();
        Vector<String> vec = new Vector<String>();
        vec.addElement("3801");
        vec.addElement("380101");
        vec.addElement("3802");
        vec.addElement("380201");
        vec.addElement("3803");
        vec.addElement("380301");
        vec.addElement("3804");
        vec.addElement("380401");
        vec.addElement("3805");
        vec.addElement("380501");// 会议室添加/修改
        vec.addElement("380502");// 培训教室添加/修改
        vec.addElement("3806");
        vec.addElement("380601");// 培训通知添加/修改
        vec.addElement("3807");
        vec.addElement("380701");// 培训通知审批
        vec.addElement("3808");
        vec.addElement("380801");// 培训通知重新分配教室
        session.setAttribute("userid", userid);
        session.setAttribute("passwd", passwd);
        session.setAttribute("vec", vec);
        session.setAttribute("u_org", jnpc.getOrg(userid));
        request.getRequestDispatcher("/index/index.jsp").forward(request, response);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Right right = new Right();
        String userid = request.getParameter("userid");
        String passwd = request.getParameter("passwd");
        HttpSession session = request.getSession();
        String error = "";
        String fromWhere = request.getParameter("return");
        if (fromWhere != null && fromWhere.equals("1")) {
            // 密码加密
            passwd = right.computeDigest(passwd);
        }
        String flagEncrypt = "";
        if (userid == null || passwd == null || userid.equals("") || passwd.equals("")) {
            // response.sendRedirect
            // ("http://www0.jnpc.com.cn/login/login.jsp");
            response.sendRedirect("http://test01.jnpc.com.cn:8080/login/login.jsp");
        } else {
            // 验证用户
            flagEncrypt = right.VerifyUser(userid, passwd);
            if (!flagEncrypt.equals("1")) {
                error = "用户名或密码不正确，请重新输入！";
                request.setAttribute("error", error);
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            } else {
                // 设置用户所有权限（排除相同的权限）
                Vector<String> vec = right.getRights(userid, "38");
                if (vec == null) {
                    error = "数据库问题，请稍后再试！";
                    request.setAttribute("error", error);
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }
                // 存储用户信息和用户权限
                session.setAttribute("userid", userid);
                session.setAttribute("passwd", passwd);
                session.setAttribute("u_org", jnpc.getOrg(userid));
                session.setAttribute("vec", vec);
                request.getRequestDispatcher("/index/index.jsp").forward(request, response);
            }
        }
    }
}
