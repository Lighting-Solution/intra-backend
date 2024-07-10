package com.ls.in.document.repository;

import com.ls.in.document.domain.model.DocumentBox;
import com.ls.in.document.util.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentBoxRepository extends JpaRepository<DocumentBox, Integer>, JpaSpecificationExecutor<DocumentBox> {
	Page<DocumentBox> findByCategory(Category category, Pageable page);
}
