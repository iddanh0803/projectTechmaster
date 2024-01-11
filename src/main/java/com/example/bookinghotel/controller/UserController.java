package com.example.bookinghotel.controller;

import com.example.bookinghotel.entity.Role;
import com.example.bookinghotel.entity.User;
import com.example.bookinghotel.model.dto.UserDto;
import com.example.bookinghotel.repository.RoleRepository;
import com.example.bookinghotel.repository.UserRepository;
import com.example.bookinghotel.service.ImageService;
import com.example.bookinghotel.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class UserController {
    private final UserService userService;
    private final ImageService imageService;
    private final RoleRepository roleRepository;

    public UserController(UserService userService,ImageService imageService, RoleRepository roleRepository) {
        this.userService = userService;
        this.imageService = imageService;
        this.roleRepository = roleRepository;
    }
    @GetMapping
    public String getUserPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                              Model model) {
        Page<User> pageData = userService.getAllUser(page, pageSize);
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);
        return "admin/user/index";
    }
    //2. Tạo user
    @GetMapping("/create")
    public String getUserCreatePage(Model model) {
        List<Role> roleList = roleRepository.findAll();
        model.addAttribute("roleList", roleList);
        return "admin/user/create";
    }


    //3. Chi tiết user
    @GetMapping("/{id}/detail")
    public String getUserDetailPage(@PathVariable Integer id, Model model) {
        UserDto user = userService.getUserById(id);
        List<Role> roleList = roleRepository.findAll();

        model.addAttribute("user", user);
        model.addAttribute("roleList", roleList);
        model.addAttribute("imageList", imageService.getFilesOfCurrentUser());
        return "admin/user/detail";
    }
}
