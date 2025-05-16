package com.example.pc1.user.infrastructure;

import com.example.pc1.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseUserRepository<T extends User> extends JpaRepository<T, Long> {
}
