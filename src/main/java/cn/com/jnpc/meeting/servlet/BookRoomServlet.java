package cn.com.jnpc.meeting.servlet;

/**
 * Servlet implementation class BookRoomServlet
 */
public class BookRoomServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public BookRoomServlet() {
        super();
    }

    public String toBook(){
        return BASE_JSP + "bookRoom/bookRoom.jsp";
    }

}
