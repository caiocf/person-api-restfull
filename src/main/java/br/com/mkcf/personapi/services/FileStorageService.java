package br.com.mkcf.personapi.services;

import br.com.mkcf.personapi.config.FileStorageConfig;
import br.com.mkcf.personapi.exception.FileStorageException;
import br.com.mkcf.personapi.exception.MyFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception e) {
            throw new FileStorageException("Não conseguiu criar diretorio onde os arquivos de uploads serão armazenados", e);
        }
    }

    public String storeFile(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")){
                throw new FileStorageException(String.format("Desculpe! Filename contem caminho de path invalido  %s" , fileName));
            }
            // save in Disk ou DataBase
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        }catch (Exception e){
            throw new FileStorageException(String.format("Não conseguiu armazenados o arquivo %s. Por favor tente novamente. ", fileName), e);
        }
    }
    public Resource loadFileAsResource(String fileName){
        try {
            // Recovery file disk ou Database
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()){
                return resource;
            } else {
                throw new MyFileNotFoundException(String.format("Arquivo não encontrado %s ", fileName));
            }
        }catch (Exception e){
            throw new MyFileNotFoundException(String.format("Arquivo não encontrado %s ", fileName), e);
        }
    }
}
