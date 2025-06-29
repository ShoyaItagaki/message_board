 package controllers;
 import java.io.IOException;
 import javax.servlet.ServletException;
 import javax.servlet.annotation.WebServlet;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import models.Message;
 import utils.DBUtil;
 @WebServlet("/destroy")
 public class DestroyServlet extends HttpServlet {
 private static final long serialVersionUID = 1L;
 public DestroyServlet() {
 super();
 }
 protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
     String _token = request.getParameter("_token");
     if (_token != null && _token.equals(request.getSession().getId())) {
         var em = DBUtil.createEntityManager();
         // セッションスコープからメッセージのIDを取得して
//該当のIDのメッセージ1件のみをデータベースから取得
var m = em.find(Message.class, (Integer) (request.getSession().getAttribute("message_id")));
         em.getTransaction().begin();
         em.remove(m); // データ削除
em.getTransaction().commit();
request.getSession().setAttribute("flush", "削除が完了しました。");   
         em.close();
         // セッションスコープ上の不要になったデータを削除
request.getSession().removeAttribute("message_id");
         // indexページへリダイレクト
response.sendRedirect(request.getContextPath() + "/index");
     }
 }
}
