package com.example.backend.repositories;

import com.example.backend.entities.OrderDetail;
import com.example.backend.entities.Order_;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
	@Query("select o from Order_ o where o.user.id = ?1")
	List<Order_> findAllbyUserId(Long customerId);
}
