package com.supermarket.pos.repository;

import com.supermarket.pos.entity.PasswordRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PasswordRequestRepository extends JpaRepository<PasswordRequest, Long> {

    List<PasswordRequest> findByUsername(String username);

}