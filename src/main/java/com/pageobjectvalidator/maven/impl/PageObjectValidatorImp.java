package com.pageobjectvalidator.maven.impl;

import com.pageobjectvalidator.maven.interfaces.PageObjectReader;
import com.pageobjectvalidator.maven.interfaces.PageObjectValidator;
import com.pageobjectvalidator.maven.model.PageObjectClass;
import com.pageobjectvalidator.maven.model.PageObjectInterface;
import com.pageobjectvalidator.maven.utils.StringUtil;
import org.apache.maven.plugin.MojoExecutionException;

import java.util.List;

/**
 * @author sercansensulun on 13.12.2020.
 */
public class PageObjectValidatorImp implements PageObjectValidator {

    private PageObjectReader pageObjectReader;

    public PageObjectValidatorImp(PageObjectReader pageObjectReader) {
        this.pageObjectReader = pageObjectReader;
    }

    public PageObjectReader getPageObjectReader() {
        return pageObjectReader;
    }

    /**
     * Validate that interface names are matched with given regex.
     * @param regex regex value for interface names.
     */
    @Override
    public void validateInterfaces(String regex) throws MojoExecutionException {
        List<PageObjectInterface> interfaceList = pageObjectReader.getInterfaces();
        if (interfaceList.size() == 0){
            throw new MojoExecutionException("There is no any interface in absolute interface path.");
        }
        for (PageObjectInterface pageObjectInterface: interfaceList){
            String name = pageObjectInterface.getName();
            if (name == null){
                continue;
            }
            if (!name.matches(regex)){
                throw new MojoExecutionException(String.format("Interface do not match with your regex. " +
                        "\n Given regex is %s. " +
                        "\n Interface name is %s", regex, name));
            }
        }
    }

    /**
     * Validates that class names are matched with given regex and extended by given base class name.
     * @param regex regex value for class names.
     * @param baseClassName base class name
     */
    @Override
    public void validateClasses(String regex, String baseClassName) throws MojoExecutionException {
        List<PageObjectClass> classList = pageObjectReader.getClasses();
        if (StringUtil.isNullOrEmpty(baseClassName)){
            throw new MojoExecutionException("Page object classes should be extended by base class.");
        }
        if (classList.size() == 0){
            throw new MojoExecutionException("There is no any class in absolute class path.");
        }
        for (PageObjectClass pageObjectClass:classList) {
            String className = pageObjectClass.getName();
            String parentName = pageObjectClass.getParentName();
            String interfaceName = pageObjectClass.getInterfaceName();
            if (StringUtil.isNullOrEmpty(interfaceName)){
                throw new MojoExecutionException("Page object classes should implement an interface.");
            }
            if (StringUtil.isNullOrEmpty(parentName)){
                throw new MojoExecutionException("Page object classes should be extended by base class.");
            }
            if (!className.matches(regex)){
                throw new MojoExecutionException("There are classes with different regex.");
            }
        }
    }
}
