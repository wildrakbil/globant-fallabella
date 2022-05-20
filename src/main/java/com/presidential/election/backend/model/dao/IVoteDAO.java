package com.presidential.election.backend.model.dao;

import com.presidential.election.backend.model.entity.User;
import com.presidential.election.backend.model.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVoteDAO extends JpaRepository<Vote, Long> {

    Vote findByIdUser(User id);
}
