package com.foodtogo.spring.security.login.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodtogo.spring.security.login.models.Food;

@Transactional
public interface FoodRepository extends JpaRepository< Food, Long> {
  Food findById(long id);
}
