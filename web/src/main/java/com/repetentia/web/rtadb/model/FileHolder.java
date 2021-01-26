package com.repetentia.web.rtadb.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "FILE_HOLDER")
public class FileHolder {
    @Id
    @Column(name = "FILENAME")
    private String filename;
    @Column(name="FILE")
    private byte[] file;
}