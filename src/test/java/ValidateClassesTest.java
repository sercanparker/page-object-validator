import com.pageobjectvalidator.maven.impl.PageObjectValidatorImp;
import com.pageobjectvalidator.maven.interfaces.PageObjectReader;
import com.pageobjectvalidator.maven.model.PageObjectClass;
import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author sercansensulun on 13.12.2020.
 */
public class ValidateClassesTest {

    //it will be mocked
    private PageObjectReader pageObjectReader;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void beforeTest(){
        pageObjectReader = mock(PageObjectReader.class);
    }


    @Test
    public void when_there_is_no_any_class_then_throw_exception() throws MojoExecutionException {
        when(pageObjectReader.getClasses()).thenReturn(new ArrayList<>());
        PageObjectValidatorImp pageObjectValidatorImp = new PageObjectValidatorImp(pageObjectReader);
        exceptionRule.expect(MojoExecutionException.class);
        exceptionRule.expectMessage("There is no any class in absolute class path.");
        pageObjectValidatorImp.validateClasses("[A-Z][a-z]+PageObject$", "PageObject");
    }

    @Test
    public void when_classes_have_different_regex_then_throw_exception() throws MojoExecutionException {
        when(pageObjectReader.getClasses()).thenReturn(new ArrayList<PageObjectClass>(){
            {
                add(new PageObjectClass("LoginPage", "PageObject","LoginPageObjectInterface"));
                add(new PageObjectClass("MainPageObject", "PageObject", "MainPageObjectInterface"));
            }
        });
        PageObjectValidatorImp pageObjectValidatorImp = new PageObjectValidatorImp(pageObjectReader);
        exceptionRule.expect(MojoExecutionException.class);
        exceptionRule.expectMessage(String.format("There are classes with different regex. " +
                "Given regex is %s but found class name is %s",
                "[A-Z][a-z]+PageObject$", "LoginPage"));
        pageObjectValidatorImp.validateClasses("[A-Z][a-z]+PageObject$", "PageObject");
    }

    @Test
    public void when_classes_has_no_base_class_then_throw_exception() throws MojoExecutionException {
        when(pageObjectReader.getClasses()).thenReturn(new ArrayList<PageObjectClass>(){
            {
                add(new PageObjectClass("LoginPageObject", null,"LoginPageObjectInterface"));
                add(new PageObjectClass("MainPageObject", null, "MainPageObjectInterface"));
            }
        });
        PageObjectValidatorImp pageObjectValidatorImp = new PageObjectValidatorImp(pageObjectReader);
        exceptionRule.expect(MojoExecutionException.class);
        exceptionRule.expectMessage("Page object classes should be extended by base class.");
        pageObjectValidatorImp.validateClasses("[A-Z][a-z]+PageObject$", "PageObject");
    }

    @Test
    public void when_classes_has_no_interface_then_throw_exception() throws MojoExecutionException {
        when(pageObjectReader.getClasses()).thenReturn(new ArrayList<PageObjectClass>(){
            {
                add(new PageObjectClass("LoginPageObject", "PageObject",null));
                add(new PageObjectClass("MainPageObject", "PageObject", null));
            }
        });
        PageObjectValidatorImp pageObjectValidatorImp = new PageObjectValidatorImp(pageObjectReader);
        exceptionRule.expect(MojoExecutionException.class);
        exceptionRule.expectMessage("Page object classes should implement an interface.");
        pageObjectValidatorImp.validateClasses("[A-Z][a-z]+PageObject$", "PageObject");
    }

    @Test
    public void when_classes_has_same_base_class_and_same_interface_regex_then_no_exception() throws MojoExecutionException {
        when(pageObjectReader.getClasses()).thenReturn(new ArrayList<PageObjectClass>(){
            {
                add(new PageObjectClass("LoginPageObject", "PageObject","LoginPageObjectInterface"));
                add(new PageObjectClass("MainPageObject", "PageObject", "MainPageObjectInterface"));
            }
        });
        PageObjectValidatorImp pageObjectValidatorImp = new PageObjectValidatorImp(pageObjectReader);
        pageObjectValidatorImp.validateClasses("[A-Z][a-z]+PageObject$", "PageObject");

    }
}
