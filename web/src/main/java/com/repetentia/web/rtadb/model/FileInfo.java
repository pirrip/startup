package com.repetentia.web.rtadb.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "FILE_INFO")
public class FileInfo {
    @Id
    @Column(name = "SEQ")
    private Long seq;
    @Column(name = "FILENAME")
    private String filename;
    @Column(name = "ORIGINAL_FILENAME")
    private String originalFilename;
    @Column(name = "FILE_TYPE")
    private String fileType;
    @Column(name = "MIME_TYPE")
    private String mimeType;
    @Column(name = "TIKA_TYPE")
    private String tikaType;
    @Column(name = "IS_USE")
    private boolean use;
    @Column(name = "CRDT")
    private LocalDateTime crdt;
}
