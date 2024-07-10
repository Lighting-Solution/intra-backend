package com.ls.in.document.repository;

import com.ls.in.document.domain.model.DocumentBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentBoxRepository extends JpaRepository<DocumentBox, Integer> {
}
