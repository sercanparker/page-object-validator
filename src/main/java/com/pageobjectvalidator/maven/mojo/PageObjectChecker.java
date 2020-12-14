package com.pageobjectvalidator.maven.mojo;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.pageobjectvalidator.maven.impl.PageObjectReaderImp;
import com.pageobjectvalidator.maven.impl.PageObjectValidatorImp;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Checks naming conventions for Interfaces and Classes
 * while implementing page object design pattern.
 */
@Mojo(name = "PageObjectChecker")
public class PageObjectChecker extends AbstractMojo
{

    /**
     * Where classes are located on project
     * Example : src/java/page/impl
     */
    @Parameter(required = true)
    private String classesFolderPath;

    /**
     * Regex for class names
     * Example : *PageObject for LoginPageObject
     */
    @Parameter(required = true)
    private String classRegex;

    /**
     * Where interfaces are located on project
     * Example : src/java/page/interfaces
     */
    @Parameter(required = true)
    private String interfacesFolderPath;

    /**
     * Regex for interface names
     * Example : *PageObjectInterface for LoginPageObjectInterface
     */
    @Parameter(required = true)
    private String interfaceRegex;

    /**
     * Name of base class that is parent of pages
     * Example : BasePageObject
     */
    @Parameter(required = true)
    private String baseClassName;


    public void execute() throws MojoExecutionException {

        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(classesFolderPath, interfacesFolderPath);
        PageObjectValidatorImp pageObjectValidatorImp = new PageObjectValidatorImp(pageObjectReaderImp);
        pageObjectValidatorImp.validateClasses(classRegex, baseClassName);
        pageObjectValidatorImp.validateInterfaces(interfaceRegex);

    }
}
