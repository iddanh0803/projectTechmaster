package com.example.bookinghotel.service;

import com.example.bookinghotel.entity.Blog;
import com.example.bookinghotel.exception.NotFoundException;
import com.example.bookinghotel.model.request.UpsertBlogRequest;
import com.example.bookinghotel.repository.BlogRepository;
import com.example.bookinghotel.repository.UserRepository;
import com.example.bookinghotel.security.CustomUserDetails;
import com.github.slugify.Slugify;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    public BlogService(BlogRepository blogRepository, UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }

    public Page<Blog> findAll(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("publishedAt").descending());
        return blogRepository.findByStatus(true, pageable);
    }

    public List<Blog> searchByTitle(String title) {
        return blogRepository.findByTitleContainingIgnoreCase(title);
    }

    public Blog findByIdAndSlug(Integer id, String slug) {
        return blogRepository.findByIdAndSlugAndStatusTrue(id, slug)
                .orElseThrow(() -> new NotFoundException("Cannot find blog"));
    }

    public Page<Blog> getAllBlogs(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createdAt").descending());
        return blogRepository.findAll(pageable);
    }
    public List<Blog> findByStatusOrderByCreatedAtDesc(Boolean status){
        return blogRepository.findByStatusOrderByCreatedAtDesc(status);
    }
    public Page<Blog> getAllBlogsOfCurrentUser(Integer page, Integer limit) {
        // TODO : Giả định userId = 1, sau này userId chính là user đang login
        Integer userId = 1;
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createdAt").descending());
        return blogRepository.findByUser_IdOrderByCreatedAtDesc(
                userId,
                pageable
        );
    }

    public Blog createBlog(UpsertBlogRequest request) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Tao blog
        Slugify slugify = Slugify.builder().build();
        Blog blog = Blog.builder()
                .title(request.getTitle())
                .slug(slugify.slugify(request.getTitle()))
                .content(request.getContent())
                .description(request.getDescription())
                .status(request.getStatus())
                .user(customUserDetails.getUser())
                .build();

        return blogRepository.save(blog);
    }

    public Blog getBlogById(Integer id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found blog with id = " + id));
    }

    public Blog updateBlog(Integer id, UpsertBlogRequest request) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found blog with id = " + id));

        // TODO: Validate thông tin (nếu cần thiết) - validation
        Slugify slugify = Slugify.builder().build();
        blog.setTitle(request.getTitle());
        blog.setDescription(request.getDescription());
        blog.setContent(request.getContent());
        blog.setThumbnail(request.getThumbnail());
        blog.setStatus(request.getStatus());
        return blogRepository.save(blog);
    }

    public void deleteBlog(Integer id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found blog with id = " + id));

        blogRepository.delete(blog);
    }
}