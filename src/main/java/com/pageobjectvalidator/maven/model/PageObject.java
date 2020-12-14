package com.pageobjectvalidator.maven.model;

/**
 * @author sercansensulun on 13.12.2020.
 */
public abstract class PageObject {

    /**
     * Name of page object element
     * Example : LoginPageObject, LoginPageObjectInterface
     */
    private String name;

    public PageObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
