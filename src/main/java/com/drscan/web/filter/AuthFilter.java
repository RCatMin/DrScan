package com.drscan.web.filter;

import com.drscan.web.primary.permission.service.PermissionService;
import com.drscan.web.primary.users.domain.AuthUser;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;

@Configuration
@WebFilter("/*")
public class AuthFilter implements Filter {

    private final List resources = List.of(
            "/script",
            "/style",
            "/users/valid",
            "/img"
    );

    private final List urls = List.of(
            "/",
            "/index",
            "/header",
            "/footer",
            "/users/action/signout"
    );

    private final List users = List.of(
            "/users/action",
            "/users/signin",
            "/users/signup",
            "/users/action/signin",
            "/users/action/signup",
            "/send-verification",
            "/verify",
            "/users/action/checkDuplication-phone"
    );

    @Autowired
    private final PermissionService permissionService;

    public AuthFilter(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();

        HttpSession session = request.getSession();
        AuthUser authUser = (AuthUser) session.getAttribute("authUser");

        boolean isAdmin = (authUser != null) && permissionService.checkAdmin(authUser.getUserCode());

        if (urls.contains(uri) || resources.stream().anyMatch(o -> uri.startsWith((String) o))) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (users.stream().anyMatch(o -> uri.startsWith((String) o)) && !isAdmin){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (authUser == null) {
            response.sendRedirect("/users/signin");
            return;
        }

        if (isAdmin) {
            if (!uri.startsWith("/admin")) {
                response.sendRedirect("/admin");
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}