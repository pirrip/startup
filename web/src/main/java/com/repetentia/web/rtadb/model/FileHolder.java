package com.repetentia.web.rtadb.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "file_holder")
public class FileHolder {
    @Id
    @Column(name = "filename")
    private String filename;
    @Column(name="file")
    private byte[] file;
}