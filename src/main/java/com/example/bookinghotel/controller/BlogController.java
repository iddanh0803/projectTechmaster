package com.example.bookinghotel.controller;


import com.example.bookinghotel.entity.Blog;
import com.example.bookinghotel.service.BlogService;
import com.example.bookinghotel.service.ImageService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/admin/blogs")
public class BlogController {
    private final BlogService blogService;
    private final ImageService imageService;
    public BlogController(BlogService blogService, ImageService imageService) {
        this.blogService = blogService;
        this.imageService = imageService;
    }

    // Danh sách tất cả bài viết
    @GetMapping
    public String getBlogPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                              Model model) {
        Page<Blog> pageData = blogService.getAllBlogs(page, pageSize);
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);
        return "admin/blog/index";
    }

    // Danh sách bài viết của tôi
    @GetMapping("/own-blogs")
    public String getOwnBlogPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                                 @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                 Model model) {
        Page<Blog> pageData = blogService.getAllBlogsOfCurrentUser(page, pageSize);
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);
        return "admin/blog/own-blog";
    }

    // Tạo bài viết
    @GetMapping("/create")
    public String getBlogCreatePage() {
        return "admin/blog/create";
    }

    // Chi tiết bài viết
    @GetMapping("/{id}/detail")
    public String getBlogDetailPage(@PathVariable Integer id, Model model) {
        Blog blog = blogService.getBlogById(id);
        model.addAttribute("blog", blog);
        model.addAttribute("imageList", imageService.getFilesOfCurrentUser());
        return "admin/blog/detail";
    }
}