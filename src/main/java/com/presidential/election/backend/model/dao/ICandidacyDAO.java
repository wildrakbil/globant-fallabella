package com.presidential.election.backend.model.dao;

import com.presidential.election.backend.model.entity.Candidacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICandidacyDAO extends JpaRepository<Candidacy, Long> {
}
