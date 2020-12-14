import com.pageobjectvalidator.maven.impl.PageObjectReaderImp;
import com.pageobjectvalidator.maven.model.PageObjectInterface;
import com.pageobjectvalidator.maven.utils.FileUtil;
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
    public void when_there_is_nothing_then_return_empty_list(){
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(testClassesAbsolutePath,testInterfaceAbsolutePath);
        fileUtilMock.when(() -> FileUtil.getJavaContents(testInterfaceAbsolutePath)).thenReturn(new ArrayList<>());
        List<PageObjectInterface> interfaceList = pageObjectReaderImp.getInterfaces();
        assert interfaceList.size() == 0;
    }

    @Test
    public void when_there_is_no_any_interface_then_return_empty_list(){
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(testClassesAbsolutePath,testInterfaceAbsolutePath);
        fileUtilMock.when(() -> FileUtil.getJavaContents(testInterfaceAbsolutePath)).thenReturn(new ArrayList<StringBuilder>(){
            {add(new StringBuilder("text"));}
        });
        List<PageObjectInterface> interfaceList = pageObjectReaderImp.getInterfaces();
        assert interfaceList.size() == 0;
    }

    @Test
    public void when_there_is_an_interface_then_return_only_one_with_name(){
        PageObjectReaderImp pageObjectReaderImp = new PageObjectReaderImp(testClassesAbsolutePath,testInterfaceAbsolutePath);
        fileUtilMock.when(() -> FileUtil.getJavaContents(testInterfaceAbsolutePath)).thenReturn(new ArrayList<StringBuilder>(){
            {add(new StringBuilder("public interface FooInterface { " +
                    "}"));}
        });
        List<PageObjectInterface> interfaceList = pageObjectReaderImp.getInterfaces();
        assert interfaceList.size() == 1;
        assertThat(interfaceList.get(0).getName(),is("FooInterface"));
    }
}
