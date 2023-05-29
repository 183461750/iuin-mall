package com.iuin.component.test.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 文件内容的工具类
 * @author:liujun
 * @date: 2018-05-28 16:20
 */
public class FileUtils {

    /**
     * 分页导出数据到文件的页大小
     */
    public static final int PAGE_SIZE = 1000;
    public static final String CSV_FILE_EXT = ".csv";
    public static final String XLSX_FILE_EXT = ".xlsx";

    private final static String PREFIX_VIDEO="video/";


    /**
     * 多媒体文件有格式和大小限制，如下：
     * 图片（image）: 2M，支持bmp/png/jpeg/jpg/gif格式
     * 语音（voice）：5M，播放长度不超过60s，支持AMR\MP3格式
     * 视频（video）：10MB，支持MP4格式
     * 缩略图（thumb）：64KB，支持JPG格式
     */
    public static class FileType {
        //文件类型 ： 图片类型
        public static final String IMG_PNG = "png";
        public static final String IMG_JPG = "jpg";
        public static final String IMG_JPEG = "jpeg";
        public static final String IMG_DMG = "bmp";
        public static final String IMG_GIF = "gif";

        public static final String VOICE_ARM = "AMR";
        public static final String VOICE_MP3 = "MP3";

        public static final String VIDEO_MP4 = "MP4";

        public static final String VOICE = "audio";
        public static final String VIDEO = "video";
        public static final String CSV = "csv";
        public static final String IMGE = "image";
        public static final String EXCEL = "xlsx";
        public static final String EXCEL_XLS = "xls";

        public static final String PKCS = "pkcs";
        public static final String PKCS_P12 = "p12";
    }

    private static final List<String> FILE_EXT_LIST = Arrays.stream(ReflectUtil.getFieldsValue(FileType.class)).map(Objects::toString).collect(Collectors.toList());

    /**
     * 判断文件是否有后缀名称文件
     * @param file
     * @return
     */
    public static boolean isFileName(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (StrUtil.isBlank(fileName)) {
            return Boolean.FALSE;
        }
        if (fileName.indexOf(".") == -1) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }


    /**
     * 根据文件名称 + 类型名称 是否符合
     *
     * @param file
     * @param type
     * @return
     */
    public static boolean isFileNameByType(MultipartFile file, String type) {
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        boolean flag = Boolean.FALSE;
        switch (type) {
            case FileType.CSV:
                flag = FileType.CSV.equalsIgnoreCase(suffix);
                break;
            case FileType.EXCEL:
                flag = FileType.EXCEL.equalsIgnoreCase(suffix) || FileType.EXCEL_XLS.equalsIgnoreCase(suffix);
                break;
            case FileType.IMGE:
                flag = (FileType.IMG_PNG.equalsIgnoreCase(suffix) ||
                        FileType.IMG_JPG.equalsIgnoreCase(suffix) ||
                        FileType.IMG_JPEG.equalsIgnoreCase(suffix) ||
                        FileType.IMG_DMG.equalsIgnoreCase(suffix) ||
                        FileType.IMG_DMG.equalsIgnoreCase(suffix) ||
                        FileType.IMG_GIF.equalsIgnoreCase(suffix));
                break;
            case FileType.VOICE:
                flag = FileType.VOICE_ARM.equalsIgnoreCase(suffix) || FileType.VOICE_MP3.equalsIgnoreCase(suffix);
                break;
            case FileType.VIDEO:
                flag = FileType.VIDEO_MP4.equalsIgnoreCase(suffix);
                break;
            case FileType.PKCS:
                flag = FileType.PKCS_P12.equalsIgnoreCase(suffix);
                break;
            default:
        }

        return flag;
    }



    /**
     * 判断文件是否为空
     * @param file
     * @return
     */
    public static boolean isNullFile(MultipartFile file) {
        return Objects.isNull(file);
    }





    /**
     *
     * Description: 生成CSV文件
     *
     * @param: head
     * @param: dataList
     * @param: fileName
     * @param: filePath
     * @auther: HJD
     * @date: 2018/8/3 9:45
     */
    public static boolean generateCSVFile(Object[] head,List<List<Object>> dataList,String fileName,String filePath){
        List<Object> headList = Arrays.asList(head);
        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            csvFile = new File(filePath +"/"+ fileName);
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "utf-8"), 1024);

            //文件下载，使用如下代码
