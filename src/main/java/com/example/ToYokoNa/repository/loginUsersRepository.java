package com.example.ToYokoNa.repository;

import com.example.ToYokoNa.repository.entity.loginUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface loginUsersRepository extends JpaRepository<loginUsers, Integer> {
    public loginUsers findByAccount(String account);
    @Transactional
    public void deleteByAccount(String account);
}
