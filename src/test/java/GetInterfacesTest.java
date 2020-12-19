import com.pageobjectvalidator.maven.impl.PageObjectReaderImp;
import com.pageobjectvalidator.maven.model.PageObjectInterface;
import com.pageobjectvalidator.maven.utils.FileUtil;
import org.apache.maven.plugin.MojoExecutionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mockStatic;

/**
 * @author sercansensulun on 14.12.2020.
 */
public class GetInterfacesTest {

    private final String testClassesAbsolutePath = "src/test/data";
    private final String testInterfaceAbsolutePath = "src/test/data";
    private MockedStatic<FileUtil> fileUtilMock;

    @Before
    public void before(){
        fileUtilMock = mockStatic(FileUtil.class);
    }

    @After
    public void after(){
        fileUtilMock.close();
    }

    @Test
    public void when_there_is_nothing_then_return_empty_list() throws MojoExecutionException {
        fileUtilMock.when(() -> FileUtil.getJavaContents(testInterfaceAbsolutePath)).thenReturn(new ArrayList<>());
        fileUtilMock.when(() -> FileUtil.isExisting(testClassesAbsolutePath)).thenReturn(true);
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(testClassesAbsolutePath,testInterfaceAbsolutePath);
        List<PageObjectInterface> interfaceList = pageObjectReaderImp.getInterfaces();
        assertThat(interfaceList.size(), is(0));
    }

    @Test
    public void when_there_is_no_any_interface_then_return_empty_list() throws MojoExecutionException {
        fileUtilMock.when(() -> FileUtil.getJavaContents(testInterfaceAbsolutePath)).thenReturn(new ArrayList<StringBuilder>(){
            {add(new StringBuilder("text"));}
        });
        fileUtilMock.when(() -> FileUtil.isExisting(testClassesAbsolutePath)).thenReturn(true);
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(testClassesAbsolutePath,testInterfaceAbsolutePath);
        List<PageObjectInterface> interfaceList = pageObjectReaderImp.getInterfaces();
        assertThat(interfaceList.size(), is(0));
    }

    @Test
    public void given_anomaly_class_content_then_return_empty_list() throws MojoExecutionException {
        fileUtilMock.when(() -> FileUtil.getJavaContents(testInterfaceAbsolutePath)).thenReturn(new ArrayList<StringBuilder>(){
            {add(new StringBuilder("public interface Foo [}"));}
        });
        fileUtilMock.when(() -> FileUtil.isExisting(testClassesAbsolutePath)).thenReturn(true);
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(testClassesAbsolutePath,testInterfaceAbsolutePath);
        List<PageObjectInterface> interfaceList = pageObjectReaderImp.getInterfaces();
        assertThat(interfaceList.size(), is(0));
    }

    @Test
    public void when_there_is_an_interface_then_return_only_one_with_name() throws MojoExecutionException {
        fileUtilMock.when(() -> FileUtil.getJavaContents(testInterfaceAbsolutePath)).thenReturn(new ArrayList<StringBuilder>(){
            {add(new StringBuilder("public interface FooInterface { " +
                    "}"));}
        });
        fileUtilMock.when(() -> FileUtil.isExisting(testClassesAbsolutePath)).thenReturn(true);
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(testClassesAbsolutePath,testInterfaceAbsolutePath);
        List<PageObjectInterface> interfaceList = pageObjectReaderImp.getInterfaces();
        assertThat(interfaceList.size(), is(1));
        assertThat(interfaceList.get(0).getName(),is("FooInterface"));
    }


}
