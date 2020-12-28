package com.repetentia.web.rtadb.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "FILE_META")
public class FileMeta {
    @Id
    @Column(name = "FILENAME")
    private String filename;
    @Column(name="FORMAT")
    private String format;
    @Column(name="IDENTIFIER")
    private String identifier;
    @Column(name="CONTRIBUTOR")
    private String contributor;
    @Column(name="COVERAGE")
    private String coverage;
    @Column(name="CREATOR")
    private String creator;
    @Column(name="MODIFIER")
    private String modifier;
    @Column(name="CREATOR_TOOL")
    private String creatorTool;
    @Column(name="LANGUAGE")
    private String language;
    @Column(name="PUBLISHER")
    private String publisher;
    @Column(name="RELATION")
    private String relation;
    @Column(name="RIGHTS")
    private String rights;
    @Column(name="SOURCE")
    private String source;
    @Column(name="TYPE")
    private String type;
    @Column(name="TITLE")
    private String title;
    @Column(name="DESCRIPTION")
    private String description;
    @Column(name="KEYWORDS")
    private String keywords;
    @Column(name="CREATED")
    private String created;
    @Column(name="MODIFIED")
    private String modified;
    @Column(name="PRINT_DATE")
    private String printDate;
    @Column(name="METADATA_DATE")
    private String metadataDate;
    @Column(name="LATITUDE")
    private String latitude;
    @Column(name="LONGITUDE")
    private String longitude;
    @Column(name="ALTITUDE")
    private String altitude;
    @Column(name="RATING")
    private String rating;
    @Column(name="COMMENTS")
    private String comments;
}
