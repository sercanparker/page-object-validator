package com.pageobjectvalidator.maven.interfaces;

import com.pageobjectvalidator.maven.model.PageObjectClass;
import com.pageobjectvalidator.maven.model.PageObjectInterface;
import org.apache.maven.plugin.MojoExecutionException;

import java.util.List;

/**
 * @author sercansensulun on 12.12.2020.
 */
public interface PageObjectReader {

    List<PageObjectClass> getClasses() throws MojoExecutionException;

    List<PageObjectInterface> getInterfaces() throws MojoExecutionException;

}
