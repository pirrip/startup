package com.repetentia.web.startup.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.repetentia.web.rtadb.model.FileInfo;
import com.repetentia.web.rtadb.model.FileMeta;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileUploadService {
	public FileMeta detect(InputStream content) {
		Parser parser = new AutoDetectParser();
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		try {
			parser.parse(content, handler, metadata, new ParseContext());
		} catch (Exception e) {
			log.info(
					"# Your document contained more than 100000 characters, and so your requested limit has been reached");
		}

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
		return fileMeta;
	}

	public void upload(List<MultipartFile> files) {
		for (MultipartFile multipartFile : files) {
			FileInfo fileInfo = new FileInfo();
			String contentType = multipartFile.getContentType();
			String originalFilename = multipartFile.getOriginalFilename();
			int extIdx = originalFilename.lastIndexOf(".");
			String fileType = originalFilename.substring(extIdx + 1);
			log.info("# originalFilename [{}]", originalFilename);
//            fileInfo.setFilename(filename);
			fileInfo.setOriginalFilename(originalFilename);
			fileInfo.setFileType(fileType);
			fileInfo.setMimeType(contentType);
			fileInfo.setUse(true);
			fileInfo.setCrdt(LocalDateTime.now());

			try (InputStream is = multipartFile.getInputStream()) {
				String type = new Tika().detect(is);
				log.info("# {}", type);
				FileMeta fileMeta = detect(is);
				fileInfo.setTikaType(fileMeta.getFormat());
				log.info("# META - {}", fileMeta);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		String originalFilename = "Vue.js 2 cookbook_ build modern, interactive web applications with Vue.js ( PDFDrive.com ).pdf";
		int extIdx = "Vue.js 2 cookbook_ build modern, interactive web applications with Vue.js ( PDFDrive.com ).pdf"
				.lastIndexOf(".");
		String fileType = originalFilename.substring(extIdx + 1);
		System.out.println(fileType);
	}
}
