package com.ls.in.document.util;

import com.ls.in.document.domain.model.DocumentBox;
import com.ls.in.document.dto.DocumentDTO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DocumentSpecification {

	public static Specification<DocumentBox> getDocuments(DocumentDTO searchDTO) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			// Category 조건
			if (searchDTO.getCategoryName() != null && !searchDTO.getCategoryName().isEmpty()) {
				predicates.add(criteriaBuilder.equal(root.get("category"), searchDTO.getCategoryName()));
			}

			// Search Term 조건
			if (searchDTO.getSearchTerm() != null && !searchDTO.getSearchTerm().isEmpty()) {
				if ("title".equalsIgnoreCase(searchDTO.getSearchType())) {
					predicates.add(criteriaBuilder.like(root.get("documentTitle"), "%" + searchDTO.getSearchTerm() + "%"));
				} else if ("writer".equalsIgnoreCase(searchDTO.getSearchType())) {
					predicates.add(criteriaBuilder.like(root.get("emp").get("empName"), "%" + searchDTO.getSearchTerm() + "%"));
				}
			}

			// Date Range 조건
			if ("date".equalsIgnoreCase(searchDTO.getSearchType())) {
				LocalDate startDate = searchDTO.getStartDate();
				LocalDate endDate = searchDTO.getEndDate();
				predicates.add(criteriaBuilder.between(root.get("documentCreatedAt"), startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay()));
			}

			query.orderBy(criteriaBuilder.desc(root.get("documentCreatedAt")));

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
