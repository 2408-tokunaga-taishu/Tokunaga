package com.example.ToYokoNa.repository;

import com.example.ToYokoNa.repository.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
@Transactional
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
//    カテゴリー情報なし
    @Query("SELECT m FROM Message m WHERE createdDate BETWEEN :start AND :end ORDER BY createdDate DESC")
    public Page<Message> findAllByOrderByCreateDateDesc( @Param("start") Date start, @Param("end") Date end,  @Param("pageable") Pageable pageable);

//    カテゴリー情報あり
    @Query("SELECT m FROM Message m WHERE createdDate BETWEEN :start AND :end AND category LIKE %:category% ORDER BY createdDate DESC")
    public Page<Message> findAllByWHERECategoryOrderByCreateDateDesc( @Param("start") Date start, @Param("end") Date end, @Param("category") String category,  @Param("pageable") Pageable pageable);


}
