package com.example.bookinghotel.rest;


import com.example.bookinghotel.model.request.UpsertBlogRequest;
import com.example.bookinghotel.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/blogs")
public class BlogResource {
    @Autowired
    private BlogService blogService;

    @GetMapping
    public ResponseEntity<?> getAllBlogs(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(blogService.findAll(page, pageSize));
    }

    @GetMapping("/own-blogs")
    public ResponseEntity<?> getAllBlogOfCurrentUser(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                     @RequestParam(required = false,defaultValue = "10") Integer limit){
        return ResponseEntity.ok(blogService.getAllBlogsOfCurrentUser(page, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBlogById(@PathVariable Integer id){
        return ResponseEntity.ok(blogService.getBlogById(id));
    }

    @PostMapping("create")
    public ResponseEntity<?> createBlog(@RequestBody UpsertBlogRequest request){
        return new ResponseEntity<>(blogService.createBlog(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBlog(@RequestBody UpsertBlogRequest request, @PathVariable Integer id){
        return ResponseEntity.ok(blogService.updateBlog(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable Integer id){
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }
}
