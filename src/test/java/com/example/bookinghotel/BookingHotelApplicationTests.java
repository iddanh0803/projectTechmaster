package com.example.bookinghotel;

import com.example.bookinghotel.entity.*;
import com.example.bookinghotel.repository.*;
import com.github.slugify.Slugify;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Random;

@SpringBootTest
class BookingHotelApplicationTests {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private RoomRepository roomRepository;


    @Test
    void save_roles() {
        List<Role> roles = List.of(
                new Role(null, "ADMIN"),
                new Role(null, "USER")
        );

        roleRepository.saveAll(roles);
    }

    @Test
    void save_users() {
        Role userRole = roleRepository.findByName("USER").orElse(null);
        Role adminRole = roleRepository.findByName("ADMIN").orElse(null);

        List<User> users = List.of(
                new User(null, "Duy Anh",passwordEncoder.encode("123"),"Nguyen Duy Anh","Ha Noi", "1111", "duyanh@gmail.com","","",1,"Viet Nam", true,  List.of(adminRole)),
                new User(null, "Van Hung", passwordEncoder.encode("123"),"Mai Van Hung","Ninh Binh","1112", "hung@gmail.com","","",1,"Viet Nam",true,  List.of(userRole))

        );
        userRepository.saveAll(users);
    }
    @Test
    void save_blogs() {
        Slugify slugify = Slugify.builder().build();
        Random rd = new Random();

        List<User> userList = userRepository.findByRoles_NameIn(List.of("ADMIN", "AUTHOR"));
        for (int i = 0; i < 10; i++) {
            User rdUser = userList.get(rd.nextInt(userList.size()));
            // Tao blog
            Blog blog = Blog.builder()
                    .title("Blog " + (i + 1))
                    .slug(slugify.slugify("Blog " + (i + 1)))
                    .description("description " + (i + 1))
                    .content("content " + (i + 1))
                    .thumbnail("thumbnail" + (i+1))
                    .status(rd.nextInt(2) == 0)
                    .user(rdUser)
                    .build();

            blogRepository.save(blog);
        }
    }
    @Test
    void save_comments() {
        Random rd = new Random();
        List<User> userList = userRepository.findAll();
        List<Blog> blogList = blogRepository.findAll();

        for (int i = 0; i < 100; i++) {
            // Random 1 user
            User rdUser = userList.get(rd.nextInt(userList.size()));

            // Random 1 blog
            Blog rdBlog = blogList.get(rd.nextInt(blogList.size()));

            // Tao comment
            Comment comment = Comment.builder()
                    .content("comment " + (i + 1))
                    .user(rdUser)
                    .blog(rdBlog)
                    .build();

            commentRepository.save(comment);
        }
    }
    @Test
    void save_roomType(){
        for (int i = 0; i < 3; i++) {
            RoomType roomType = RoomType.builder()
                    .name((i+1) + " Star" )
                    .description("description " + (i+1))
                    .amount(i+3)
                    .build();
            roomTypeRepository.save(roomType);
        }
    }
    @Test
    void save_rooms(){
        RoomType standard = roomTypeRepository.findByName("1Star").orElse(null);
        RoomType deluxe = roomTypeRepository.findByName("2Star").orElse(null);
        RoomType suit = roomTypeRepository.findByName("3Star").orElse(null);
        List<Room> roomList= List.of(
                new Room(null,"P101","01",standard," floor 1","",2,2,3000.0,"des 1", true),
                new Room(null,"P201","02",deluxe," floor 2","",2,4,6000.0,"des 2", true),
                new Room(null,"P301","03",suit," floor 3","",4,4,9000.0,"des 3", true)
        );
        roomRepository.saveAll(roomList);
    }
}
