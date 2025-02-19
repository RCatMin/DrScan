package com.drscan.web.filter;

import com.drscan.web.permission.service.PermissionService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;

@Configuration
@WebFilter("/*")
public class AuthFilter implements Filter {

    private final List resources = List.of(
            "/script",
            "/style"
    );

    private final List urls = List.of(
            "/",
            "/index",
            "/header",
            "/footer"
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();

        if(urls.contains(uri) || resources.stream().anyMatch(o -> uri.startsWith((String) o))) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

//        HttpSession session = request.getSession();
//        AuthUser authUser = (AuthUser) session.getAttribute("authUser");
//
//        if(authUser == null) {
//            response.sendRedirect("/users/signin");
//            return;
//        }
//
//        PermissionService permissionService = new PermissionService;
//        if(permissionService.checkAdmin(authUser.getUsercode())){
//            response.sendRedirect("/admin");
//            return;
//        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}