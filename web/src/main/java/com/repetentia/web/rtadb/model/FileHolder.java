package com.repetentia.web.rtadb.model;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "FILE_HOLDER")
public class FileHolder {
    @Id
    @Column(name="SEQ")
    private Long seq;
    @Id
    @Column(name="FILE_SEQ")
    private Integer fileSeq;
    @Column(name="FILE")
    private File [] file;
}