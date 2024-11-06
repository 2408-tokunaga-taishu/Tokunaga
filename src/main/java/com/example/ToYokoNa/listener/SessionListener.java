package com.example.ToYokoNa.listener;

import com.example.ToYokoNa.controller.form.UserForm;
import com.example.ToYokoNa.repository.entity.loginUsers;
import com.example.ToYokoNa.repository.loginUsersRepository;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class SessionListener implements HttpSessionAttributeListener {
    @Autowired
    HttpSession session;
    @Autowired
    loginUsersRepository loginUsersRepository;
    // セッション追加時に呼び出されるメソッド
    @EventListener
    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        if (Objects.equals(se.getName(), "loginUser")) {
            UserForm user = (UserForm) se.getValue();
            loginUsers loginUser = new loginUsers();
            loginUser.setAccount(user.getAccount());
            loginUser.setUserId(user.getId());
            loginUsersRepository.save(loginUser);
        }
    }

    // セッション削除時に呼び出されるメソッド
    @EventListener
    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
        if (Objects.equals(se.getName(), "loginUser")) {
            UserForm user = (UserForm) se.getValue();
            loginUsersRepository.deleteByAccount(user.getAccount());
        }
    }
}
