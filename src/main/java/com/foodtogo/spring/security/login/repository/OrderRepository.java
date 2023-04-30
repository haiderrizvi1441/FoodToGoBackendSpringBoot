package com.foodtogo.spring.security.login.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodtogo.spring.security.login.models.Orders;

@Transactional
public interface OrderRepository extends JpaRepository<Orders, Long> {
	Orders findById(long id);

}
