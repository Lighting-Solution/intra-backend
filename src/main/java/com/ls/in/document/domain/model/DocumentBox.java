package com.ls.in.document.domain.model;

import com.ls.in.global.emp.domain.model.Emp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "documentBox")
public class DocumentBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Integer documentId;

    @Column(name = "title")
    private String documentTitle;

    @Column(name = "path")
    private String documentPath;

    @Column(name = "content")
    private String documentContent;

    @Column(name = "createdAt")
    private LocalDateTime documentCreatedAt;

    @Column(name = "updatedAt")
    private LocalDateTime documentUpdatedAt;

    @Column(name = "category")
    private String documentCategory;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Emp emp;
}
