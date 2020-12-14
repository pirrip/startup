package com.repetentia.component.property;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "property_source")
public class DatabaseProperty {
    @Id
    @Column(name = "pid")
    private int pid;
    @Column(name = "site")
    private String site;
    @Column(name = "profile")
    private String profile;
    @Column(name = "property_key")
    private String propertyKey;
    @Column(name = "se")
    private String se;
    @Column(name = "property_name")
    private String propertyName;
    @Column(name = "property_value")
    private String propertyValue;
}