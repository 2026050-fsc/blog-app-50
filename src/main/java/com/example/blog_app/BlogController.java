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

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/blogs")
    public String blogs(Model model) {
        model.addAttribute("blogs", blogService.findAll());
        return "blogs";
    }

    @PostMapping("/blogs")
    public String create(@ModelAttribute BlogForm form) {
        // @ModelAttribute: 画面(View)から送られてきたパラメータ(フォームの入力値)を、Javaのオブジェクトに自動的に変換
        // BlogForm
        // formにこのアノテーションをつけることで、ユーザーが入力した「タイトル」や「本文」がBlogFormオブジェクトの対応するフィールドに自動で格納される
        blogService.add(form);
        // new.htmlでformを送信→controllerのcreateメソッドを実行→serviceのadd(入力チェックのビジネスロジックでデータの通過を判断)→repositoryのsaveメソッドを実行→SQL(insert)を実行
        return "redirect:/blogs";
    }

    @GetMapping("/blogs/new")
    // "/blogs/new"へのGETリクエストが届いたときに呼び出される
    public String newBlog() {
        return "blogs/new";
    }

    // @GetMapping("/blogs/detail")
    // public String detailBlog() {
    //     return "blogs/detail";
    // }

    // @GetMapping("/blogs/detail")
    // public String detailBlog(Model model) {
    //     model.addAttribute("blogs", blogService.findAll());
    //     return "blogs/detail";
    // }

    // @GetMapping("/blogs/{id}")
    // public String detailBlog(@PathVariable Long id, Model model) {
    //     // @PathVariableでパスの一部を受け取りLong型のidという名前の変数に代入
    //     blogService.detailBlog(id);
    //     // Serviceを介してブログを取得
    //     model.addAttribute("blog" , blog);
    //     return "redirect:/detail";
    // }

    @GetMapping("/blogs/{id}")
    public String detailBlog(@PathVariable Long id, Model model) {
        // @PathVariableでパスの一部を受け取りLong型のidという名前の変数に代入
        Optional<Blog> blogOpt = blogService.detailBlog(id);
        if (blogOpt.isEmpty()) {
            return "redirect:/blogs";
        }
        model.addAttribute("blog" , blogOpt.get());
        return "blogs/detail";
    }

}
