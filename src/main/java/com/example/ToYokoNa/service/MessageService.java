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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
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

//    開始時間変換
    private Date startConvert(String startDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (isBlank(startDate)) {
            startDate = "2022-01-01 00:00:00";
        } else {
            startDate = startDate + " 00:00:00";
        }
        return sdf.parse(startDate);
    }
//    終了時間変換
    private Date endConvert(String endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (isBlank(endDate)) {
            endDate = sdf.format(new Date());
        } else {
            endDate = endDate + " 23:59:59";
        }
        return sdf.parse(endDate);
    }
    /*
    全投稿取得処理
     */
    public Page<UserMessageForm> findALLMessages(String startDate, String endDate, String category, Pageable pageable) throws ParseException {
//       取得件数定数
        int limit = 1000;
//        絞込日付作成処理
        Page<Message> results ;
        Date start = startConvert(startDate);
        Date end = endConvert(endDate);
        if (isBlank(category)) {
//            カテゴリー情報なし投稿取得処理
             results = messageRepository.findAllByOrderByCreateDateDesc(start, end,pageable);
        } else {
//            カテゴリー情報あり投稿取得処理
            results = messageRepository.findAllByWHERECategoryOrderByCreateDateDesc(start, end, category,pageable);
        }
        return setUserMessageForm(results, pageable);
    }

    private Page<UserMessageForm> setUserMessageForm(Page<Message> results, Pageable pageable) {
        List<UserMessageForm> userMessageForms = new ArrayList<>();
        for (Message message : results.getContent()) {
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
            userMessageForm.setDurationTime(durationDate(message.getCreatedDate()));
            userMessageForms.add(userMessageForm);
        }
        return new PageImpl<>(userMessageForms, pageable, results.getTotalElements());
    }
//    現在時刻との差分計算 分、時、日表記
    private String durationDate(Date date) {
        LocalDateTime createDate = toLocalDateTime(date);
        LocalDateTime nowDate = toLocalDateTime(new Date());
        Duration duration = Duration.between(createDate, nowDate);
        Long seconds = duration.getSeconds();
        String durationDate = "";
        if (seconds < 3600 && seconds >= 60) {
            long minute = duration.toMinutes();
            durationDate = minute +"分前";
        } else if (seconds >= 3600 && seconds < 86400) {
            long hour = duration.toHours();
            durationDate = hour +"時間前";
        } else if (seconds >= 86400 && seconds < 31536000) {
            long day = duration.toDays();
            durationDate = day +"日前";
        } else if (seconds < 60 ){
            durationDate = seconds + "秒前";
        } else {
            durationDate =  new SimpleDateFormat("yyyy/MM/dd").format(date);
        }
        return durationDate;
    }

//    DateをLocalDateTimeに変換メソッド
        private static LocalDateTime toLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
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
