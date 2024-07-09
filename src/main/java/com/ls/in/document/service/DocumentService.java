package com.ls.in.document.service;

import com.ls.in.document.domain.model.DocumentBox;
import com.ls.in.document.dto.DocumentDTO;
import com.ls.in.document.dto.DocumentDetailDTO;
import com.ls.in.document.dto.DocumentInitDTO;
import com.ls.in.document.dto.DocumentList;
import com.ls.in.document.repository.DocumentBoxRepository;
import com.ls.in.document.util.Category;
import com.ls.in.global.emp.domain.model.DepartmentType;
import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.repository.EmpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DocumentService {
	private final DocumentBoxRepository documentBoxRepository;
	private final EmpRepository empRepository;

	@Transactional
	public Page<DocumentBox> getDocs(DocumentDTO documentDTO, Pageable pageable) {
		Emp loginEmp = empRepository.findById(documentDTO.getEmpId()).get();
		String loginEmpDepartMent = getEmpDepartment(loginEmp);
		Category category = Category.fromCategoryName(documentDTO.getCategoryName());
		Page<DocumentBox> docs = documentBoxRepository.findByCategory(category, pageable);
		// PUBLIC
		if (category == Category.PUBLIC)
			return docs;
		// APPROVAL
		if (category == Category.APPROVAL)
			return getApprovalDocuments(loginEmp, docs, pageable);
		// myDepartment 문서. 부서별로 열람 가능. 이후 해당 코드로 로직 변경
		if (documentDTO.getCategoryName().equalsIgnoreCase(loginEmpDepartMent))
			return docs;
		// 내가 해당 부서에서 작성한 게시글
		List<DocumentBox> myDocs = docs.stream().filter(doc -> doc.getEmp().getEmpId().intValue() == loginEmp.getEmpId().intValue()).toList();
		return new PageImpl<>(myDocs, pageable, myDocs.size());
	}

	private String getEmpDepartment(Emp emp) {
		int id = emp.getDepartment().getDepartmentId();
		if (DepartmentType.SOLUTION_DEVELOPMENT_DIVISION.getID() <= id)
			return "solution";
		if (DepartmentType.MANAGEMENT_SUPPORT_DIVISION.getID() <= id)
			return "manage";
		if (DepartmentType.SERVICE_BUSINESS_DIVISION.getID() <= id)
			return "service";
		return "";
	}

	private Page<DocumentBox> getApprovalDocuments(Emp loginEmp, Page<DocumentBox> docs, Pageable pageable) {
		String positionName = loginEmp.getPosition().getPositionName();
		if (positionName.equals("대표이사")) {
			return docs;
		}
		Stream<DocumentBox> filteredStream = docs.stream();
		if (positionName.equals("부장")) {
			filteredStream = filteredStream.filter(doc ->
					Objects.equals(getEmpDepartment(doc.getEmp()), getEmpDepartment(loginEmp)) ||
							doc.getEmp().getEmpId().intValue() == loginEmp.getEmpId().intValue()
			);
		} else {
			filteredStream = filteredStream.filter(doc -> doc.getEmp().getEmpId().intValue() == loginEmp.getEmpId().intValue());
		}
		List<DocumentBox> resultDocs = filteredStream.collect(Collectors.toList());
		return new PageImpl<>(resultDocs, pageable, resultDocs.size());
	}

	@Transactional
	public void saveDocument(DocumentInitDTO document) {
		Optional<Emp> myEmp = empRepository.findById(document.getWriterEmpId());
		DocumentBox doc = DocumentBox.createDocs(document, myEmp.get());
		documentBoxRepository.save(doc);
	}

	@Transactional
	public DocumentBox getDocumentById(Integer id) {
		return documentBoxRepository.findById(id).get();
	}

	public DocumentList convertToDocumentList(DocumentBox documentBox) {
		DocumentList documentList = new DocumentList();
		documentList.setId(documentBox.getDocumentId());
		documentList.setTitle(documentBox.getDocumentTitle());
		documentList.setCategory(documentBox.getCategory().name());
		documentList.setEmpName(documentBox.getEmp().getEmpName());
		return documentList;
	}

	public DocumentDetailDTO convertToDocumentDetail(DocumentBox documentBox) {
		DocumentDetailDTO documentDetailDTO = new DocumentDetailDTO();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		documentDetailDTO.setDocumentId(documentBox.getDocumentId());
		documentDetailDTO.setWriterEmpId(documentBox.getEmp().getEmpId());
		documentDetailDTO.setTitle(documentBox.getDocumentTitle());
		documentDetailDTO.setCategory(documentBox.getCategory().name());
		documentDetailDTO.setWriterName(documentBox.getEmp().getEmpName());
		documentDetailDTO.setContent(documentBox.getDocumentContent());
		documentDetailDTO.setFilePath(documentBox.getDocumentPath()); // FILE
		documentDetailDTO.setCreatedAt(documentBox.getDocumentCreatedAt().format(formatter));
		documentDetailDTO.setUpdatedAt(documentBox.getDocumentUpdatedAt().format(formatter));
		return documentDetailDTO;
	}

	@Transactional
	public DocumentDetailDTO updateDocument(Integer documentId, String title, String content, String fileName) {
		DocumentBox documentBox = this.getDocumentById(documentId);
		documentBox.setDocumentTitle(title);
		documentBox.setDocumentContent(content);
		if (fileName != null)
			documentBox.setDocumentPath(fileName);
		return convertToDocumentDetail(documentBox);
	}
}
