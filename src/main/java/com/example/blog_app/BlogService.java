// サービス

/**
 * @author 由迫ひかり
 * @version 4.0.6
 * @since 2026/6/4
 */

package com.example.blog_app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public List<Blog> findAll() {
        // コントローラーからタスクの一覧を求められたときにそのままリポジトリに丸投げしてデータを引き渡す
        return blogRepository.findAll();
        // データベースにあるすべてのブログをList<Blog>(ブログの詰め合わせ箱)の形で取ってきて、returnでそのまま呼び出し元(コントローラー)に返す
    }

    public void add(BlogForm form) {
        if (form.getTitle() == null || form.getTitle().isEmpty()) {
            // タイトルの入力チェック
            // HTMLの<input>フォームから空のまま保存ボタンを押した場合、Java側(BlogForm)のtitleにはnullではなく、空文字("")が届いてしまう
            // そのためisEmpty()で空文字("")もチェックする
            // isEmpty()を使った場合、ユーザーが間違えてスペース(空白)だけを入力して送信ボタンを押したときに、チェックをすり抜けて空白のタスクが登録されてしまうため、まぁ注意
            // 上位互換的存在にisBlank()
            throw new IllegalArgumentException("タイトルが空です");
        }

        if (form.getNotes() == null || form.getNotes().isEmpty()) {
            // 本文の入力チェック
            // 詳細は上記のタイトルの入力チェックと同じ
            throw new IllegalArgumentException("本文が空です");
        }

        MultipartFile file = form.getImgs();
        String fileName = file.getOriginalFilename();

        try {
            Path filePath = Paths.get("src/main/resources/static/images/" + fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        blogRepository.save(new Blog(null, form.getTitle(), form.getNotes(), fileName));
        // 上記のビジネスロジックを通過した安全なデータだけをデータベースに保存する
        // 組み立てたBlogオブジェクトをリポジトリのsaveメソッドに手渡し、INSERTの命令
        // nullはsave時(新規登録時)段階ではまだidが決まっていないため、idの部分にnullを渡している
        // 引数を設定しないと、Blogのフィールド数と数が合わずエラーが起きる
    }

    public Optional<Blog> detailBlog(Long id) {
        return blogRepository.detailBlog(id);
    }

    public void deleteBlog(Long id) {
        blogRepository.deleteBlog(id);
    }
}
