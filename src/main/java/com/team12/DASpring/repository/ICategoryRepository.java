package com.team12.DASpring.repository;

import com.team12.DASpring.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ICategoryRepository extends JpaRepository<Category,Long> {
}
