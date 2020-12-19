import com.pageobjectvalidator.maven.impl.PageObjectReaderImp;
import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author sercansensulun on 19.12.2020.
 */
public class PageObjectReaderImpTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void given_class_folder_path_is_empty_then_throw_exception() throws MojoExecutionException {
        exceptionRule.expect(MojoExecutionException.class);
        exceptionRule.expectMessage("Class path is empty. Please check your configuration tag.");
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(
                "",
                "src/main/java/interfaces");
    }

    @Test
    public void given_interface_folder_path_is_empty_then_throw_exception() throws MojoExecutionException {
        exceptionRule.expect(MojoExecutionException.class);
        exceptionRule.expectMessage("Interface path is empty. Please check your configuration");
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(
                "src/main/java/page",
                "");
    }

    @Test
    public void given_class_path_does_not_exist_then_throw_exception() throws MojoExecutionException {
        exceptionRule.expect(MojoExecutionException.class);
        exceptionRule.expectMessage("Class path does not exist. Please check you configuration tag.");
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(
                "src/test/data/haha",
                "src/test/data");
    }

    @Test
    public void given_interface_path_does_not_exist_then_throw_exception() throws MojoExecutionException {
        exceptionRule.expect(MojoExecutionException.class);
        exceptionRule.expectMessage("Interface path does not exist. Please check you configuration tag.");
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(
                "src/test/data",
                "src/test/data/lolo");
    }

    @Test
    public void given_interface_and_class_paths_exist_then_no_exception() throws MojoExecutionException {
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(
                "src/test/data",
                "src/test/data");
    }
}
