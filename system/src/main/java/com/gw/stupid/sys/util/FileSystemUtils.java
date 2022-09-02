package com.gw.stupid.sys.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * 文件管理工具
 * 用于管理文件
 *
 * @author guanwu
 * @created on 2022-08-02 14:11:01
 **/

@Slf4j
public class FileSystemUtils {

    private static final String NO_SPACE_CN = "设备上没有空间";

    private static final String NO_SPACE_EN = "No space left on device";

    private static final String DISK_OVERFLOW_CN = "超出磁盘限额";

    private static final String DISK_OVERFLOW_EN = "Disk quota exceeded";

    /**
     * 创建目录，如果父级目录不存在时会强制创建
     * @param file
     * @throws IOException
     */
    public static void forceMakeDir(File file) throws IOException {
        FileUtils.forceMkdir(file);
    }

    public static void forceMakeDir(String filePath) throws IOException {
        FileUtils.forceMkdir(new File(filePath));
    }

    public static void forceMakeDir(String path, String fileName) throws IOException {
        FileUtils.forceMkdir(new File(Paths.get(path, fileName).toUri()));
    }
}
