package com.example.ToYokoNa.controller;

import com.example.ToYokoNa.controller.form.CommentForm;
import com.example.ToYokoNa.controller.form.UserForm;
import com.example.ToYokoNa.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private HttpSession session;

    @Autowired
    CommentService commentService;
    /*
    コメントの追加機能
     */
    @PostMapping("/commentAdd/{messageId}")
    public ModelAndView commentAdd(@PathVariable int messageId, @ModelAttribute("commentForm") @Validated CommentForm commentForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        UserForm loginUser = (UserForm) session.getAttribute("loginUser");
        List<String> errorMessages = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
        }
        if (errorMessages.size() > 0){
            redirectAttributes.addFlashAttribute("commentErrorMessages", errorMessages);
            redirectAttributes.addFlashAttribute("messageId", messageId);
            mav.setViewName("redirect:/");
        } else {
            commentService.saveComment(loginUser, commentForm);
            mav.setViewName("redirect:/");
        }
        return mav;
    }

    /*
    コメントの削除機能
     */
    @DeleteMapping("/deleteComment/{id}")
    public ModelAndView deleteComment (@PathVariable int id,
                                       @RequestParam("commentUserId") int commentUserId) {
        ModelAndView mav = new ModelAndView();
        commentService.deleteComment(id, commentUserId);
        mav.setViewName("redirect:/");
        return mav;
    }
}
