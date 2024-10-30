package com.example.ToYokoNa.service;

import com.example.ToYokoNa.controller.form.DepartmentForm;
import com.example.ToYokoNa.repository.DepartmentRepository;
import com.example.ToYokoNa.repository.entity.Branch;
import com.example.ToYokoNa.repository.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    public List<DepartmentForm> findAllDepartments() {
        List<Department> results = departmentRepository.findAll();
        List<DepartmentForm> departments = setDepartmentForm(results);
        return departments;
    }

    private List<DepartmentForm> setDepartmentForm(List<Department> results) {
        List<DepartmentForm> departments = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            DepartmentForm department = new DepartmentForm();
            Department result = results.get(i);
            department.setId(result.getId());
            department.setName(result.getName());
            departments.add(department);
        }
        return departments;
    }
}
