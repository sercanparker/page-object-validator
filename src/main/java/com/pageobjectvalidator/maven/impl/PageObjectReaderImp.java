package com.pageobjectvalidator.maven.impl;

import com.pageobjectvalidator.maven.interfaces.PageObjectReader;
import com.pageobjectvalidator.maven.model.PageObject;
import com.pageobjectvalidator.maven.model.PageObjectClass;
import com.pageobjectvalidator.maven.model.PageObjectInterface;
import com.pageobjectvalidator.maven.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sercansensulun on 13.12.2020.
 */
public class PageObjectReaderImp implements PageObjectReader {

    /**
     * Absolute path of class folder
     */
    private String classesAbsolutePath;

    /**
     * Absolute path of interface folder
     */
    private String interfaceAbsolutePath;

    public PageObjectReaderImp(String classesAbsolutePath, String interfaceAbsolutePath) {
        this.classesAbsolutePath = classesAbsolutePath;
        this.interfaceAbsolutePath = interfaceAbsolutePath;
    }

    public String getClassesAbsolutePath() {
        return classesAbsolutePath;
    }

    public String getInterfaceAbsolutePath() {
        return interfaceAbsolutePath;
    }


    /**
     * Returns list of Page Object Class objects from absolute path.
     * @see PageObject
     * @return
     */
    @Override
    public List<PageObjectClass> getClasses() {
        return null;
    }

    /**
     * Returns list of Page Object Interface objects.
     * @see PageObjectInterface
     * @return
     */
    @Override
    public List<PageObjectInterface> getInterfaces() {
        List<PageObjectInterface> interfaceList = new ArrayList<>();
        List<StringBuilder> candidateInterfaceContents = FileUtil.getJavaContents(interfaceAbsolutePath);
        for (StringBuilder candidateContent :
                candidateInterfaceContents) {
            String candidateContentAsString = candidateContent.toString();
            String interfaceKeyword = "interface ";
            int indexOfInterfaceKeyword = candidateContentAsString.indexOf(interfaceKeyword);
            int indexOfInterfaceOpenCurlyBrace = candidateContentAsString.indexOf('{');
            if (indexOfInterfaceKeyword < 0 || indexOfInterfaceOpenCurlyBrace < 0){
                return interfaceList;
            }
            String interfaceName = candidateContentAsString.substring(indexOfInterfaceKeyword+interfaceKeyword.length(), indexOfInterfaceOpenCurlyBrace);
            interfaceName = interfaceName.replace(" ","");
            interfaceList.add(new PageObjectInterface(interfaceName));
        }
        return interfaceList;
    }
}
