package com.repetentia.web.startup.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.repetentia.web.rtadb.model.FileMeta;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileUploadService {
    
    public void detect(InputStream content) {
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        try {
            parser.parse(content, handler, metadata, new ParseContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

//        String contentType = metadata.get(Metadata.CONTENT_TYPE);
        FileMeta fileMeta = new FileMeta();
        String format = metadata.get(TikaCoreProperties.FORMAT);
        String identifier = metadata.get(TikaCoreProperties.IDENTIFIER);
        String contributor = metadata.get(TikaCoreProperties.CONTRIBUTOR);
        String coverage = metadata.get(TikaCoreProperties.COVERAGE);
        String creator = metadata.get(TikaCoreProperties.CREATOR);
        String modifier = metadata.get(TikaCoreProperties.MODIFIER);
        String creatorTool = metadata.get(TikaCoreProperties.CREATOR_TOOL);
        String language = metadata.get(TikaCoreProperties.LANGUAGE);
        String publisher = metadata.get(TikaCoreProperties.PUBLISHER);
        String relation = metadata.get(TikaCoreProperties.RELATION);
        String rights = metadata.get(TikaCoreProperties.RIGHTS);
        String source = metadata.get(TikaCoreProperties.SOURCE);
        String type = metadata.get(TikaCoreProperties.TYPE);
        String title = metadata.get(TikaCoreProperties.TITLE);
        String description = metadata.get(TikaCoreProperties.DESCRIPTION);
        String keywords = metadata.get(TikaCoreProperties.KEYWORDS);
        String created = metadata.get(TikaCoreProperties.CREATED);
        String modified = metadata.get(TikaCoreProperties.MODIFIED);
        String printDate = metadata.get(TikaCoreProperties.PRINT_DATE);
        String metadataDate = metadata.get(TikaCoreProperties.METADATA_DATE);
        String latitude = metadata.get(TikaCoreProperties.LATITUDE);
        String longitude = metadata.get(TikaCoreProperties.LONGITUDE);
        String altitude = metadata.get(TikaCoreProperties.ALTITUDE);
        String rating = metadata.get(TikaCoreProperties.RATING);
        String comments = metadata.get(TikaCoreProperties.COMMENTS);
        fileMeta.setFormat(format);
        fileMeta.setIdentifier(identifier);
        fileMeta.setContributor(contributor);
        fileMeta.setCoverage(coverage);
        fileMeta.setCreator(creator);
        fileMeta.setModifier(modifier);
        fileMeta.setCreatorTool(creatorTool);
        fileMeta.setLanguage(language);
        fileMeta.setPublisher(publisher);
        fileMeta.setRelation(relation);
        fileMeta.setRights(rights);
        fileMeta.setSource(source);
        fileMeta.setType(type);
        fileMeta.setTitle(title);
        fileMeta.setDescription(description);
        fileMeta.setKeywords(keywords);
        fileMeta.setCreated(created);
        fileMeta.setModified(modified);
        fileMeta.setPrintDate(printDate);
        fileMeta.setMetadataDate(metadataDate);
        fileMeta.setLatitude(latitude);
        fileMeta.setLongitude(longitude);
        fileMeta.setAltitude(altitude);
        fileMeta.setRating(rating);
        fileMeta.setComments(comments);
        log.info("# META - {}", fileMeta);
    }
    public void upload(List<MultipartFile> files) {
        TikaConfig tika = null;
        try {
            tika = new TikaConfig();
        } catch (TikaException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        for (MultipartFile multipartFile : files) {
            String contentType = multipartFile.getContentType();
            String originalFilename = multipartFile.getOriginalFilename();
            String name = multipartFile.getName();

            log.info("multipart file default : {}, {}, {}", contentType, originalFilename, name);

            try {
                InputStream is = multipartFile.getInputStream();
                detect(is);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
}
