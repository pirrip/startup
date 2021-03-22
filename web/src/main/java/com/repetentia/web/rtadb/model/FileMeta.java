package com.repetentia.web.rtadb.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "file_meta")
public class FileMeta {
    @Id
    @Column(name = "filename")
    private String filename;
    @Column(name = "format")
    private String format;
    @Column(name = "identifier")
    private String identifier;
    @Column(name = "contributor")
    private String contributor;
    @Column(name = "coverage")
    private String coverage;
    @Column(name = "creator")
    private String creator;
    @Column(name = "modifier")
    private String modifier;
    @Column(name = "creator_tool")
    private String creatorTool;
    @Column(name = "language")
    private String language;
    @Column(name = "publisher")
    private String publisher;
    @Column(name = "relation")
    private String relation;
    @Column(name = "rights")
    private String rights;
    @Column(name = "source")
    private String source;
    @Column(name = "type")
    private String type;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "keywords")
    private String keywords;
    @Column(name = "created")
    private String created;
    @Column(name = "modified")
    private String modified;
    @Column(name = "print_date")
    private String printDate;
    @Column(name = "metadata_date")
    private String metadataDate;
    @Column(name = "latitude")
    private String latitude;
    @Column(name = "longitude")
    private String longitude;
    @Column(name = "altitude")
    private String altitude;
    @Column(name = "rating")
    private String rating;
    @Column(name = "comments")
    private String comments;
}
