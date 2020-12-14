package com.pageobjectvalidator.maven.model;

/**
 * @author sercansensulun on 13.12.2020.
 */
public class PageObjectClass extends PageObject {

    /**
     * name of parent class that was extended.
     * Example : BasePage for LoginPageObject extends BasePage
     */
    private String parentName;

    /**
     * name of interface that was implemented.
     * Example : LoginPageInterface for LoginPageObject extends BasePage implements LoginPageInterface
     */
    private String interfaceName;


    public PageObjectClass(String name) {
        super(name);
    }

    public PageObjectClass(String name, String parentName, String interfaceName){
        super(name);
        this.parentName = parentName;
        this.interfaceName = interfaceName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getParentName() {
        return parentName;
    }
}
