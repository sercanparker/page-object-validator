package com.pageobjectvalidator.maven.impl;

import com.pageobjectvalidator.maven.interfaces.PageObjectReader;
import com.pageobjectvalidator.maven.model.PageObject;
import com.pageobjectvalidator.maven.model.PageObjectClass;
import com.pageobjectvalidator.maven.model.PageObjectInterface;
import com.pageobjectvalidator.maven.utils.FileUtil;
import com.pageobjectvalidator.maven.utils.StringUtil;
import org.apache.maven.plugin.MojoExecutionException;

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

    public PageObjectReaderImp(String classesAbsolutePath, String interfaceAbsolutePath) throws MojoExecutionException {
        if (StringUtil.isNullOrEmpty(classesAbsolutePath)){
            throw new MojoExecutionException("Class path is empty. Please check your configuration tag.");
        }
        if (StringUtil.isNullOrEmpty(interfaceAbsolutePath)){
            throw new MojoExecutionException("Interface path is empty. Please check your configuration");
        }
        if (!FileUtil.isExisting(classesAbsolutePath)){
            throw new MojoExecutionException("Class path does not exist. Please check you configuration tag.");
        }
        if (!FileUtil.isExisting(interfaceAbsolutePath)){
            throw new MojoExecutionException("Interface path does not exist. Please check you configuration tag.");
        }
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
        List<PageObjectClass> classList = new ArrayList<>();
        List<StringBuilder> candidateClassContents = FileUtil.getJavaContents(classesAbsolutePath);
        for (StringBuilder candidateClassContent :
                candidateClassContents) {
            String candidateClassContentAsString = candidateClassContent.toString();
            String className, baseClassName = null, interfaceName = null;
            String classKeyword = " class ";
            String extendKeyword = " extends ";
            String implementsKeyword = " implements ";
            int indexOfClassKeyword = candidateClassContentAsString.indexOf(classKeyword);
            if (indexOfClassKeyword < 0){
                break;
            }
            int indexOfClassOpenCurlyBrace = candidateClassContentAsString.indexOf('{');
            if (indexOfClassOpenCurlyBrace < 0){
                break;
            }
            int indexOfExtendKeyword = candidateClassContentAsString.indexOf(extendKeyword);
            int indexOfImplementsKeyword = candidateClassContentAsString.indexOf(implementsKeyword);
            if (indexOfExtendKeyword > 0){
                className = candidateClassContentAsString.substring(indexOfClassKeyword+classKeyword.length(), indexOfExtendKeyword).replace(" ","");
                if (indexOfImplementsKeyword > 0){
                    baseClassName = candidateClassContentAsString.substring(indexOfExtendKeyword+extendKeyword.length(), indexOfImplementsKeyword).replace(" ","");
                    interfaceName = candidateClassContentAsString.substring(indexOfImplementsKeyword+implementsKeyword.length(), indexOfClassOpenCurlyBrace).replace(" ","");
                }else{
                    baseClassName = candidateClassContentAsString.substring(indexOfExtendKeyword+extendKeyword.length(), indexOfClassOpenCurlyBrace).replace(" ","");
                }
            }else {
                if (indexOfImplementsKeyword > 0){
                    className = candidateClassContentAsString.substring(indexOfClassKeyword+classKeyword.length(), indexOfImplementsKeyword).replace(" ","");
                    interfaceName = candidateClassContentAsString.substring(indexOfImplementsKeyword+implementsKeyword.length(), indexOfClassOpenCurlyBrace).replace(" ","");
                }
                else {
                    className = candidateClassContentAsString.substring(indexOfClassKeyword+classKeyword.length(), indexOfClassOpenCurlyBrace).replace(" ","");
                }
            }
            classList.add(new PageObjectClass(className, baseClassName, interfaceName));
        }
        return classList;
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
            if (indexOfInterfaceKeyword < 0){
                break;
            }
            int indexOfInterfaceOpenCurlyBrace = candidateContentAsString.indexOf('{');
            if (indexOfInterfaceOpenCurlyBrace < 0){
                break;
            }
            String interfaceName = candidateContentAsString.substring(indexOfInterfaceKeyword+interfaceKeyword.length(), indexOfInterfaceOpenCurlyBrace);
            interfaceName = interfaceName.replace(" ","");
            interfaceList.add(new PageObjectInterface(interfaceName));
        }
        return interfaceList;
    }
}
