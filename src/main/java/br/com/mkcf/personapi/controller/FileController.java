package br.com.mkcf.personapi.controller;

import br.com.mkcf.personapi.data.vo.UploadFileResponseVO;
import br.com.mkcf.personapi.services.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name  =  "File Endpoint")
@RestController
@RequestMapping("/api/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/uploadFile")
    public UploadFileResponseVO uploadFile(@RequestParam("file") MultipartFile file){

        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponseVO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }


    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponseVO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files){
        return Arrays.asList(files).stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadfile(@PathVariable String fileName, HttpServletRequest request){
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String contentType= null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (Exception e){
            logger.warn("Não conseguiu determina o tipo do aquivo!");
        }
        if(contentType == null){
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"" + resource.getFilename() + "\""))
                .body(resource);
    }
}
