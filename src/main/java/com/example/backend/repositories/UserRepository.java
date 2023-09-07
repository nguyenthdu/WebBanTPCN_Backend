package com.example.backend.repositories;

import com.example.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/*
* TODO:giao diện mở rộng từ JpaRepository, một phần của Spring Data JPA, được sử dụng để tương tác với cơ sở dữ liệu. Nó cung cấp các phương thức tiêu chuẩn như tìm kiếm, thêm, sửa và xóa.
* */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
