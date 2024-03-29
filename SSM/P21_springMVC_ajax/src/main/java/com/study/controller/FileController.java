package com.study.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Controller
public class FileController {
    //文件下载
    @RequestMapping("/testDown")
    public ResponseEntity<byte[]> testDown(HttpSession session) throws IOException {
        //获取ServletContext对象
        ServletContext servletContext = session.getServletContext();
        //获取服务器中文件的真实路径
        //String realPath = servletContext.getRealPath("/static/img/a.jpg");
        String realPath = servletContext.getRealPath("static");
        realPath = realPath + File.separator + "img"+File.separator+"a.jpg";
        //创建输入流
        InputStream is = new FileInputStream(realPath);
        //创建字节数组
        byte[] bytes = new byte[is.available()];
        //将流读到字节数组中,is.available()获取输入流所对应文件的字节数（只能传一次流）
        is.read(bytes);
        //创建HttpHeaders对象设置响应头信息
        MultiValueMap<String, String> headers = new HttpHeaders();
        //设置要下载方式以及下载文件的名字
        headers.add("Content-Disposition", "attachment;filename=1.jpg");
        //设置响应状态码
        HttpStatus statusCode = HttpStatus.OK;
        //创建ResponseEntity对象
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers,statusCode);
        //关闭输入流
        is.close();
        return responseEntity;
    }
    //文件上传
    @RequestMapping("/testUp")
    public String testUp(MultipartFile user_up, HttpSession session)throws IOException{
        //获取容器对象
        ServletContext servletContext = session.getServletContext();
        //获得上传文件的文件名
        String upFilename = user_up.getOriginalFilename();
        //加入特殊编码，处理重名问题
        String fileName= UUID.randomUUID() +"--"+upFilename;
        //获取文件目录
        String up = servletContext.getRealPath("Up");
        File f=new File(up);
        if (!f.exists()){
            f.mkdirs();
        }
        //得出最后的最终路径
        String nameFinal = up + File.separator + fileName;
        //System.out.println("上传的最终路径为："+nameFinal);
        //user_up对象中的文件内容复制到destFile中，并关闭所有相关的资源，最终保存文件到本地文件系统中
        user_up.transferTo(new File(nameFinal));
        return "success";
    }

}
