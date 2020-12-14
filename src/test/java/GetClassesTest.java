import com.pageobjectvalidator.maven.impl.PageObjectReaderImp;
import com.pageobjectvalidator.maven.model.PageObjectClass;
import com.pageobjectvalidator.maven.utils.FileUtil;
import org.junit.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mockStatic;

/**
 * @author sercansensulun on 13.12.2020.
 */
public class GetClassesTest {

    private final String testClassesAbsolutePath = "src/test/data";
    private final String testInterfaceAbsolutePath = "src/test/data";

    @Test
    public void when_there_is_nothing_then_return_empty_list(){
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(testClassesAbsolutePath,testInterfaceAbsolutePath);
        MockedStatic<FileUtil> fileUtilMock = mockStatic(FileUtil.class);
        fileUtilMock.when(() -> FileUtil.getJavaContents(testClassesAbsolutePath)).thenReturn(new ArrayList<StringBuilder>());
        List<PageObjectClass> classesList =  pageObjectReaderImp.getClasses();
        assert classesList.size() == 0;
    }

    @Test
    public void when_there_is_no_any_class_java_then_return_empty_list(){
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(testClassesAbsolutePath, testInterfaceAbsolutePath);
        MockedStatic<FileUtil> fileUtilMock = mockStatic(FileUtil.class);
        fileUtilMock.when(() -> FileUtil.getJavaContents(testClassesAbsolutePath)).thenReturn(new ArrayList<StringBuilder>(){
            {add(new StringBuilder("text"));}
        });

        List<PageObjectClass> classList = pageObjectReaderImp.getClasses();
        assert classList.size() == 0;
    }

    @Test
    public void when_there_is_class_without_parent_and_interface_then_return_only_one_with_only_name(){
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(testClassesAbsolutePath, testInterfaceAbsolutePath);
        MockedStatic<FileUtil> fileUtilMock = mockStatic(FileUtil.class);
        fileUtilMock.when(() -> FileUtil.getJavaContents(testClassesAbsolutePath)).thenReturn(new ArrayList<StringBuilder>(){
            {add(new StringBuilder(
                    "public class Foo " +
                    "{" +
                    "}"));}
        });

        List<PageObjectClass> classList = pageObjectReaderImp.getClasses();
        assert classList.size() == 1;
        assert classList.get(0).getName().equals("Foo");
    }

    @Test
    public void when_there_is_class_with_parent_and_without_interface_then_return_only_one_with_name_and_parent(){
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(testClassesAbsolutePath, testInterfaceAbsolutePath);
        MockedStatic<FileUtil> fileUtilMock = mockStatic(FileUtil.class);
        fileUtilMock.when(() -> FileUtil.getJavaContents(testClassesAbsolutePath)).thenReturn(new ArrayList<StringBuilder>(){
            {add(new StringBuilder(
                    "public class Foo extends Bar" +
                            "{" +
                            "}"));}
        });

        List<PageObjectClass> classList = pageObjectReaderImp.getClasses();
        assert classList.size() == 1;
        assert classList.get(0).getName().equals("Foo");
        assert classList.get(0).getParentName().equals("Bar");
    }

    @Test
    public void when_there_is_class_with_parent_and_interface_then_return_only_one_with_name_and_parent_and_interface(){
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(testClassesAbsolutePath, testInterfaceAbsolutePath);
        MockedStatic<FileUtil> fileUtilMock = mockStatic(FileUtil.class);
        fileUtilMock.when(() -> FileUtil.getJavaContents(testClassesAbsolutePath)).thenReturn(new ArrayList<StringBuilder>(){
            {add(new StringBuilder(
                    "public class Foo extends Bar implements Zoo" +
                            "{" +
                            "}"));}
        });

        List<PageObjectClass> classList = pageObjectReaderImp.getClasses();
        assert classList.size() == 1;
        assert classList.get(0).getName().equals("Foo");
        assert classList.get(0).getParentName().equals("Bar");
        assert classList.get(0).getInterfaceName().equals("Zoo");
    }


}
