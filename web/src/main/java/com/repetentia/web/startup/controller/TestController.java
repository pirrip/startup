package com.repetentia.web.startup.controller;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.repetentia.web.startup.service.FileUploadService;
import com.repetentia.web.startup.service.TestService;

import liquibase.CatalogAndSchema;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.DiffToChangeLog;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.serializer.core.formattedsql.FormattedSqlChangeLogSerializer;
import liquibase.serializer.core.string.StringChangeLogSerializer;
import liquibase.serializer.core.xml.XMLChangeLogSerializer;
import liquibase.serializer.core.yaml.YamlChangeLogSerializer;
import liquibase.structure.core.Column;
import liquibase.structure.core.Data;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Sequence;
import liquibase.structure.core.Table;
import liquibase.structure.core.UniqueConstraint;
import liquibase.structure.core.View;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {
    private String folder = "D:/rta/folder";
    @Autowired
    TestService testService;
    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    DataSource dataSource;
    @GetMapping("/test")
	public ModelAndView test(ModelAndView mav, HttpServletRequest request) {
	    String id = request.getSession().getId();
	    log.info("# test session id  - {}", id);
	    log.info("# log  - {}, {}", log.getClass(), log.getName());
	    
	    mav.setViewName("test");
		return mav;
	}

//    @RequestMapping(value="/upload")
//	public void upload() {
//	    log.info("# upload");
//	}
    @RequestMapping("/upload")
	public void upload(List<MultipartFile> files) {
	    log.info("# file : {}", files);
	    fileUploadService.upload(files);
	    log.info("# change : {}", files);
	}


    @SuppressWarnings("unchecked")
	@GetMapping("/lb")
	public String lb() throws Exception {
    	Connection connection = dataSource.getConnection();
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new Liquibase("classpath:config/liquibase/master.xml", new ClassLoaderResourceAccessor(), database);

        CatalogAndSchema catalogAndSchema = new CatalogAndSchema(null, "rtadb");
        DiffToChangeLog changeLog = new DiffToChangeLog(new DiffOutputControl(false, false, true, null));

        liquibase.generateChangeLog(catalogAndSchema, changeLog, new PrintStream(new FileOutputStream("d:/change-logs.xml")), new XMLChangeLogSerializer(), snapTypes());
        liquibase.close();
		return "OK";
	}

    @SuppressWarnings("rawtypes")
	private static Class[] snapTypes() {
        return new Class[]{UniqueConstraint.class, Sequence.class, Table.class, View.class, ForeignKey.class, PrimaryKey.class, Index.class, Column.class};
    }
}

