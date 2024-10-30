package com.example.ToYokoNa.filter;

import com.example.ToYokoNa.controller.form.UserForm;
import com.example.ToYokoNa.repository.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ManageAccountFilter implements Filter {
    @Autowired
    private HttpSession httpSession;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain ) throws ServletException, IOException {
        // 型変換
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // セッション情報を利用できるようにするためにgetSessionして取得。
        httpSession = httpRequest.getSession();
        UserForm user = (UserForm)httpSession.getAttribute("loginUser");
        if (user.getDepartmentId() == 1 && user.getBranchId() == 1) {
                chain.doFilter(httpRequest, httpResponse); //処理を続ける(フィルターを抜ける)
        } else {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("無効なアクセスです");
            httpSession.setAttribute("errorMessages", errorMessages);
            httpResponse.sendRedirect("/ToYokoNa");
        }
    }

    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void destroy() {
    }
}
