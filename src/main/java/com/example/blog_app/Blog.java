package com.example.blog_app;

// Blog.javaはDTOで、画面→コントローラー→サービスへとデータの入力時の受け渡しを担う
public class Blog {
    private final Long id; // ブログID
    private final String title; // ブログタイトル
    private final String notes; // ブログ本文
    private final String imgs; // ブログ画像

    public Blog(Long id, String title, String notes, String imgs) {
        this.id = id;
        this.title = title;
        this.notes = notes;
        this.imgs = imgs;
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

    public String getImgs() {
        return imgs;
    }
}
