package com.example.bookinghotel.service;

import com.example.bookinghotel.entity.User;
import com.example.bookinghotel.exception.NotFoundException;
import com.example.bookinghotel.model.dto.UserDto;
import com.example.bookinghotel.model.request.UpdateUserRequest;
import com.example.bookinghotel.model.request.UpsertUserRequest;
import com.example.bookinghotel.repository.RoleRepository;
import com.example.bookinghotel.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }
    public UserDto getUserById(Integer id) {
        User user =  userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Không tìm thấy user"));
        return new UserDto(user);
    }
    public Page<User> getAllUser(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("username").descending());
        return userRepository.findAll(pageable);
    }
    public User createUser(UpsertUserRequest request) {
//        if (request.getUsername() != null){
//            throw new RuntimeException("Username already exists");
//        }
        System.out.println(request);
        User user = new User();
        user.setUsername(request.getUsername());
        String encodePassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodePassword);
        user.setEmail(request.getEmail());
        user.setRoles(roleRepository.findByIdIn(request.getRoles()));
        userRepository.save(user);
        return user;
    }
    public User updateUser(UpdateUserRequest request, Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Not found user with id: " + id));
        user.setUsername(request.getUsername());
        user.setFull_name(request.getFull_name());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setAvatar(request.getAvatar());
        user.setNationality(request.getNationality());
        user.setRoles(roleRepository.findByIdIn(request.getRoles()));
        userRepository.save(user);
        return user;
    }
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found user with id: " + id));
        userRepository.delete(user);
    }


}
