package com.festnode.festnode.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.festnode.festnode.model.AppUser;
import com.festnode.festnode.model.Department;
import com.festnode.festnode.model.UserRole;
import com.festnode.festnode.repository.AppUserRepository;
import com.festnode.festnode.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    @Autowired
    private AppUserRepository userRepo;

    @Autowired
    private DepartmentRepository deptRepo;
    public Department createDepartment(JsonNode node) {
        Department dept = new Department();
        dept.setDepartmentName(node.get("deptName").asText());
        dept.setDepartmentDescription(node.get("deptDesc").asText());

        AppUser convener = userRepo.findById(node.get("userId").asLong())
                .orElseThrow(() -> new EntityNotFoundException("User Not Found!"));

        // Set role and validate
        convener.setRole(UserRole.CONVENER);
        convener.setDesignation(node.get("designation").asText());
        System.out.println("Convener before saving: " + convener);


        try {
            AppUser convenerUpdated = userRepo.save(convener);
            dept.setConvener(convenerUpdated);
            return deptRepo.save(dept);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save Convener: " + e.getMessage());
        }
    }

}
