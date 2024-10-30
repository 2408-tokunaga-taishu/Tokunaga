package com.example.ToYokoNa.repository;

import com.example.ToYokoNa.repository.entity.Message;
import com.example.ToYokoNa.repository.entity.NgWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NgWordRepository extends JpaRepository<NgWord, Integer> {
}
