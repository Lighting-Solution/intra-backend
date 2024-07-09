package com.ls.in.document.repository;

import com.ls.in.document.domain.model.DocumentBox;
import com.ls.in.document.util.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentBoxRepository extends JpaRepository<DocumentBox, Integer> {
	Page<DocumentBox> findByCategory(Category category, Pageable page);
}
