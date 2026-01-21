package com.cinema.stream.repository;

import com.cinema.stream.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DirectorRepository extends JpaRepository<Director, Long> {
}
