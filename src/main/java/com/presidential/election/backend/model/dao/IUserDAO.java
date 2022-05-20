package com.presidential.election.backend.model.dao;

import com.presidential.election.backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDAO extends JpaRepository<User, Long> {
}
