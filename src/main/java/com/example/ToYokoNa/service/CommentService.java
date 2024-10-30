package com.example.ToYokoNa.service;

import com.example.ToYokoNa.controller.form.CommentForm;
import com.example.ToYokoNa.controller.form.UserCommentForm;
import com.example.ToYokoNa.controller.form.UserForm;
import com.example.ToYokoNa.repository.BranchRepository;
import com.example.ToYokoNa.repository.CommentRepository;
import com.example.ToYokoNa.repository.UserRepository;
import com.example.ToYokoNa.repository.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    UserService userService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BranchRepository branchRepository;

    @Transactional
    public void saveComment(UserForm loginUser, CommentForm commentForm) {
        commentForm.setUserId(loginUser.getId());
        Comment comment = setComment(commentForm);
        commentRepository.save(comment);
        userRepository.countUpComment(loginUser.getId());
        branchRepository.countUpComment(loginUser.getBranchId());
    }

    private Comment setComment(CommentForm commentForm) {
        Comment comment = new Comment();
        comment.setId(commentForm.getId());
        comment.setText(commentForm.getText());
        comment.setUserId(commentForm.getUserId());
        comment.setMessageId(commentForm.getMessageId());
        comment.setCreatedDate(commentForm.getCreatedDate());
        comment.setUpdatedDate(commentForm.getUpdatedDate());
        return comment;
    }

    public List<UserCommentForm> findAllComments() {
        int limit = 1000;
        List<Comment> results = commentRepository.findAllComments(limit);
        List<UserCommentForm> comments = setUserCommentForm(results);
        return comments;
    }

    private List<UserCommentForm> setUserCommentForm(List<Comment> results) {
        List<UserCommentForm> comments = new ArrayList<>();
        for (Comment comment : results) {
            UserCommentForm userCommentForm = new UserCommentForm();
            userCommentForm.setId(comment.getId());
            userCommentForm.setText(comment.getText());
            userCommentForm.setUserId(comment.getUserId());
            userCommentForm.setMessageId(comment.getMessageId());
            userCommentForm.setUserName(comment.getUser().getName());
            userCommentForm.setUserAccount(comment.getUser().getAccount());
            userCommentForm.setCreatedDate(comment.getCreatedDate());
            comments.add(userCommentForm);
        }
        return  comments;
    }
    /*
    コメントの削除処理
     */
    @Transactional
    public void deleteComment(int id, int userId) {
        commentRepository.deleteById(id);
        userRepository.countDownComment(userId);
        branchRepository.countDownComment(userService.findUser(userId).getBranchId());

    }
}
