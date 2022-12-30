package ru.martynovevgeniy.vetclinic;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;
import ru.martynovevgeniy.vetclinic.models.Animal;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InteractionPhoto {

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\photos";

    public static void checkMainDir() {
        File dir = new File(UPLOAD_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static void createDir(Long animalId) {
        String pathDir = UPLOAD_DIRECTORY + "\\" + animalId;
        File dirPhotos = new File(pathDir);
        dirPhotos.mkdirs();
    }

    public static void deleteDir(Long animalId) {
        String pathDir = UPLOAD_DIRECTORY + "\\" + animalId;
        File dirPhotos = new File(pathDir);
        File[] allContents = dirPhotos.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                file.delete();
            }
        }
        dirPhotos.delete();
    }

    public static String uploadImage(MultipartFile file, Long animalId) throws IOException {
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY + "\\" + animalId, file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        return file.getOriginalFilename();
    }

    public static String getPhoto(Animal animal) throws IOException {
        String pathFile = UPLOAD_DIRECTORY + "\\" + animal.getId() + "\\" + animal.getPath();
        return toBase64(pathFile);
    }

    public static void deletePhoto(Animal animal) {
        String pathFile = UPLOAD_DIRECTORY + "\\" + animal.getId() + "\\" + animal.getPath();
        File file = new File(pathFile);
        file.delete();
    }

    private static String toBase64(String path) throws IOException {
        File file = new File(path);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        Base64 base64 = new Base64();
        byte[] encodeBase64 = base64.encode(fileContent);
        return new String(encodeBase64, StandardCharsets.UTF_8);
    }
}
