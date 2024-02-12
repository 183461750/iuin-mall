package com.iuin.ssoserver.maintest.test8;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

/**
 * <p>TODO</p>
 *
 * @author fa
 */
public class TestMain {


    public static void main(String[] args) {
        final Calendar calendar = Calendar.getInstance();
        String localFilePath = "/data/wwwroot/shushangyun/uploads";
        String uploadFilePath = "upload/csv//";
        Path path = Paths.get("/", "http://baidu.com", localFilePath, uploadFilePath, String.valueOf(calendar.get(Calendar.YEAR)), String.valueOf(calendar.get(Calendar.MONTH) + 1), String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

        URLUtil.completeUrl("http://baidu.com", path.normalize().toString());

        String testPath = path.toUri().getPath();
        boolean bool = StrUtil.startWith(testPath, '/');
        if (bool) {
            testPath = testPath.substring(1);
        }

        System.out.println(path.toUri().getPath());
        System.out.println(path);
        System.out.println(testPath);
        System.out.println(path.normalize().toString());
    }


}
