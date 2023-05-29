package com.iuin.component.test.test1;

import cn.hutool.core.io.FileUtil;
import com.iuin.component.test.utils.FileUtils;
import org.apache.tika.Tika;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.file.Paths;

/**
 * <p>TODO</p>
 *
 * @author fa
 */
public class TestMain {

    public static void main(String[] args) throws IOException {

        {
            String originalFilename = "1.png";

            FileNameMap fileNameMap = URLConnection.getFileNameMap();
            String mimeType = fileNameMap.getContentTypeFor(originalFilename);

            System.out.println(mimeType);
        }

        {
            String originalFilename = "1.png";

            String mimeType = URLConnection.guessContentTypeFromName(originalFilename);

            System.out.println(mimeType);
        }

        {
            FileInputStream fileInputStream = new FileInputStream(Paths.get("/Users/fa/dev/IdeaProjects/ma/wap-api/dog.jpeg").toString());
            String mimeType = URLConnection.guessContentTypeFromStream(fileInputStream);

            System.out.println(mimeType);
        }

        {
            FileInputStream fileInputStream = new FileInputStream(Paths.get("/Users/fa/dev/IdeaProjects/ma/wap-api/dog.jpeg").toString());

            Tika tika = new Tika();

            String mimeType = tika.detect(fileInputStream);

            System.out.println(mimeType);
        }

        {
            String originalFilename = "dog.jpeg";
            FileInputStream fileInputStream = new FileInputStream(Paths.get("/Users/fa/dev/IdeaProjects/ma/wap-api/" + originalFilename).toString());
            Boolean aBoolean = FileUtils.validMineTypeAndFileExt(fileInputStream, originalFilename);

            System.out.println(aBoolean);
        }

        {
            String originalFilename = "dog.jpeg";
            File file = FileUtil.file("classpath:" + originalFilename);
            FileInputStream fileInputStream = new FileInputStream(file);
            Boolean aBoolean = FileUtils.validUploadFile(fileInputStream, originalFilename);

            System.out.println(aBoolean);
        }

        {
            String originalFilename = "dog.png";
            File file = FileUtil.file("classpath:" + originalFilename);
            FileInputStream fileInputStream = new FileInputStream(file);
            Boolean aBoolean = FileUtils.validUploadFile(fileInputStream, originalFilename);

            System.out.println(aBoolean);
        }

    }

}
