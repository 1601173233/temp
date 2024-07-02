package cn.com.toolWeb.util;

import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import cn.com.toolWeb.error.ExceptionUtil;
import cn.com.toolWeb.model.vo.action.FileInfoVo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import org.apache.commons.codec.binary.Hex;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;

/**
 * 文件工具
 *
 * @author jack.huang
 */
@Component
public class FileUtils {
    /** 文件所属路径 */
    @Value("${toolWeb.file.path}")
    private String _filePath;

    /** 文件所属路径 */
    private static String filePath;

    @PostConstruct
    public void init() {
        filePath = _filePath;
    }

    /**
     * 文件保存
     *
     * @return
     */
    public static FileInfoVo saveFile(MultipartFile multipartFile, String pathPrefix) throws IOException {
        String path = DateUtil.now().substring(0, 10) + "\\" + pathPrefix ;
        File dirFile = new File(filePath + path);
        dirFile.mkdirs();

        List<File> fileList = new ArrayList<>();
        File file = new File(filePath + path + "\\" + System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename());
        FileUtil.writeFromStream(multipartFile.getInputStream(), file);
        fileList.add(file);

        FileInfoVo fileInfoVo = new FileInfoVo();
        fileInfoVo.setFile(file);
        fileInfoVo.setFilePath(path + "\\" + file.getName());
        fileInfoVo.setMd5(getMD5(file));
        fileInfoVo.setName(multipartFile.getOriginalFilename());
        return fileInfoVo;
    }

    /**
     * 获取一个文件的md5值(可处理大文件)
     *
     * https://blog.csdn.net/weixin_29179311/article/details/114056350
     * @return md5 value
     */
    public static String getMD5(File file) {
        FileInputStream fileInputStream = null;
        try {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(file);

            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
            }

            return new String(Hex.encodeHex(MD5.digest()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取临时文件
     *
     * @param path 文件路径
     * @return 文件对象
     */
    public static File getTmpFile(String path) {
        return new File(filePath + "\\" + path);
    }
}