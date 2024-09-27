package com.upao.pe.coderlink.repos;

import com.upao.pe.coderlink.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<List<Project>> findAllByNameContaining(String name);
}
