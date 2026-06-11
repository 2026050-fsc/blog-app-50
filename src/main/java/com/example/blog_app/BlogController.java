// コントローラー

/**
 * @author 由迫ひかり
 * @version 4.0.6
 * @since 2026/6/4
 */

package com.example.blog_app;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlogController {

    private final BlogService blogService;

    /**
     * BlogControllerのコンストラクタ
     * BlogServiceのインスタンスを注入
     * 
     * @param blogService ブログのビジネスロジックを処理するサービスクラス
     */
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    /**
     * ブログの一覧画面を表示
     * 
     * @param model 画面にデータを渡すためのModelオブジェクト
     * @return ブログ一覧画面("blogs")
     */
    @GetMapping("/blogs")
    public String blogs(Model model) {
        model.addAttribute("blogs", blogService.findAll());
        return "blogs";
    }

    /**
     * ブログの新規投稿を行い、一覧画面へリダイレクト
     * フォームデータをBlogServiceでチェックし、データベースへの保存処理を行う
     * 
     * @param form 画面から送られてきた入力データを格納したフォームオブジェクト
     * @return 登録完了後はブログ一覧画面へのリダイレクトパスへ
     */
    @PostMapping("/blogs")
    public String create(@ModelAttribute BlogForm form) {
        // @ModelAttribute: 画面(View)から送られてきたパラメータ(フォームの入力値)を、Javaのオブジェクトに自動的に変換
        // BlogForm
        // formにこのアノテーションをつけることで、ユーザーが入力した「タイトル」や「本文」がBlogFormオブジェクトの対応するフィールドに自動で格納される
        blogService.add(form);
        // new.htmlでformを送信→controllerのcreateメソッドを実行→serviceのadd(入力チェックのビジネスロジックでデータの通過を判断)→repositoryのsaveメソッドを実行→SQL(insert)を実行
        return "redirect:/blogs";
    }

    /**
     * ブログの新規投稿画面を表示する
     * 
     * @return ブログ新規投稿画面("blogs/new")
     */
    @GetMapping("/blogs/new")
    // "/blogs/new"へのGETリクエストが届いたときに呼び出される
    public String newBlog() {
        return "blogs/new";
    }

    /**
     * idで指定されたブログの詳細画面を表示
     * 該当するブログが見つからない場合は、ブログ一覧画面へリダイレクト
     * 
     * @param id    参照したいブログのid
     * @param model 画面に取得したブログのデータを渡すためのModelオブジェクト
     * @return
     */
    @GetMapping("/blogs/{id}")
    public String detailBlog(@PathVariable Long id, Model model) {
        // @PathVariableでパスの一部を受け取りLong型のidという名前の変数に代入
        Optional<Blog> blogOpt = blogService.detailBlog(id);
        if (blogOpt.isEmpty()) {
            return "redirect:/blogs";
        }
        model.addAttribute("blog", blogOpt.get());
        return "blogs/detail";
    }

    /**
     * ブログサイト主のプロフィール画面を表示
     * 
     * @return プロフィールページ("blogs/profile")
     */
    @GetMapping("/blogs/profile")
    public String profileBlog() {
        return "blogs/profile";
    }

    /**
     * idで指定されたブログを削除する
     * 削除処理完了後は、最新の状態を反映したブログ一覧画面へリダイレクト
     * 
     * @param id 削除の対象となるブログのid
     * @return ブログ一覧画面へのリダイレクトパス
     */
    @PostMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return "redirect:/blogs";
    }
}
