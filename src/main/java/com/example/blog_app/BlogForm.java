// データの保存時

/**
 * @author 由迫ひかり
 * @version 4.0.6
 * @since 2026/6/4
 */

package com.example.blog_app;

import org.springframework.web.multipart.MultipartFile;

// BlogForm.javaはEntityで、サービス→リポジトリ→データベースへとデータの保存時の受け渡しを担う
public class BlogForm {
    // @RequestParamで引数に並べて受け取る形では、入力欄が増えるたびにメソッドの引数リストが長くなる
    // 複数のフィールドをまとめたフォームクラスを作り、Spring Bootがリクエストの値をフィールドに割り当てる形にする目的
    private String title; // ブログタイトル
    private String notes; // ブログ本文
    private MultipartFile imgs; // ブログ画像

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public MultipartFile getImgs() {
        return imgs;
    }

    public void setImgs(MultipartFile imgs) {
        this.imgs = imgs;
    }
}
