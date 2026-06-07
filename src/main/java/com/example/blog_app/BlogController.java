package com.example.blog_app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {
    @GetMapping("/blogs")
    public String blogs() {
        return "blogs";
    }

    @GetMapping("/blogs/new")
    public String newBlog() {
        return "blogs/new";
    }

    @GetMapping("/blogs/detail")
    public String detailBlog() {
        return "blogs/detail";
    }
}
