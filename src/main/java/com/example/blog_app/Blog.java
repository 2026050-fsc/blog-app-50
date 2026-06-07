package com.example.blog_app;

// Blog.javaはDTOで、画面→コントローラー→サービスへとデータの入力時の受け渡しを担う
public class Blog {
    private final Long id; // ブログID
    private final String title; // ブログタイトル
    private final String notes; // ブログ本文

    public Blog(Long id, String title, String notes) {
        this.id = id;
        this.title = title;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNotes() {
        return notes;
    }

}
