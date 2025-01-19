package com.example.nbe341team02.admin.repository;

import com.example.nbe341team02.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Query("SELECT a FROM Admin a WHERE a.adminUsername = :adminUsername")
    Optional<Admin> findByAdminUsername(@Param("adminUsername") String adminUsername);
}
