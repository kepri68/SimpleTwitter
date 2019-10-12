package controllers.servlets;

import dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "loginServlet", urlPatterns = {"", "/login"})
public class LoginServlet extends HttpServlet {
    private final String PASSWORD = "password";
    private final String LOGIN = "login";
    private final String REMEMBER = "remember";
    private final String LOGIN_COOKIE = "twitter_login";
    private final String PASSWORD_COOKIE = "twitter_password";
    private final String CHECKBOX_SELECTED = "on";
    private final int SECONDS_IN_DAY = 60 * 60 * 24;

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        super.init();
    }

    /**
     * if user send request :
     * Cookies will be checked if any of Logging depended are stored in user broswer,
     * if those cookies exist, their max age wil be averrided. Login and password would be attached to request as a attribute.
     * and doPost method will be called.
     * otherwise login.jsp will be displayed.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = null;
        String password = null;

        if (!(null == req.getCookies())) {
            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals(LOGIN_COOKIE)) {
                    login = cookie.getValue();
                    cookie.setMaxAge(SECONDS_IN_DAY);
                    resp.addCookie(cookie);
                } else if (cookie.getName().equals(PASSWORD_COOKIE)) {
                    password = cookie.getValue();
                    cookie.setMaxAge(SECONDS_IN_DAY);
                    resp.addCookie(cookie);
                }
            }
        }
        if (null != login && null != password) {
            req.setAttribute(LOGIN, login);
            req.setAttribute(PASSWORD, password);
            doPost(req, resp);
        } else {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    /**
     * Firtly method try to get Login, password from paramerers (parameters are send by fro)
     * if parameters are null it means that doPost method was called by doGet() because doGet method
     * is putting attributes instead of parameters,
     * In next step attributes are checked and Login and password are set to attributes values.
     * Login and password are verified by dao isUserValid method. If it is valid is saved in session attribute.
     * Next if remember checkbox is set to born, cookies for login and password are created and attached to response
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        String remember = req.getParameter(REMEMBER);

        if (null == login || null == password) {
            login = (String) req.getAttribute(LOGIN);
            password = (String) req.getAttribute(PASSWORD);
        }
        if (userDAO.isUserValid(login, password)) {
            req.getSession().setAttribute(LOGIN, login);
            if (null != remember && remember.equals(CHECKBOX_SELECTED)) {
                Cookie loginCookie = new Cookie(LOGIN_COOKIE, login);
                Cookie passwordCookie = new Cookie(PASSWORD_COOKIE, password);
                loginCookie.setMaxAge(SECONDS_IN_DAY);
                passwordCookie.setMaxAge(SECONDS_IN_DAY);
                resp.addCookie(loginCookie);
                resp.addCookie(passwordCookie);
            }
            req.getRequestDispatcher("users").forward(req, resp);
        } else {
            req.setAttribute("hasError", "true");
            req.setAttribute("error", "Login or password incorrect!");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
