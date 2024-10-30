package com.example.ToYokoNa.repository;

import com.example.ToYokoNa.repository.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
    //     投稿数カウントアップ
    @Modifying
    @Query("UPDATE Branch SET messageCount = messageCount + 1 WHERE id = :branchId")
    void countUpMessage(@Param("branchId") int branchId);

    //      投稿数カウントダウン
    @Modifying
    @Query("UPDATE Branch SET messageCount = messageCount - 1 WHERE id = :branchId")
    void countDownMessage(@Param("branchId") int branchId);

    //    コメントの投稿数のカウントアップ
    @Modifying
    @Query("UPDATE Branch SET commentCount = commentCount + 1 WHERE id = :branchId")
    void countUpComment(@Param("branchId") int branchId);

    //    コメントの投稿数のカウントダウン
    @Modifying
    @Query("UPDATE Branch SET commentCount = commentCount - 1 WHERE id = :branchId")
    void countDownComment(@Param("branchId") int branchId);

    List<Branch> findAllByOrderByIdAsc();
}