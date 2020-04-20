package com.base.biz.user.server.common;
import java.io.InputStream;
import java.util.*;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.base.common.exception.BaseException;

/**
 * @author:小M
 * @date:2020/4/20 10:36 PM
 */
public class ZipUtil {

    /**
     * 解析zip，保存其中的图片文件到指定的目录，返回保存的文件名称。
     * @param file
     * @param savePath
     * @return Map<String,String> 原文件名：保存的文件名
     * @throws Exception
     */
    public static Map<String,String> parseZipAndSaveImage(File file , String savePath) throws Exception{
        if(file == null) {
            throw new BaseException("zip文件不存在");
        }
        if(!file.getName().endsWith("zip")){
            throw new BaseException("请上传zip文件，其他格式不支持。");
        }
        File group = new File(savePath);
        if(!group.exists()){
            group.mkdirs();
        }
        Map<String,String> imageNameMap = new HashMap<>();

        try {
            ZipFile zipFile = new ZipFile(file, Charset.forName("GBK"));
            // 遍历zip中的文件/夹
            for (Enumeration<? extends ZipEntry> entries = zipFile.entries(); entries.hasMoreElements();){
                ZipEntry zipEntry = entries.nextElement();
                String zipEntryName = zipEntry.getName();
                zipEntryName = new String(zipEntryName.getBytes("GBK"));

                // 根据名称过滤非图片和系统临时文件
                if(!(zipEntryName.endsWith("png") || zipEntryName.endsWith("jpg") || zipEntryName.endsWith("jpeg"))||
                    zipEntryName.startsWith(".") || zipEntryName.startsWith("__")) {
                    continue;
                }

                // 过滤目录
                if(zipEntry.isDirectory()){
                    continue;
                }
                String oldImageName = zipEntryName.substring(zipEntryName.lastIndexOf("/") + 1,zipEntryName.length());
                String imageName = UUID.randomUUID() + ".png";
                String outUrl = savePath + imageName;
                InputStream in = zipFile.getInputStream(zipEntry);
                FileOutputStream out = new FileOutputStream(outUrl);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                imageNameMap.put(oldImageName,imageName);
            }
        }catch (Exception e) {
            if(imageNameMap != null && imageNameMap.size() > 0) {
                for(String key : imageNameMap.keySet()) {
                    String name = imageNameMap.get(key);
                    File f = new File(savePath + name);
                    if(f.exists()) {
                        f.delete();
                    }
                }
            }
            throw e;
        }

        return imageNameMap;
    }
}
