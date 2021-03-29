package com.repetentia.component.liquibase;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.springframework.core.env.Environment;

import liquibase.CatalogAndSchema;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.DiffToChangeLog;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.serializer.core.xml.XMLChangeLogSerializer;
import liquibase.structure.core.Column;
import liquibase.structure.core.Data;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Sequence;
import liquibase.structure.core.Table;
import liquibase.structure.core.UniqueConstraint;
import liquibase.structure.core.View;

public class LiquiBaseChangeLogGenerator {
    private final Logger log = org.slf4j.LoggerFactory.getLogger(getClass());

    private DataSource dataSource;
    private String pathMaster;
    private String pathChangelog;
    private String catalog;
    private String schema;


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public LiquiBaseChangeLogGenerator(Environment env) {
        // pathMaster - classpath:config/liquibase/master.xml
        // pathChangelog - d:/change-logs.xml
        // schema - rtadb
        this.pathMaster = env.getProperty("liquibase.path.master");
        this.pathChangelog = env.getProperty("liquibase.path.changelog");
        this.catalog = env.getProperty("liquibase.catalog");
        this.schema = env.getProperty("liquibase.schema");
        log.info("# liquibase master file path : {}", pathMaster);
        log.info("# liquibase generated change-log path : {}", pathChangelog);
        log.info("# liquibase catalog : {} & schema : {}", catalog, schema);
    }

    @SuppressWarnings("unchecked")
    public boolean generateChangeLog(String type) throws Exception {
        Connection connection = dataSource.getConnection();
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new Liquibase(pathMaster, new ClassLoaderResourceAccessor(), database);

        CatalogAndSchema catalogAndSchema = new CatalogAndSchema(catalog, schema);
        DiffToChangeLog changeLog = new DiffToChangeLog(new DiffOutputControl(false, false, true, null));


        liquibase.generateChangeLog(catalogAndSchema, changeLog, new PrintStream(new FileOutputStream(pathChangelog)), new XMLChangeLogSerializer(), snapTypes(type));
        liquibase.close();
        return true;
    }

    @SuppressWarnings("rawtypes")
    private static Class[] snapTypes(String type) {
        if ("data".equals(type)) {
            return dataTypes();
        } else {
            return schemaTypes();
        }

    }

    @SuppressWarnings("rawtypes")
    private static Class[] schemaTypes() {
        return new Class[] { UniqueConstraint.class, Sequence.class, Table.class, View.class, ForeignKey.class, PrimaryKey.class, Index.class, Column.class };
    }

    @SuppressWarnings("rawtypes")
    private static Class[] dataTypes() {
        return new Class[] { Data.class };
    }
}
