package com.java.backend.service.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.backend.entity.Department;
import com.java.backend.repository.DepartmentDao;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public Department addDepartment(Department department) {
        return departmentDao.save(department);
    }
}
