package com.pageobjectvalidator.maven.impl;

import com.pageobjectvalidator.maven.interfaces.PageObjectReader;
import com.pageobjectvalidator.maven.interfaces.PageObjectValidator;
import org.apache.maven.plugin.MojoExecutionException;

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

    }

    /**
     * Validates that class names are matched with given regex and extended by given base class name.
     * @param regex regex value for class names.
     * @param baseClassName base class name
     */
    @Override
    public void validateClasses(String regex, String baseClassName) throws MojoExecutionException {

    }
}
