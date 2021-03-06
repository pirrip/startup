package com.repetentia.component.message;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "st_message_source")
public class DatabaseMessageSource {
    @Id
    @Column(name = "page")
    private String page;
    @Id
    @Column(name = "locale")
    private String locale;
    @Id
    @Column(name = "code")
    private String code;
    @Column(name = "message")
    private String message;
}