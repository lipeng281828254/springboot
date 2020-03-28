package com.dapeng.springboot.controller;


import cn.hutool.system.UserInfo;
import com.dapeng.springboot.dto.FileDto;
import com.dapeng.springboot.dto.JobDto;
import com.dapeng.springboot.dto.UserInfoDto;
import com.dapeng.springboot.service.JobSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@RestController
@RequestMapping("api/file/")
public class FileController {

    @Autowired
    private JobSerivce jobSerivce;

    /**
     * 上传附件
     *
     * @param file
     * @return
     */
    @PostMapping("upload.json")
    public FileDto upload(@RequestParam("file") MultipartFile file, @RequestParam("jobId") Long jobId, HttpServletRequest request) throws IOException {

        FileDto fileDto = new FileDto();
        log.info("jobId={}", jobId);
        HttpSession session = request.getSession();
        UserInfoDto userInfoDto = (UserInfoDto) session.getAttribute("userInfo");
        OutputStream out = null;
        File fileObj = null;
        try {
            if (file == null) {
                throw new RuntimeException("附件不能为空");
            }
            byte[] bytes = file.getBytes();
            String fileName = file.getOriginalFilename();//附件名称
            log.info("文件名称-->>{}", fileName);
            String filePath = "files";
            File pfile = new File(filePath);//创建文件夹
            if (!pfile.exists() && !pfile.isDirectory()) {
                log.info("目录不存在");
                pfile.mkdir();
            }
            //更新附件地址
            String fileId = filePath.concat("/").concat(fileName);
            JobDto jobDto = new JobDto();
            jobDto.setId(jobId);
            jobDto.setFileId(fileId);
            jobDto.setFileName(fileName);
            fileDto.setFileId(fileId);
            fileDto.setFileName(fileName);
            jobSerivce.updateJob(jobDto, userInfoDto);
            fileObj = new File(pfile, fileName);
            out = new FileOutputStream(fileObj);
            out.write(bytes);
        } catch (Exception e) {
            log.error("上传附件失败", e);
            if (fileObj != null) {
                fileObj.delete();
            }
            throw new RuntimeException("上传异常");
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return fileDto;
    }


}
