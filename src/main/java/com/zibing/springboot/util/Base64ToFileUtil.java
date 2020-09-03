package com.zibing.springboot.util;

import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.Base64;

public class Base64ToFileUtil {


    public static void base64ToFile(String base64, String fileName) {
        File file = null;
        //创建文件目录
        String filePath = "D:\\image";
        File dir = new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            file = new File(filePath + "\\" + fileName);
            fos = new java.io.FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void base64ToFile2(String base64) throws Exception {
        BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        byte[] bytes1 = decoder.decodeBuffer(base64);
        OutputStream out = new FileOutputStream("D://fffffd.png");
        out.write(bytes1);
        out.flush();
        out.close();
    }

    public static void main(String[] args) throws Exception {
        String ss = "";
        File file = new File("D:\\xx.txt");
        if (file.isFile() && file.exists()) { //判断文件是否存在
            InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file));//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                System.out.println(lineTxt);
                ss = lineTxt;
            }
            read.close();
        } else {
            System.out.println("找不到指定的文件");

        }
        xxxx(ss);
    }


    private static void xxxx(String base64Pic) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
//        String baseValue = base64Pic.replaceAll(" ", "+");
        byte[] b = decoder.decodeBuffer(base64Pic.replace("data:image/jpeg;base64,", ""));

        System.out.println(b);
        String imgFilePath = "D:\\1.jpg";

        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {// 调整异常数据
                b[i] += 256;
            }
        }
        // 生成jpeg图片
        OutputStream out = new FileOutputStream(imgFilePath);
        out.write(b);
        out.flush();
        out.close();

    }
}