//            response.setContentType("application/csv;charset=gb18030");
//            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
//            ServletOutputStream out = response.getOutputStream();
//            csvWtriter = new BufferedWriter(new OutputStreamWriter(out, "GB2312"), 1024);

            int num = headList.size() / 2;
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < num; i++) {
                buffer.append(" ,");
            }
            /*csvWtriter.write(buffer.toString() + fileName + buffer.toString());
            csvWtriter.newLine();*/

            // 写入文件头部
            writeRow(headList, csvWtriter);

            // 写入文件内容
            for (List<Object> row : dataList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                csvWtriter.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * Description：写一行csv数据
     * @param row 数据列表
     * @param csvWriter
     * @auther: HJD
     */
    private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
        for (Object data : row) {
            StringBuffer sb = new StringBuffer();
            String rowStr = sb.append("\"").append(data).append("\",").toString();
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }

    /**
     * 文件名中是否包含在Windows下不支持的非法字符，包括： \ / : * ? " &lt; &gt; |
     */
    public static boolean containsInvalid(List<String> fileNameList) {
        return Optional.ofNullable(fileNameList).orElseGet(ArrayList::new).stream().map(FileUtil::containsInvalid).reduce((aBoolean, aBoolean2) -> aBoolean && aBoolean2).orElse(Boolean.FALSE);
    }

    /**
     * 文件名中是否包含在Windows下不支持的非法字符，包括： \ / : * ? " &lt; &gt; |
     */
    public static boolean containsInvalid(String... fileNameList) {
        return Optional.ofNullable(fileNameList).map(CollUtil::toList).orElseGet(ArrayList::new).stream().map(FileUtil::containsInvalid).reduce((aBoolean, aBoolean2) -> aBoolean && aBoolean2).orElse(Boolean.FALSE);
    }

    /**
     * 文件名中是否包含非法字符，包括： ..
     */
    public static boolean containsReversePath(String pathStr) {
        return Optional.ofNullable(pathStr).map(s -> s.contains("..")).orElse(Boolean.FALSE);
    }

    /**
     * 文件名中是否包含非法字符，包括： ..
     */
    public static boolean containsReversePath(String... pathStr) {
        return Optional.ofNullable(pathStr).map(CollUtil::toList).map(s -> s.contains("..")).orElse(Boolean.FALSE);
    }

    /**
     * 文件名中是否包含非法字符，包括： ..
     */
    public static String cleanReversePath(String pathStr) {
        return Optional.ofNullable(pathStr).map(s -> s.replace("..", "")).orElse("");
    }

    /**
     * 校验MineType是否有效
     */
    public static Boolean validMineType(String mimeType) {
        return TikaConfig.getDefaultConfig().getMediaTypeRegistry().getTypes().stream().anyMatch(mediaType -> mediaType.getType().equals(mimeType));
    }

    /**
     * 校验文件扩展名
     */
    public static Boolean validFileExt(String fileExt) {
        return FILE_EXT_LIST.contains(fileExt);
    }

    /**
     * 获取文件扩展名
     */
    public static String extName(String fileName) {
        return FileUtil.extName(fileName);
    }

    /**
     * 校验文件扩展名
     */
    public static Boolean validMineTypeAndFileExt(MultipartFile multipartFile) {
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            InputStream inputStream = multipartFile.getInputStream();
            return validMineTypeAndFileExt(inputStream, originalFilename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 校验文件
     */
    public static Boolean validMineTypeAndFileExt(InputStream inputStream, String fileName) {
        try {
            Tika tika = new Tika();
            String mimeType = tika.detect(inputStream);
            String mimeType2 = tika.detect(fileName);
            return Objects.equals(mimeType, mimeType2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 校验上传文件
     */
    public static Boolean validUploadFile(MultipartFile multipartFile) {
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            InputStream inputStream = multipartFile.getInputStream();
            return validUploadFile(inputStream, originalFilename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 校验上传文件
     */
    public static Boolean validUploadFile(InputStream inputStream, String fileName) {
        return FILE_EXT_LIST.contains(FileUtil.extName(fileName)) && validMineTypeAndFileExt(inputStream, fileName);
    }

}
