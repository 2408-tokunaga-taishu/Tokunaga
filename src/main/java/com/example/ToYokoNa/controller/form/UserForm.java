package com.example.ToYokoNa.controller.form;

import com.example.ToYokoNa.Validation.CheckBlank;
import com.example.ToYokoNa.Validation.Unique;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Component
public class UserForm {

    //ログインorユーザ登録・編集でバリデーションの使用を分けたいのでグループ分け
    public static interface UserLogin{}
    public static interface UserCreate{} // 入力の必須チェックメイン
    public static interface UserEdit{}


    private int id;

    @CheckBlank(message = "アカウントを入力してください", groups = {UserLogin.class, UserCreate.class, UserEdit.class})
    @Unique(groups = {UserCreate.class}) //重複チェック
    private String account;

    @AssertTrue(message = "アカウントは半角英数字かつ6文字以上20文字以下で入力してください", groups = {UserCreate.class, UserEdit.class})
    private boolean isAccountValid() {
        if (account.isBlank()) {
            return true; //アカウント名が空の場合は@CheckBlankでまずバリデーションするため処理を抜ける
        } else {
            // 以下の条件を満たしていない場合はエラーメッセージを表示する
            return (account.length() >= 6 && account.length() <= 20 && account.matches("^[A-Za-z0-9]+$"));
        }
    }

    @CheckBlank(message = "パスワードを入力してください", groups = {UserLogin.class, UserCreate.class})
    private String password;
    private String passCheck;

    @AssertTrue(message = "パスワードは半角文字かつ6文字以上20文字以下で入力してください", groups = {UserCreate.class})
    private boolean isPasswordValid() {
        if (password.isBlank()) {
            return true; //パスワードが空の場合は@CheckBlankでまずバリデーションするため処理を抜ける
        } else {
            // 以下の条件を満たしていない場合はエラーメッセージを表示する
            return (password.length() >= 6 && password.length() <= 20 && password.matches("^[\\x20-\\x7E]+$"));
        }
    }

    @AssertTrue(message = "パスワードと確認用パスワードが一致しません", groups = {UserCreate.class, UserEdit.class})
    private boolean isSamePassword() {
        return Objects.equals(password, passCheck);
    }

    @CheckBlank(message = "氏名を入力してください", groups = {UserCreate.class, UserEdit.class})
    @Size(max = 10)
    private String name;

    @AssertTrue(message = "氏名は10文字以下で入力してください", groups = {UserCreate.class, UserEdit.class})
    private boolean isMessageValid() {
        if (name.isBlank()) {
            return true;
        } else {
            return (name.length() <= 10);
        }
    }

    @NotNull(message = "支社を選択してください", groups = {UserCreate.class, UserEdit.class})
    private Integer branchId;

    @NotNull(message = "部署を選択してください", groups = {UserCreate.class, UserEdit.class})
    private Integer departmentId;

    @AssertTrue(message = "支社と部署の組み合わせが不正です", groups = {UserCreate.class, UserEdit.class})
    public boolean isRelationValid() {
        if (branchId == null || departmentId == null) {
            return true;
        }
        if (branchId == 1) { //支社が本社の場合の正しい組み合わせ
            return (departmentId == 1 || departmentId == 2);
        } else { //支社が本社以外の場合の正しい組み合わせ
            return (departmentId == 3 || departmentId == 4);
        }
    }

    @AssertTrue(message = "パスワードは半角文字かつ6文字以上20文字以下で入力してください", groups = {UserEdit.class})
    public boolean isEditPasswordValid() {
        //  パスワードが2つとも何も入力されていなければバリデーションはかけない
        if (password.isEmpty() && passCheck.isEmpty()) {
            return true;
        }
        if (password.length() >= 6 && password.length() <= 20 && password.matches("^[\\x20-\\x7E]+$")) {
            return true;
        }
        return false;
    }

    private Integer isStopped;
    private Date createdDate;
    private Date updatedDate;
    private String loginTime;
    private int messageCount;
    private int commentCount;
    private long version;
}