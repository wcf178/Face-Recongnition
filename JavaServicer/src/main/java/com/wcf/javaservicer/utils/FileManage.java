package com.wcf.javaservicer.utils;

import org.apache.ibatis.jdbc.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;



import java.io.File;
import java.io.IOException;

/**
 * 文件出路类
 */
public class FileManage {

    private static final Logger logger = LoggerFactory.getLogger(FileManage.class);

    /**
     * 保存图片到指定路径
     * @param file 文件
     * @param UPLOAD_PATH 路径
     * @return 是否保存成功
     */
    public static String savePicture(MultipartFile file,String UPLOAD_PATH){
        String flagName;
        if (!file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();
                // 随机文件名
                String newFileName = SnowflakeIdWorker.getUUID()
                        + originalFilename.substring(originalFilename.lastIndexOf("."));
                // 上传文件路径
                File upload = new File(UPLOAD_PATH, "images/");
                if (!upload.exists())
                    upload.mkdirs();
                //windows 上传路径写法
                String uploadPath = upload + "/";
                //linux上传路径写法
//                String uploadPath = upload + "/";
                logger.info("保存路径->/upload/facepicture/images/" + newFileName);
                File uploadfile = new File(uploadPath + newFileName);
                // 将上传文件保存到一个目标文件当中
                file.transferTo(uploadfile);
                flagName=UPLOAD_PATH+"/images/" + newFileName;
            } catch (IllegalStateException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                flagName= null;
            }
        }else flagName=null;

        return flagName;
    }

    public static String savePortrait(MultipartFile file,String UPLOAD_PATH) {
        String flagName;
        if (!file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();
                // 随机文件名
                String newFileName = SnowflakeIdWorker.getUUID()
                        + originalFilename.substring(originalFilename.lastIndexOf("."));
                // 上传文件路径
                File upload = new File(UPLOAD_PATH, "images/");
                if (!upload.exists())
                    upload.mkdirs();
                String uploadPath = upload + "/";
                logger.info("保存路径->/upload/facepicture/images/" + newFileName);
                File uploadfile = new File(uploadPath + newFileName);
                // 将上传文件保存到一个目标文件当中
                file.transferTo(uploadfile);
                flagName="/upload/portrait/images/" + newFileName;
            } catch (IllegalStateException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                flagName= null;
            }
        }else flagName=null;

        return flagName;
    }

}