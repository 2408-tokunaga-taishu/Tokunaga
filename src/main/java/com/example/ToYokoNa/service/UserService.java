package com.example.ToYokoNa.service;

import com.example.ToYokoNa.controller.form.UserForm;
import com.example.ToYokoNa.repository.MessageRepository;
import com.example.ToYokoNa.repository.UserRepository;
import com.example.ToYokoNa.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    /*
     * ログインユーザー情報取得(ログイン処理)
     */
    public UserForm selectUser(UserForm userForm) throws Exception {
        String account = userForm.getAccount();
        String password = userForm.getPassword();
        User result = userRepository.findByAccountAndPassword(account, password);
        if (result == null) {
            // Controllerにエラーを投げる
            throw new Exception("ログインに失敗しました");
        }
        UserForm user = setUserForm(result);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String loginDate = sdf.format(new Date());
        user.setLoginTime(loginDate);
        return user;
    }

    private UserForm setUserForm(User result) {
        UserForm user = new UserForm();
        user.setId(result.getId());
        user.setPassword(result.getPassword());
        user.setAccount(result.getAccount());
        user.setName(result.getName());
        user.setBranchId(result.getBranchId());
        user.setDepartmentId(result.getDepartmentId());
        user.setIsStopped(result.getIsStopped());
        user.setVersion(result.getVersion());
        return user;
    }
    /*
     * ユーザー管理画面表示
     */
    public List<UserForm> findAllUser() {
        List<User> results = userRepository.findAll();
        List<UserForm> users = setUserForm(results);
        return users;
    }

    private List<UserForm> setUserForm(List<User> results) {
        List<UserForm> users = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            UserForm user = new UserForm();
            User result = results.get(i);
            user.setId(result.getId());
            user.setAccount(result.getAccount());
            user.setName(result.getName());
            user.setBranchId(result.getBranchId());
            user.setDepartmentId(result.getDepartmentId());
            user.setIsStopped(result.getIsStopped());
            user.setMessageCount(result.getMessageCount());
            user.setCommentCount(result.getCommentCount());
            users.add(user);
        }
        return users;
    }

    /*
     * ユーザ情報の更新(ユーザの稼働状態と新規登録)
     */
    public void saveUser(UserForm userForm) {
        User saveUser = setUserEntity(userForm);
        userRepository.save(saveUser);
    }

    public void updateUser(UserForm userForm) throws Exception {
        // アカウント名重複チェック
        User user = userRepository.findByAccount(userForm.getAccount());
        if (user != null) {
            if (user.getId() != userForm.getId()) {
                throw new Exception("アカウントが重複しています");
            }
        }
        User updateUser = setUserEntity(userForm);
        userRepository.save(updateUser);
    }

    private User setUserEntity(UserForm userForm) {
        User user = new User();
        user.setId(userForm.getId());
        user.setPassword(userForm.getPassword());
        user.setAccount(userForm.getAccount());
        user.setName(userForm.getName());
        user.setBranchId(userForm.getBranchId());
        user.setDepartmentId(userForm.getDepartmentId());
        user.setIsStopped(userForm.getIsStopped());
        user.setUpdatedDate(new Date());
        user.setVersion(userForm.getVersion());
        return user;
    }

    public UserForm findUser(int id) {
        User result = userRepository.findById(id).orElse(null);
        UserForm user = setUserForm(result);
        return user;
    }
}