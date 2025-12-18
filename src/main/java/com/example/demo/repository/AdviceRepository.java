package com.example.demo.repository;



import com.example.demo.model.Advice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface AdviceRepository extends JpaRepository<Advice, Long> {
    List<Advice> findByLandDbId(Long landDbId);
}
