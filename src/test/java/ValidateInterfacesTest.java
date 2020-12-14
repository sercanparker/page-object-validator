import com.pageobjectvalidator.maven.impl.PageObjectValidatorImp;
import com.pageobjectvalidator.maven.interfaces.PageObjectReader;
import com.pageobjectvalidator.maven.model.PageObjectInterface;
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
public class ValidateInterfacesTest {

    //it will be mocked
    private PageObjectReader pageObjectReader;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void beforeTest(){
        pageObjectReader = mock(PageObjectReader.class);
    }

    @Test
    public void when_there_is_no_interface_then_throw_exception() throws MojoExecutionException {
        when(pageObjectReader.getInterfaces()).thenReturn(new ArrayList<>());
        PageObjectValidatorImp pageObjectValidatorImp = new PageObjectValidatorImp(pageObjectReader);
        exceptionRule.expect(MojoExecutionException.class);
        exceptionRule.expectMessage("There is no any interface in absolute interface path.");
        pageObjectValidatorImp.validateInterfaces("[A-Z][a-z]+PageObjectInterface$");
    }

    @Test
    public void when_there_are_different_interfaces_regex_then_throw_exception() throws MojoExecutionException {
        when(pageObjectReader.getInterfaces()).thenReturn(new ArrayList<PageObjectInterface>(){
            {
                add(new PageObjectInterface("FooObjectInterface"));
                add(new PageObjectInterface("BarPageObjectInterface"));
            }
        });
        PageObjectValidatorImp pageObjectValidatorImp = new PageObjectValidatorImp(pageObjectReader);
        exceptionRule.expect(MojoExecutionException.class);
        exceptionRule.expectMessage("There are interfaces with different regex.");
        pageObjectValidatorImp.validateInterfaces("[A-Z][a-z]+PageObjectInterface$");
    }

    @Test
    public void when_all_interfaces_are_same_regex_then_no_exception() throws MojoExecutionException {
        when(pageObjectReader.getInterfaces()).thenReturn(new ArrayList<PageObjectInterface>(){
            {
                add(new PageObjectInterface("FooPageObjectInterface"));
                add(new PageObjectInterface("BarPageObjectInterface"));
            }
        });
        PageObjectValidatorImp pageObjectValidatorImp = new PageObjectValidatorImp(pageObjectReader);
        exceptionRule.expect(Test.None.class);
        pageObjectValidatorImp.validateInterfaces("[A-Z][a-z]+PageObjectInterface$");
    }

}
