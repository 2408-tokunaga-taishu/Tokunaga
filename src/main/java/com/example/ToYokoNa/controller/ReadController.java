package com.example.ToYokoNa.controller;

import com.example.ToYokoNa.controller.form.UserForm;
import com.example.ToYokoNa.repository.ReadRepository;
import com.example.ToYokoNa.repository.entity.Read;
import com.example.ToYokoNa.service.ReadService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ReadController {
    @Autowired
    ReadService readService;
    @Autowired
    private HttpSession session;

    /*
     * 既読・未読処理
     */
    @PostMapping("/read/{id}")
    public ModelAndView ReadMessage(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView();
        // ログインしているユーザの情報を取得
        UserForm loginUser = (UserForm)session.getAttribute("loginUser");
        if (readService.existsByUserIdAndPostId(loginUser.getId(), id)) {
            // true(既読状態)だったらの処理
            readService.deleteByUserIdAndPostId(loginUser.getId(), id);
        } else {
            // false(未読状態)だったらの処理
            readService.save(loginUser.getId(), id);
        }
        mav.setViewName("redirect:/");
        return mav;
    }
}
