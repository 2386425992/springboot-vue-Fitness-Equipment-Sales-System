package com.niuniu.fitness_shop.repository;

import com.niuniu.fitness_shop.entity.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    Page<Log> findByOrderByCreatedAtDesc(Pageable pageable);
}
