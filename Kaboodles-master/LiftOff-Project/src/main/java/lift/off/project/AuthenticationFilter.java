package lift.off.project;

import lift.off.project.controllers.UserController;
import lift.off.project.models.User;
import lift.off.project.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter extends HandlerInterceptorAdapter {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserController userController;

//    home page that does not require being logged in to view, Whitelist

    private static final List<String> whitelist = Arrays.asList("/index","/login", "/register", "/logout","/css","/images");

//    check whether  a given request is whitelisted or not.

    private static boolean isWhitelisted(String path) {
        for (String pathRoot : whitelist) {
            if (path.startsWith(pathRoot)) {
                return true;
            }
        }
        return false;
    }

//    check all requests against the whitelist within preHandle

@Override
public boolean preHandle(HttpServletRequest request,
                         HttpServletResponse response,
                         Object handler) throws IOException {

    // Don't require sign-in for whitelisted pages
    if (isWhitelisted(request.getRequestURI())) {
        // returning true indicates that the request may proceed
        return true;
    }

    HttpSession session = request.getSession();
    User user = userController.getUserFromSession(session);

    // The user is logged in
    if (user != null) {
        return true;
    }

    // The user is NOT logged in
    response.sendRedirect("/index");
    return false;
}

}
