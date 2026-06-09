package com.example.blog_app;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class BlogRepository {
    private final JdbcClient jdbcClient;

    public BlogRepository (JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Blog> findAll() {
        return jdbcClient.sql("SELECT id, title, notes, imgs FROM blogs")
                .query(Blog.class)
                // 結果の1行を1つのBlogにする
                // このとき、Blogのコンストラクターを呼び、引数と同じ名前の列の値を渡す
                .list();
    }

    public void save(Blog blog) {
        jdbcClient.sql("INSERT INTO blogs (title, notes, imgs) VALUES (:title, :notes, :imgs)")
        // 名前付きパラメーター(:名前 という目印をSQLに置き、paramで渡す)
                .param("title", blog.getTitle())
                .param("notes", blog.getNotes())
                .param("imgs", blog.getImgs())
                .update();
    }

    public Optional<Blog> detailBlog(Long id) {
        return jdbcClient.sql("SELECT id, title, notes, imgs FROM blogs WHERE id = :id")
                .param("id", id)
                .query(Blog.class)
                .optional();
    }
}
