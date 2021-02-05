package com.repetentia.web.rtadb.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "file_info")
public class FileInfo {
    @Id
    @Column(name = "filename")
    private String filename;
    @Column(name = "original_filename")
    private String originalFilename;
    @Column(name = "file_type")
    private String fileType;
    @Column(name = "mime_type")
    private String mimeType;
    @Column(name = "tika_type")
    private String tikaType;
    @Column(name = "is_use")
    private boolean use;
    @Column(name = "crdt")
    private LocalDateTime crdt;
}
