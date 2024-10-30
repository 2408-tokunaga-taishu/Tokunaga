package com.example.ToYokoNa.repository;

import com.example.ToYokoNa.repository.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}