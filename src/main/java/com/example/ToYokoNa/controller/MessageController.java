package com.example.ToYokoNa.controller;

import com.example.ToYokoNa.controller.form.*;
import com.example.ToYokoNa.service.CommentService;
import com.example.ToYokoNa.service.MessageService;
import com.example.ToYokoNa.service.ReadService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    private HttpSession session;

    @Autowired
    CommentService commentService;

    @Autowired
    ReadService readService;
    /*
    Top画面表示処理
     */
    @GetMapping
    public ModelAndView top(@RequestParam(name = "startDate", required = false) String startDate,
                            @RequestParam(name = "endDate", required = false) String endDate,
                            @RequestParam(name = "category", required = false) String category,
                            Model model)
                            throws ParseException {
        ModelAndView mav = new ModelAndView();
        UserForm loginUser = (UserForm)session.getAttribute("loginUser");
        List<UserMessageForm> messages = messageService.findALLMessages(startDate, endDate, category);
        List<UserCommentForm> comments = commentService.findAllComments();
        CommentForm commentForm = new CommentForm();
        List<Integer> readMessages = readService.findReadMessages(loginUser.getId());
//　　　ユーザー管理画面表示フラグ
        boolean isShowUserManage = false;
        if (loginUser.getDepartmentId() == 1) {
            isShowUserManage = true;
        }
        mav.addObject("commentForm", commentForm);
        mav.addObject("comments", comments);
        mav.addObject("messages", messages);
        mav.addObject("errorMessages", session.getAttribute("errorMessages"));
        mav.addObject("loginUser", loginUser);
        mav.addObject("isShowUserManage", isShowUserManage);
        mav.addObject("startDate", startDate);
        mav.addObject("endDate", endDate);
        mav.addObject("category", category);
        mav.addObject("messageId", model.getAttribute("messageId"));
        mav.addObject("commentErrorMessages", model.getAttribute("commentErrorMessages"));
        mav.addObject("read", readMessages);
        mav.addObject("deleteErrorMessage", session.getAttribute("deleteErrorMessage"));
        mav.addObject("deleteErrorMessageId", session.getAttribute("messageId"));
        mav.setViewName("/top");
        // 管理者フィルターのエラーメッセージをsessionで渡しているので最後に削除してtopページ表示
        session.removeAttribute("errorMessages");
        return mav;
    }
    /*
    新規投稿画面遷移
     */
    @GetMapping("/newMessage")
    public ModelAndView newMessage(@ModelAttribute("messageForm") MessageForm messageForm) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("messageForm", messageForm);
        mav.setViewName("/newMessage");
        return mav;
    }
    /*
    投稿追加処理
     */
    @PostMapping("/addMessage")
    public ModelAndView addMessage(@ModelAttribute("messageForm")
                                       @Validated MessageForm messageForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        UserForm loginUser = (UserForm) session.getAttribute("loginUser");
        List<String> errorMessages = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
        }
        if (errorMessages.size() > 0 ) {
            redirectAttributes.addFlashAttribute("errorMessages", errorMessages);
            redirectAttributes.addFlashAttribute("messageForm", messageForm);
            mav.setViewName("redirect:/newMessage");
        } else {
            messageService.save(messageForm, loginUser);
            mav.setViewName("redirect:/");
        }
        return mav;
    }

    /*
    投稿削除処理
     */
    @DeleteMapping("/deleteMessage/{id}")
    public ModelAndView deleteMessage(@PathVariable int id,
                                      @RequestParam("messageUserId") int messageUserId,
                                      @RequestParam("messageBranch") int messageBranch,
                                      @RequestParam("messageDepartment") int messageDepartment) {
        ModelAndView mav = new ModelAndView();
        UserForm loginUser = (UserForm) session.getAttribute("loginUser");
        if (loginUser.getId() == messageUserId) {
            messageService.deleteMessage(id,messageUserId,messageBranch);
            mav.setViewName("redirect:/");
            return mav;
        } else if (loginUser.getDepartmentId() == 2) {
                messageService.deleteMessage(id, messageUserId, messageBranch);
                mav.setViewName("redirect:/");
                return mav;
        } else if (loginUser.getBranchId() == messageBranch && loginUser.getDepartmentId() == 3 && messageDepartment == 4) {
            messageService.deleteMessage(id, messageUserId,messageBranch);
            mav.setViewName("redirect:/");
            return mav;
        } else {
            String errorMessage ="この投稿は削除できません";
            session.setAttribute("deleteErrorMessage", errorMessage);
            session.setAttribute("messageId", id);
            mav.setViewName("redirect:/");
            return mav;
        }
    }
}
