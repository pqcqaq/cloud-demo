package online.zust.services.chainservice.utils;

import com.google.common.io.Resources;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.chainmaker.sdk.utils.UtilsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author qcqcqc
 */
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    private FileUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 根据文件路径获取文件内容
     * 如果是在jar内访问，优先访问jar包外文件，然后在访问resource目录下文件
     *
     * @param resourcePath 文件路径
     * @return byte数组
     * @throws UtilsException
     */
    public static byte[] getResourceFileBytes(String resourcePath) throws UtilsException {
        byte[] fileBytes = null;

        String protocol = Objects.requireNonNull(FileUtils.class.getResource("")).getProtocol();
        if (Objects.equals(protocol, "jar")) {
            try {
                fileBytes = getFileBytes(resourcePath);
                return fileBytes;
            } catch (Exception e) {
                logger.warn("jar get file fail, msg :{}", e.getMessage());
            }
        }
        try {
            fileBytes = IOUtils.toByteArray((BufferedInputStream) Resources.getResource(resourcePath).getContent());
        } catch (IOException e) {
            throw new UtilsException("get file by path err : " + e.getMessage());
        }
        return fileBytes;

    }

    public static byte[] getFileBytes(String filePath) throws UtilsException {
        String replace = filePath.replace("\\", "/")
                .replace("//", "/")
                .replace("./", "/");
        byte[] fileBytes = null;
        Resource classPathResource = new ClassPathResource(replace);
        File f;
        try {
            f = classPathResource.getFile();
            fileBytes = IOUtils.toByteArray(new FileInputStream(f));
        } catch (Exception e) {
            throw new UtilsException("get file by path err : " + e.getMessage());
        }
        return fileBytes;
    }

    @SneakyThrows
    public static List<File> getFilesByPath(String path) {
        String replace = path.replace("\\", "/")
                .replace("//", "/")
                .replace("./", "/");
        Resource classPathResource = new ClassPathResource(replace);
        File file;
        try {
            file = classPathResource.getFile();
        } catch (Exception e) {
            throw new UtilsException("get file by path err : " + e.getMessage());
        }
        List<File> files = new ArrayList<>();
        File[] tempList = file.listFiles();
        if (tempList != null) {
            for (File value : tempList) {
                if (value.isFile() && value.getName().contains(".crt")) {
                    files.add(value);
                }
            }
        }
        return files;
    }

    public static String getResourceFilePath(String resourcePath) {
        return System.getProperty("user.dir") + "/src/main/resources/" + resourcePath;
    }

    public static boolean isFileExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
}
