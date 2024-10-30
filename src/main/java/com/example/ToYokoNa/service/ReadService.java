package com.example.ToYokoNa.service;

import com.example.ToYokoNa.repository.ReadRepository;
import com.example.ToYokoNa.repository.entity.Read;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadService {
    @Autowired
    ReadRepository readRepository;

    // 既読か未読かをexistsでbooleanで結果をもらう
    public boolean existsByUserIdAndPostId(Integer UserId, Integer MessageId) {
        return readRepository.existsByUserIdAndMessageId(UserId, MessageId);
    }

    // 既読を未読に変更する処理
    public void deleteByUserIdAndPostId(Integer UserId, Integer MessageId) {
        readRepository.deleteByUserIdAndMessageId(UserId, MessageId);
    }

    // 未読を既読に変更する処理
    public void save(Integer UserId, Integer MessageId) {
        Read saveRead = setReadEntity(UserId, MessageId);
        readRepository.save(saveRead);
    }

    public Read setReadEntity(Integer UserId, Integer MessageId) {
        Read read = new Read();
        read.setUserId(UserId);
        read.setMessageId(MessageId);
        return read;
    }

    public List<Integer> findReadMessages(Integer UserId) {
        // 既読状態の投稿のIdを取得
        return readRepository.getAllMessageIdsByUserId(UserId);
    }
}
