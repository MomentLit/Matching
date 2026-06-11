package com.example.matching.repository;

import com.example.matching.entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {

    List<Matching> findByRequesterIdOrderByCreatedAtDesc(String requesterId);
}
