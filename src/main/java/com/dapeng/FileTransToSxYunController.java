//package com.dapeng;
//
//import com.google.common.collect.Maps;
//import com.zds.appcore.facade.competency.api.CommonBlockAppServiceInteface;
//import com.zds.appcore.facade.competency.api.UploadFileToYunInterface;
//import com.zds.boot.common.exception.BusinessException;
//import com.zds.boot.common.facade.SingleRequest;
//import com.zds.boot.common.utils.Ids;
//import com.zds.boot.common.view.ViewResult;
//import com.zds.boot.transpond.comm.enums.ResultCode;
//import it.sauronsoftware.jave.AudioAttributes;
//import it.sauronsoftware.jave.Encoder;
//import it.sauronsoftware.jave.EncodingAttributes;
//import it.sauronsoftware.jave.VideoAttributes;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.util.Map;
//
///**
// * ==================================================
// * <p>
// * FileName: FileTransToSxYunController
// *
// * @author : lipeng
// * @create 2019/8/10
// * @since 1.0.0
// * 〈功能〉：上传视频到三峡云服务功能返回视频地址
// * ==================================================
// */
//@Slf4j
//@RestController
//@RequestMapping(value = "/backend/file")
//public class FileTransToSxYunController {
//
//    @Autowired
//    private CommonBlockAppServiceInteface commonBlockAppServiceInteface;
//    @Autowired
//    private UploadFileToYunInterface uploadFileToYunInterface;
//
//    @PostMapping("/toSxyun.json")
//    public ViewResult getRepaymentTrial(HttpServletRequest request) {
//        log.info("上传视频开始");
//        ViewResult viewResult = new ViewResult();
//        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
//        MultipartFile multipartFile = multipartRequest.getFile("file");
//        String url = "";
//        InputStream in = null;
//        File tempSource = null;
//        File target = null;
//        try {
//            String fileName = multipartFile.getOriginalFilename();
//            InputStream inmOrg = multipartFile.getInputStream();
//            log.info("视频大小：{}MB",inmOrg.available()/1024/1024);
//            if (inmOrg.available()/1024/1024 > 10){
//                log.info("压缩后上传");
//                //            InputStream in = VedioUtil.toCompress(multipartFile);
//                String ext = fileName.substring(fileName.lastIndexOf("."));
//                String uuid = Ids.gid();
//                String newName = uuid + ext;
//                //构建临时文件
//                tempSource = new File("/home/rydapp/" + newName);
//                multipartFile.transferTo(tempSource);//生成新的file文件
//                target = new File("/home/rydapp/" + fileName);//处理的目标文件
//
//                //压缩视频
//                Long begin = System.currentTimeMillis();
//                log.info("begin,{}", begin);
//                AudioAttributes audio = new AudioAttributes();//音频属性
//                audio.setCodec("libmp3lame");// libfaac PGM编码
//                audio.setBitRate(new Integer(56000));// 音频比特率
//                audio.setChannels(new Integer(1));// 声道
//                audio.setSamplingRate(new Integer(22050));// 采样率
//                VideoAttributes video = new VideoAttributes();// 视频属性
//                video.setCodec("mpeg4");// 视频编码
//                video.setBitRate(new Integer(200000));// 视频比特率
//                video.setFrameRate(new Integer(7));// 帧率
////            video.setSize(new VideoSize(1920,1080));// 视频宽高
//                EncodingAttributes attr = new EncodingAttributes();// 转码属性
//                attr.setFormat("mp4");// 视频格式
//                attr.setAudioAttributes(audio);// 音频属性
//                attr.setVideoAttributes(video);// 视频属性
//                Encoder encoder = new Encoder();// 创建解码器
//                encoder.encode(tempSource, target, attr);
//                log.info("压缩成功");
//                Long end = System.currentTimeMillis();
//                log.info("end,耗时:{}秒", (end - begin) / 1000);
//                Long size = target.length()/(1024*1024)+1;
//                log.error("视频大小：{}MB，{}",size,target.length());
//                if(target.exists() &&  size > 10){
//                    throw new BusinessException("视频过大,请重新录制", ResultCode.BUSINESS_EXCEPTION.code());
//                }
//                in = new FileInputStream(target);
//                log.info("获取视频流大小:{}",in.available());
//            } else {
//                log.info("不压缩，直接上传");
//                in = inmOrg;
//            }
//
//            Map<String, Object> map = Maps.newHashMap();
//            map.put("inputStream",in);
//            map.put("fileName",fileName);
//            url = uploadFileToYunInterface.uploadFileToSxYun(SingleRequest.from(map)).getData();
//            viewResult.setSuccess(true);
//            viewResult.setMessage("上传成功");
//            log.info("上传视频成功，url={}", url);
//        }catch (BusinessException e){
//            throw new BusinessException(e.getMessage());
//        } catch (Exception e) {
//            log.error("视频处理失败：", e);
//            throw new BusinessException("系统异常，请重试");
//        } finally {
//            if (tempSource != null) {
//                tempSource.delete();
//            }
//            if (target != null) {
//                target.delete();
//            }
//        }
//        viewResult.setData(url);
//        log.info("上传视频结束");
//        return viewResult;
//    }
//
//
//    /**
//     * 根据obsid下载图片，并上传到三峡云
//     * @param obsId
//     * @return
//     */
//    @GetMapping("/sfzToUpload.json")
//    @Transactional
//        public ViewResult scsxyAndLzrObsy(Long obsId) {
//        return commonBlockAppServiceInteface.uploadStorage(SingleRequest.from(obsId)).to();
//    }
//
//
//    /**
//     *========================================
//     * @方法说明 : 获取唇语
//     * @author : lipeng
//     * @创建时间:    2019-08-13 21:13:03
//     *========================================
//    */
//    @GetMapping("/getChunYu.json")
//    public ViewResult getChunYu(String length){
//        return commonBlockAppServiceInteface.getCyNum(SingleRequest.from(length)).to();
//    }
//
//
//}
