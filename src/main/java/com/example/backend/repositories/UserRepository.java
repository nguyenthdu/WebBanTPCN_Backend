package com.example.backend.repositories;

import com.example.backend.entities.User_;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/*
* TODO:giao diện mở rộng từ JpaRepository, một phần của Spring Data JPA, được sử dụng để tương tác với cơ sở dữ liệu. Nó cung cấp các phương thức tiêu chuẩn như tìm kiếm, thêm, sửa và xóa.
* */
@Repository
public interface UserRepository extends JpaRepository<User_, Long> {

    Optional<User_> findByUsername(String username);
}
