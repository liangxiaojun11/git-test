package com.liang.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

public class UploadFile extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       if(!ServletFileUpload.isMultipartContent(req)){//判断请求是否为文件
           return;
       }
        String contextPath = this.getServletContext().getContextPath();
        System.out.println(contextPath);
        String uploadFile = this.getServletContext().getRealPath("/WEB-INF/upload");
        System.out.println(uploadFile);
        File file = new File(uploadFile);
        if (!file.exists()){
            file.mkdir();
        }
        //临时路径
        String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
        File temp = new File(tempPath);
        if (!temp.exists()){
            temp.mkdir();
        }
        //使用工具类
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(temp);
        factory.setSizeThreshold(1024*1024);

        ServletFileUpload fileUpload = new ServletFileUpload(factory);
        //处理文件
        //将前端请求解析成一个对象
        String msg;
        try {
            List<FileItem> fileItems = fileUpload.parseRequest(req);
            for (FileItem fileItem : fileItems) {
                if (!fileItem.isFormField()){//判断是普通表单还是带文件的表单
                    String fieldName = fileItem.getFieldName();
                    String string = fileItem.getString("utf-8");
                    System.out.println(fieldName+":"+string);
                }else{
                   String name = fileItem.getFieldName();
                   if (name.trim().equals("")||name==null){
                       continue;
                   }//获得文件名，和后缀名
                    String fileName = name.substring(name.lastIndexOf("/") + 1);
                    String fileExtName = name.substring(name.lastIndexOf(".") + 1);
                    System.out.println("fileName:"+fileName+"fileExtName:"+fileExtName);
                    //利用UUID生成地址
                    UUID uuidPath = UUID.randomUUID();
                    String realPath=uploadFile+"/"+uuidPath;//文件路径建在upload文件夹下
                    File realPathFile = new File(realPath);
                    if (!realPathFile.exists()){
                        realPathFile.mkdir();
                    }
                    //利用流读取传输
                    InputStream in = fileItem.getInputStream();
                    FileOutputStream out = new FileOutputStream(realPath+"/"+fileName);
                    byte[] buffer = new byte[1024*1024];
                    int len;
                    while ((len=in.read(buffer))!=-1){
                        out.write(buffer,0,len);
                    }
                    out.close();
                    in.close();
                    msg="上传成功！";
                    fileItem.delete();

                    //servlet请求转发信息
                    req.setAttribute("msg",msg);
                    req.getRequestDispatcher("info.jsp").forward(req,resp);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }
}
