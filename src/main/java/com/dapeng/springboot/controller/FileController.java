package com.dapeng.springboot.controller;


import com.dapeng.springboot.dto.FileDto;
import com.dapeng.springboot.dto.JobDto;
import com.dapeng.springboot.dto.UserInfoDto;
import com.dapeng.springboot.service.JobSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Map;

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
            if (!fileName.endsWith(".pdf") && !fileName.endsWith(".word") && !fileName.endsWith(".excel") &&
                    !fileName.endsWith(".text")) {
                throw new RuntimeException("文件类型不支持");
            }
            String filePath = "files";//C:\Users\Public\Documents\
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


    @GetMapping("getByObsId.json")
    public String getFileObsId() {
        JobDto jobDto = jobSerivce.getById(1l);
        return jobDto.getFileId();

    }


    //Model model返回页面数据封装
    @RequestMapping("show/file.json")
    @ResponseBody
    public Map<String, Object> jsp(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        JobDto jobDto = jobSerivce.getById(1l);
        String fileId = jobDto.getFileId();
        model.addAttribute("fileId", fileId);
        log.info("路径-->>{}",fileId);
        FileInputStream fs = new FileInputStream(jobDto.getFileId());
        int b = fs.available();
        byte data[] = new byte[b];
        fs.read(data);

        String type = jobDto.getFileId().substring(fileId.lastIndexOf(".")+1);
        String contentType = "";
        if(type.equals("txt")){
            contentType = "text/plain; charset=utf-8";
        } else if(type.equals("pdf")){
            contentType = "application/pdf";
        } else if (type.equals("docx") || type.equals("doc")){
            contentType ="application/msword";
        } else if (type.equals("xlsx")||type.equals("xls")){
            contentType = "application/vnd.ms-excel";//application/x-xls   application/vnd.ms-excel
        }
        log.info("type ===>>>{}",type);
        response.setContentType(contentType);//设置返回的文件类型
        ServletOutputStream toClient = response.getOutputStream(); //得到向客户端输出二进制数据的对象
        toClient.write(data);  //输出数据
        toClient.flush();
        toClient.close();
        fs.close();
        return null;
    }

}
