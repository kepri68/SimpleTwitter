package controllers.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import model.User;
import org.w3c.dom.UserDataHandler;

@WebServlet(name = "test", value = "/test")
public class TestServlet extends HttpServlet {
    UserDAO userDAO= new UserDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User.UserBuilder().buildPassword("lala").buildEmail("w@w.pl").buildLastName("kowalik").buildName("Gabi")
                .buildLogin("Gabs").buildUser();
        List<User> allUsers = userDAO.getAllUsers();
        req.setAttribute("users", allUsers);
        req.setAttribute("user", user);
        req.setAttribute("me", "Vampi");
        req.getSession().setAttribute("me", "VampiSession");
        req.setAttribute("now", new Date());
        req.getRequestDispatcher("test.jsp").forward(req, resp);
    }
}