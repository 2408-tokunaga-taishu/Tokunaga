package com.example.ToYokoNa.service;

import com.example.ToYokoNa.controller.form.MessageForm;
import com.example.ToYokoNa.controller.form.UserForm;
import com.example.ToYokoNa.controller.form.UserMessageForm;
import com.example.ToYokoNa.repository.BranchRepository;
import com.example.ToYokoNa.repository.CommentRepository;
import com.example.ToYokoNa.repository.MessageRepository;
import com.example.ToYokoNa.repository.UserRepository;
import com.example.ToYokoNa.repository.entity.Comment;
import com.example.ToYokoNa.repository.entity.Message;
import com.example.ToYokoNa.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentService commentService;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    UserRepository userRepository;

    /*
    全投稿取得処理
     */
    public List<UserMessageForm> findALLMessages(String startDate, String endDate, String category) throws ParseException {
//       取得件数定数
        int limit = 1000;
//        絞込日付作成処理
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Message> results = new ArrayList<>();
        if (isBlank(startDate)) {
            startDate = "2022-01-01 00:00:00";
        } else {
            startDate = startDate + " 00:00:00";
        }
        if (isBlank(endDate)) {
            endDate = sdf.format(new Date());
        } else {
            endDate = endDate + " 23:59:59";
        }
        Date start = sdf.parse(startDate);
        Date end = sdf.parse(endDate);

        if (isBlank(category)) {
//            カテゴリー情報なし投稿取得処理
             results = messageRepository.findAllByOrderByCreateDateDesc(limit, start, end);
        } else {
//            カテゴリー情報あり投稿取得処理
            results = messageRepository.findAllByWHERECategoryOrderByCreateDateDesc(limit, start, end, category);
        }

        List<UserMessageForm> messages = setUserMessageForm(results);
        return messages;
    }

    private List<UserMessageForm> setUserMessageForm(List<Message> results) {
        List<UserMessageForm> messages = new ArrayList<>();
        for (Message message : results) {
            UserMessageForm userMessageForm = new UserMessageForm();
            userMessageForm.setId(message.getId());
            userMessageForm.setText(message.getText());
            userMessageForm.setTitle(message.getTitle());
            userMessageForm.setUserId(message.getUserId());
            userMessageForm.setUserName(message.getUser().getName());
            userMessageForm.setCreatedDate(message.getCreatedDate());
            userMessageForm.setCategory(message.getCategory());
            userMessageForm.setDepartmentId(message.getUser().getDepartmentId());
            userMessageForm.setBranchId(message.getUser().getBranchId());
            messages.add(userMessageForm);
        }
        return messages;
    }
    /*
    投稿追加処理
     */
    @Transactional
    public void save(MessageForm messageForm, UserForm loginUser) {
        messageForm.setUserId(loginUser.getId());
        Message message = setMessage(messageForm);
        messageRepository.save(message);
        userRepository.countUpMessage(loginUser.getId());
        branchRepository.countUpMessage(loginUser.getBranchId());
    }

    private Message setMessage(MessageForm messageForm) {
        Message message = new Message();
        message.setId(messageForm.getId());
        message.setTitle(messageForm.getTitle());
        message.setText(messageForm.getText());
        message.setCategory(messageForm.getCategory());
        message.setUserId(messageForm.getUserId());
        return message;
    }
    /*
    投稿取得処理
     */
    public MessageForm findMessage(int id) {
        Message result = messageRepository.findById(id).orElse(null);
        MessageForm messageForm = setMessageForm(result);
        return messageForm;
    }

    private MessageForm setMessageForm(Message result) {
        MessageForm messageForm = new MessageForm();
        messageForm.setId(result.getId());
        messageForm.setTitle(result.getTitle());
        messageForm.setText(result.getText());
        messageForm.setCategory(result.getCategory());
        messageForm.setUserId(result.getUserId());
        messageForm.setCreatedDate(result.getCreatedDate());
        messageForm.setUpdatedDate(result.getUpdatedDate());
        return messageForm;
    }

    /*
    投稿削除処理
     */
    @Transactional
    public void deleteMessage(int id, int messageUserId, int messageBranch) {
        messageRepository.deleteById(id);
        userRepository.countDownMessage(messageUserId);
        branchRepository.countDownMessage(messageBranch);
        List<Comment> comments = commentRepository.findByMessageId(id);
        if (!comments.isEmpty()) {
            for (Comment comment : comments) {
                commentService.deleteComment(comment.getId(), comment.getUserId());
            }
        }
    }
}
