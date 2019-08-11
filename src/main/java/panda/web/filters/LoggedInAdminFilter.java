package panda.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({ "/",
        "/faces/view/index.xhtml",
        "/faces/view/login.xhtml",
        "/faces/view/register.xhtml"
})
public class LoggedInAdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (req.getSession().getAttribute("username") != null &&
                req.getSession().getAttribute("role").equals("Admin")) {
            resp.sendRedirect("/faces/view/home.xhtml");
        } else {
            chain.doFilter(req, resp);
        }
    }
}
