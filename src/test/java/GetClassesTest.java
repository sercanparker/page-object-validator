import com.pageobjectvalidator.maven.impl.PageObjectReaderImp;
import com.pageobjectvalidator.maven.model.PageObjectClass;
import com.pageobjectvalidator.maven.utils.FileUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mockStatic;

/**
 * @author sercansensulun on 13.12.2020.
 */
public class GetClassesTest {

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
    public void when_there_is_nothing_then_return_empty_list(){
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(testClassesAbsolutePath,testInterfaceAbsolutePath);
        fileUtilMock.when(() -> FileUtil.getJavaContents(testClassesAbsolutePath)).thenReturn(new ArrayList<StringBuilder>());
        List<PageObjectClass> classesList =  pageObjectReaderImp.getClasses();
        assertThat(classesList.size(), is(0));
    }

    @Test
    public void when_there_is_no_any_class_java_then_return_empty_list(){
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(testClassesAbsolutePath, testInterfaceAbsolutePath);
        fileUtilMock.when(() -> FileUtil.getJavaContents(testClassesAbsolutePath)).thenReturn(new ArrayList<StringBuilder>(){
            {add(new StringBuilder("text"));}
        });
        List<PageObjectClass> classList = pageObjectReaderImp.getClasses();
        assertThat(classList.size(), is(0));
    }

    @Test
    public void given_interface_content_has_anomaly_then_return_empty_list(){
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(testClassesAbsolutePath, testInterfaceAbsolutePath);
        fileUtilMock.when(() -> FileUtil.getJavaContents(testClassesAbsolutePath)).thenReturn(new ArrayList<StringBuilder>(){
            {add(new StringBuilder(
                    "public class Foo " +
                            "[" +
                            "}"));}
        });
        List<PageObjectClass> classList = pageObjectReaderImp.getClasses();
        assertThat(classList.size(), is(0));
    }

    @Test
    public void when_there_is_class_without_parent_and_interface_then_return_only_one_with_only_name(){
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(testClassesAbsolutePath, testInterfaceAbsolutePath);
        fileUtilMock.when(() -> FileUtil.getJavaContents(testClassesAbsolutePath)).thenReturn(new ArrayList<StringBuilder>(){
            {add(new StringBuilder(
                    "public class Foo " +
                    "{" +
                    "}"));}
        });
        List<PageObjectClass> classList = pageObjectReaderImp.getClasses();
        assertThat(classList.size(), is(1));
        assertThat(classList.get(0).getName(), is("Foo"));
        assertNull(classList.get(0).getParentName());
        assertNull(classList.get(0).getInterfaceName());
    }

    @Test
    public void when_there_is_class_without_parent_then_return_only_one_with_interface(){
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(testClassesAbsolutePath, testInterfaceAbsolutePath);
        fileUtilMock.when(() -> FileUtil.getJavaContents(testClassesAbsolutePath)).thenReturn(new ArrayList<StringBuilder>(){
            {add(new StringBuilder(
                    "public class Foo implements Bar" +
                            "{" +
                            "}"));}
        });
        List<PageObjectClass> classList = pageObjectReaderImp.getClasses();
        assertThat(classList.size(), is(1));
        assertThat(classList.get(0).getName(), is("Foo"));
        assertNull(classList.get(0).getParentName());
        assertThat(classList.get(0).getInterfaceName(), is("Bar"));
    }

    @Test
    public void when_there_is_class_with_parent_and_without_interface_then_return_only_one_with_name_and_parent(){
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(testClassesAbsolutePath, testInterfaceAbsolutePath);
        fileUtilMock.when(() -> FileUtil.getJavaContents(testClassesAbsolutePath)).thenReturn(new ArrayList<StringBuilder>(){
            {add(new StringBuilder(
                    "public class Foo extends Bar" +
                            "{" +
                            "}"));}
        });
        List<PageObjectClass> classList = pageObjectReaderImp.getClasses();
        assertThat(classList.size(), is(1));
        assertThat(classList.get(0).getName(), is("Foo"));
        assertThat(classList.get(0).getParentName(), is("Bar"));
        assertNull(classList.get(0).getInterfaceName());
    }

    @Test
    public void when_there_is_class_with_parent_and_interface_then_return_only_one_with_name_and_parent_and_interface(){
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(testClassesAbsolutePath, testInterfaceAbsolutePath);
        fileUtilMock.when(() -> FileUtil.getJavaContents(testClassesAbsolutePath)).thenReturn(new ArrayList<StringBuilder>(){
            {add(new StringBuilder(
                    "public class Foo extends Bar implements Zoo" +
                            "{" +
                            "}"));}
        });

        List<PageObjectClass> classList = pageObjectReaderImp.getClasses();
        assertThat(classList.size(), is(1));
        assertThat(classList.get(0).getName(), is("Foo"));
        assertThat(classList.get(0).getParentName(), is("Bar"));
        assertThat(classList.get(0).getInterfaceName(), is("Zoo"));
    }


}
