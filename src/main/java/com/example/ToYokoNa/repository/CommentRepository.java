package com.example.ToYokoNa.repository;

import com.example.ToYokoNa.repository.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("SELECT c FROM Comment c ORDER BY createdDate ASC LIMIT :limit")
    List<Comment> findAllComments(@Param("limit") int limit);

    List<Comment> findByMessageId(int id);
}
