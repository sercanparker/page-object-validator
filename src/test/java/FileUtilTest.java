import com.pageobjectvalidator.maven.utils.FileUtil;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * @author sercansensulun on 13.12.2020.
 */
public class FileUtilTest {

    private final String testDataFolderPath = "src/test/data";

    @Test
    public void when_there_are_java_files_then_they_found(){
        File testDataFolder = new File(testDataFolderPath);
        List<File> javaFiles = FileUtil.getJavaFiles(testDataFolder.getAbsolutePath());
        for (File javaFile: javaFiles) {
            assert javaFile.getName().endsWith(".java");
        }
    }

    @Test
    public void when_exist_file_is_taken_then_content_is_returned(){
        File testTxtFile = new File(testDataFolderPath + "/" +
                "Test.txt");
        StringBuilder actualContent = FileUtil.getContent(testTxtFile);
        assert actualContent.toString().equals("test");
    }

    @Test
    public void when_there_are_java_files_then_their_contents_return(){
        List<StringBuilder> contentList = FileUtil.getJavaContents(testDataFolderPath);
        assert contentList.stream().anyMatch(x -> x.toString().equals("interface TestObjectInterface {}"));
        assert contentList.stream().anyMatch(x -> x.toString().equals("public class TestObjectClass {}"));
    }

    @Test
    public void when_there_is_no_any_java_file_then_return_empty_list(){
        List<StringBuilder> contentList = FileUtil.getJavaContents(testDataFolderPath + "/empty");
        assert contentList.size() == 0;
    }
}
