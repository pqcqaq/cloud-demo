package online.zust.services.chainservice.utils;

import com.google.common.io.Resources;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.chainmaker.sdk.utils.UtilsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public static byte[] readAllBytes(InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int n = 0;
        try {
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
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
            fileBytes = getFileBytes(resourcePath);
        } catch (Exception e) {
            throw new UtilsException("get file by path err : " + e.getMessage());
        }
        return fileBytes;

    }

    public static String getRealPathByPath(String path) {
        String path2 = FileUtils.class.getClassLoader().getResource("").getPath();
        if (path2.contains("jar")) {
            String substring = path.substring(path.lastIndexOf("/"));
            return getRealPath(substring);
        }
        return path.substring(1);
    }

    public static String getRealPath(String name) {
        String path = FileUtils.class.getClassLoader().getResource("").getPath();
        if (path.contains("jar")) {
            return getJarPath() + name;
        }
        return name.substring(1);
    }

    public static byte[] getFileBytes(String filePath) throws UtilsException {
        URL resource = FileUtils.class.getClassLoader().getResource("");
        assert resource != null;
        if (resource.getPath().contains("jar")) {
            String replace = getPathTrim(filePath);
            String directoryPath = getJarPath();
            String filePathTrue = directoryPath + replace;
            InputStream in = null;
            try {
                in = Files.newInputStream(Paths.get(filePathTrue));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (in != null) {
                return readAllBytes(in);
            }
            System.out.println(filePathTrue);

            throw new UtilsException("cannot open file : " + replace);
        }
        String replace = getPathTrim(filePath);
        byte[] fileBytes;
        Resource classPathResource = new ClassPathResource(replace);
        try {
            fileBytes = IOUtils.toByteArray(classPathResource.getInputStream());
        } catch (Exception e) {
            throw new UtilsException("get file by path err : " + e.getMessage());
        }
        return fileBytes;
    }

    public static String getPathTrim(String filePath) {
        return filePath.replace("\\", "/")
                .replace("//", "/")
                .replace("./", "/");
    }

    @SneakyThrows
    public static List<File> getFilesByPath(String path) {
        URL resource = FileUtils.class.getClassLoader().getResource("");
        assert resource != null;
        if (resource.getPath().contains("jar")) {
            String filePathTrue = genTrueJarResPath(path);
            File file;
            try {
                file = new File(filePathTrue);
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
        String replace = getPathTrim(path);
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

    public static String genTrueJarResPath(String path) {
        String replace = getPathTrim(path);
        String directoryPath = getJarPath();
        return directoryPath + replace;
    }

    public static String getJarPath() {
        String jarPath = new File(FileUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getAbsolutePath();
        String replace1 = jarPath.substring(0, jarPath.indexOf("file:") + 1);
        Path jarFilePath = Paths.get(replace1);
        Path parentPath = jarFilePath.getParent();
        return parentPath.toString();
    }

    public static String getResourceFilePath(String resourcePath) {
        return System.getProperty("user.dir") + "/src/main/resources/" + resourcePath;
    }

    public static boolean isFileExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
}
