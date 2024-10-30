package com.example.ToYokoNa.repository;

import com.example.ToYokoNa.repository.entity.Read;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadRepository extends JpaRepository<Read, Integer> {
    boolean existsByUserIdAndMessageId(Integer userId, Integer messageId);

    @Transactional
    void deleteByUserIdAndMessageId(Integer userId, Integer messageId);

    @Query(value = "select message_id from read where user_id = :UserId", nativeQuery = true)
    public List<Integer> getAllMessageIdsByUserId(Integer UserId);
}
