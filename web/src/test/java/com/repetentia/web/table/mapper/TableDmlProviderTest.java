package com.repetentia.web.table.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.repetentia.component.table.TableDmlProvider;
import com.repetentia.web.rtadb.model.FileInfo;
import com.repetentia.web.rtadb.model.FileMeta;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TableDmlProviderTest {
    
    @Test
    @DisplayName("TABLE INSERT TEST")
    public void insert() {
        TableDmlProvider sp = new TableDmlProvider();
        FileInfo fileInfo = new FileInfo();
        String sql = sp.insert(fileInfo);
        log.info(".insert - SQL : \r\n{}", sql);
    }

    @Test
    public void update() {
        TableDmlProvider sp = new TableDmlProvider();
        FileMeta fileMeta = new FileMeta();
        String sql = sp.update(fileMeta);
        log.info(".update - SQL : \r\n{}", sql);
    }

    @Test
    public void delete() {
        TableDmlProvider sp = new TableDmlProvider();
        FileMeta fileMeta = new FileMeta();
        String sql = sp.delete(fileMeta);
        log.info(".delete - SQL : \r\n{}", sql);
    }

    @Test
    public void find() {
        TableDmlProvider sp = new TableDmlProvider();
        FileMeta fileMeta = new FileMeta();
        String sql = sp.find(fileMeta);
        log.info(".find - SQL : \r\n{}", sql);
    }
    
    @Test
    public void findAll() {
        TableDmlProvider sp = new TableDmlProvider();
        FileMeta fileMeta = new FileMeta();
        fileMeta.setKeywords("yours only");
        String sql = sp.findAll(fileMeta);
        log.info(".findAll - SQL : \r\n{}", sql);
    }
}
