package controllers;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Message;
import utils.DBUtil;

@WebServlet("/edit")
public class EditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public EditServlet() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        var em = DBUtil.createEntityManager();
        // 該当のIDのメッセージ1件のみをデータベースから取得
        var m = em.find(Message.class, Integer.parseInt(request.getParameter("id")));
        em.close();
        // メッセージ情報をセッションIDをリクエストスコープに登録
        request.setAttribute("message", m);
        request.setAttribute("_token", request.getSession().getId());
        // メッセージのIDをセッションスコープに登録
        if (m != null) {
            request.getSession().setAttribute("message_id", m.getId());
        }
        // JSPにフォワード
        var rd = request.getRequestDispatcher("/WEB-INF/views/messages/edit.jsp");
        rd.forward(request, response);
    }
} // ← これでクラスを閉じる