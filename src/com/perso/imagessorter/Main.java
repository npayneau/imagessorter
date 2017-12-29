package com.perso.imagessorter;

import com.drew.metadata.Metadata;
import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;
import com.drew.imaging.ImageMetadataReader;

public class Main {

    public static void main(String[] args) throws IOException {
        DateFormat f = new SimpleDateFormat("dd-MM-yyyy");

        try (Stream<Path> paths = Files.walk(Paths.get(args[0]))) {
                paths.filter(Files::isRegularFile).forEach(path -> {
                    BasicFileAttributes attr = null;

                    try {
                        Metadata metadata = ImageMetadataReader.readMetadata(path.toFile());
                        attr = Files.readAttributes(path, BasicFileAttributes.class);
                        File dir = new File(args[0]+"\\"+f.format(new Date(attr.creationTime().toMillis())));
                        FileUtils.moveFileToDirectory(path.toFile(),dir,true);

                        /*System.out.println("---------------------------------------------------");
                        System.out.println("creationTime: " + attr.creationTime().toMillis());
                        System.out.println("lastAccessTime: " + attr.lastAccessTime());
                        System.out.println("lastModifiedTime: " + attr.lastModifiedTime());

                        System.out.println("isDirectory: " + attr.isDirectory());
                        System.out.println("isOther: " + attr.isOther());
                        System.out.println("isRegularFile: " + attr.isRegularFile());
                        System.out.println("isSymbolicLink: " + attr.isSymbolicLink());
                        System.out.println("size: " + attr.size());*/
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        }

    }
}