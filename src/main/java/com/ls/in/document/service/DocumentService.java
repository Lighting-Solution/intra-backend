package com.ls.in.document.service;

import com.ls.in.document.domain.model.DocumentBox;
import com.ls.in.document.dto.DocumentDTO;
import com.ls.in.document.dto.DocumentInitDTO;
import com.ls.in.document.dto.DocumentList;
import com.ls.in.document.repository.DocumentBoxRepository;
import com.ls.in.document.util.Category;
import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.repository.EmpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DocumentService {
	private final DocumentBoxRepository documentBoxRepository;
	private final EmpRepository empRepository;

	@Transactional
	public List<DocumentBox> getDocs(DocumentDTO documentDTO) {
		Emp loginEmp = empRepository.findById(documentDTO.getEmpId()).get();
		Category category = Category.fromCategoryName(documentDTO.getCategoryName());
		List<DocumentBox> docs = documentBoxRepository.findByCategory(category);
		// 1. public 2. approval 3. department(service, manage, solution)
		// PUBLIC
		if (category == Category.PUBLIC)
			return docs;
		// APPROVAL
		if (category == Category.APPROVAL)
			return getApprovalDocuments(loginEmp, docs);
		// myDepartment 문서
		if (documentDTO.getCategoryName().equalsIgnoreCase(loginEmp.getDepartment().getDepartmentName())) {
			return docs;
		}
		return new ArrayList<>();
	}

	private List<DocumentBox> getApprovalDocuments(Emp loginEmp, List<DocumentBox> docs) {
		String positionName = loginEmp.getPosition().getPositionName();

		if (positionName.equals("이사")) {
			return docs;
		}

		Stream<DocumentBox> filteredStream = docs.stream();

		if (positionName.equals("부장")) {
			filteredStream = filteredStream.filter(doc ->
					doc.getEmp().getDepartment().getDepartmentId().intValue() == loginEmp.getDepartment().getDepartmentId().intValue() ||
							doc.getEmp().getEmpId().intValue() == loginEmp.getEmpId().intValue()
			);
		} else {
			filteredStream = filteredStream.filter(doc -> doc.getEmp().getEmpId().intValue() == loginEmp.getEmpId().intValue());
		}

		return filteredStream.collect(Collectors.toList());
	}

	@Transactional
	public void saveDocument(DocumentInitDTO document) {
		Optional<Emp> myEmp = empRepository.findById(document.getWriterEmpId());
		DocumentBox doc = DocumentBox.createDocs(document, myEmp.get());
		documentBoxRepository.save(doc);
	}
}
