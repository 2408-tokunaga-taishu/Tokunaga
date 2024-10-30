package com.example.ToYokoNa.repository;

import com.example.ToYokoNa.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByAccountAndPassword(String account, String password);

    User findByAccount(String account);

    //     投稿数カウントアップ
    @Modifying
    @Query("UPDATE User SET messageCount = messageCount + 1 WHERE id = :id")
    void countUpMessage(@Param("id") int id);

    //      投稿数カウントダウン
    @Modifying
    @Query("UPDATE User SET messageCount = messageCount - 1 WHERE id = :id")
    void countDownMessage(@Param("id") int id);

    //    コメントの投稿数のカウントアップ
    @Modifying
    @Query("UPDATE User SET commentCount = commentCount + 1 WHERE id = :id")
    void countUpComment(@Param("id") int id);

    //    コメントの投稿数のカウントダウン
    @Modifying
    @Query("UPDATE User SET commentCount = commentCount - 1 WHERE id = :id")
    void countDownComment(@Param("id") int id);


}