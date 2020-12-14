package com.pageobjectvalidator.maven.interfaces;

import org.apache.maven.plugin.MojoExecutionException;

/**
 * @author sercansensulun on 13.12.2020.
 */
public interface PageObjectValidator {

    void validateInterfaces(String regex) throws MojoExecutionException;

    void validateClasses(String regex, String baseClassName) throws MojoExecutionException;
}
