package com.upao.pe.coderlink.repos;

import com.upao.pe.coderlink.models.Postulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostulationRepository extends JpaRepository<Postulation, Long> {
}
