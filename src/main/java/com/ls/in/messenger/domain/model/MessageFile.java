package com.ls.in.messenger.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messageFile")
public class MessageFile {

    @Id
    @Column(name = "file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer messageFileId;

    @Column(name = "name")
    private String fileName;

    @Column(name = "path")
    private String filePath;

    @OneToOne
    @JoinColumn(name = "message_id")
    private Message message;
}
