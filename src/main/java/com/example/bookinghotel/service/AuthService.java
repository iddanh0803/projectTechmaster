package com.example.bookinghotel.service;


import com.example.bookinghotel.entity.Role;
import com.example.bookinghotel.entity.TokenConfirm;
import com.example.bookinghotel.entity.User;
import com.example.bookinghotel.exception.BadRequestException;
import com.example.bookinghotel.exception.NotFoundException;
import com.example.bookinghotel.model.request.LoginRequest;
import com.example.bookinghotel.model.request.RegisterRequest;
import com.example.bookinghotel.repository.RoleRepository;
import com.example.bookinghotel.repository.TokenConfirmRepository;
import com.example.bookinghotel.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final HttpSession httpSession;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenConfirmRepository tokenConfirmRepository;

    public AuthService(AuthenticationManager authenticationManager, HttpSession httpSession, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, TokenConfirmRepository tokenConfirmRepository) {
        this.authenticationManager = authenticationManager;
        this.httpSession = httpSession;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenConfirmRepository = tokenConfirmRepository;
    }

    public String login(LoginRequest request) {
        // Tạo đối tượng xác thực
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );

        try {
            // Tiến hành xác thực
            Authentication authentication = authenticationManager.authenticate(token);

            // Lưu đối tượng đã xác thực vào trong SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Lưu vào trong session
            httpSession.setAttribute("MY_SESSION", authentication.getName()); // Lưu email -> session

            return "Đăng nhập thành công";
        } catch (Exception e) {
            log.info("Lỗi đăng nhập: " + e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    public String register(RegisterRequest request) {
        //check email exist
        if(userRepository.existsByEmail(request.getEmail())){
            throw new BadRequestException("Email already exist");
        }
        //get role user
        Role roleUser = roleRepository.findByName("USER")
                .orElseThrow(()-> new NotFoundException("Not found role"));
        //create user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(false);
        user.setRoles(List.of(roleUser));
        userRepository.save(user);

        //create token
        TokenConfirm tokenConfirm = new TokenConfirm();
        tokenConfirm.setToken(UUID.randomUUID().toString());
        tokenConfirm.setCreatedAt(LocalDateTime.now());
        tokenConfirm.setExpiredAt(LocalDateTime.now().plusMinutes(20));
        tokenConfirm.setUser(user);
        tokenConfirmRepository.save(tokenConfirm);

        return "http://localhost:8080/confirm?token=" + tokenConfirm.getToken();
    }
    // isValid : true/false, message : thông báo lỗi
    public Map<String, Object> confirm(String token) {
        Map<String, Object> data = new HashMap<>();
        // Token có tồn tại không
        Optional<TokenConfirm> tokenConfirmOptional = tokenConfirmRepository.findByToken(token);
        if (tokenConfirmOptional.isEmpty()) {
            data.put("isValid", false);
            data.put("message", "Token không tồn tại");
            return data;
        }

        TokenConfirm tokenConfirm = tokenConfirmOptional.get();
        // Token đã được xác nhận chưa (nếu trường confirmedAt = null là chưa xác nhận)
        if (tokenConfirm.getConfirmedAt() != null) {
            data.put("isValid", false);
            data.put("message", "Token đã được xác nhận");
            return data;
        }

        // Token đã hết hạn hay chưa
        if (tokenConfirm.getExpiredAt().isBefore(LocalDateTime.now())) {
            data.put("isValid", false);
            data.put("message", "Token đã hết hạn");
            return data;
        }

        // set lại trường confirmedAt = thời điểm thực hiện
        tokenConfirm.setConfirmedAt(LocalDateTime.now());

        // set lại trường isEnabled của user = true
        tokenConfirm.getUser().setStatus(true);

        // lưu lại tokenConfirm và user
        tokenConfirmRepository.save(tokenConfirm);
        userRepository.save(tokenConfirm.getUser());

        data.put("isValid", true);
        data.put("message", "Xác nhận thành công");
        return data;
    }
}