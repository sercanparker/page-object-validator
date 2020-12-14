package com.pageobjectvalidator.maven.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author sercansensulun on 13.12.2020.
 */
public class FileUtil {


    /**
     * Return content of given file as StringBuilder
     * @param file
     * @return
     */
    public static StringBuilder getContent(File file){
        StringBuilder content = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()){
                content.append(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * Returns content of all java files which are located under given absolute folder path.
     * @param absoluteFolderPath
     * @return
     */
    public static List<StringBuilder> getJavaContents(String absoluteFolderPath){
        List<StringBuilder> contentList = new ArrayList<>();
        List<File> javaFileList = getJavaFiles(absoluteFolderPath);
        for (File javaFile:
             javaFileList) {
            StringBuilder javaFileContent = getContent(javaFile);
            contentList.add(javaFileContent);
        }
        return contentList;
    }

    /**
     * Return List of File objects from given absolute path.
     * @param absoluteFolderPath
     * @return
     */
    public static List<File> getJavaFiles(String absoluteFolderPath){
        try {
            File javaFolder = new File(absoluteFolderPath);
            FilenameFilter filenameFilter = (file, name) -> name.endsWith(".java");
            File[] javaFiles = javaFolder.listFiles(filenameFilter);
            if (javaFiles != null){
                return Arrays.asList(javaFiles);
            }
            return new ArrayList<>();
        }
        catch (Exception e){
            throw e;
        }
    }

}
