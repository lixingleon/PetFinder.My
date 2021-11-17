package com.example.demo.Service;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import com.example.demo.Model.ComposedFile;
import com.example.demo.Model.FileChunk;
import com.example.demo.dao.ChunkFileDao;
import com.example.demo.dao.ComposedFileDao;
import jnr.ffi.annotations.Out;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class FileStorageService {
    private final static String UTF8 = "utf-8";
    private final Path root = Paths.get("uploads");
    String uploadPath = "uploads";
    private ChunkFileDao chunkFileDao;
    private ComposedFileDao composedFileDao;
    @Autowired
    public FileStorageService(ChunkFileDao chunkFileDao, ComposedFileDao composedFileDao){
        this.chunkFileDao = chunkFileDao;
        this.composedFileDao = composedFileDao;
    }

    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }



    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    public void writeChunk(int chunkIdx, int chunkNum, String name, String md5, MultipartFile file)  {
        //1. 创建目录
        String curFileName = chunkIdx+"_"+name;
        Path path = Paths.get(uploadPath, curFileName);
        OutputStream os = null;
        try{
            //2. 创建输出流
            os = Files.newOutputStream(path);
            //3. 把碎片文件写到指定目录
            os.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //创建对象，写到数据库中
        FileChunk chunk= new FileChunk();
        chunk.setChunkIdx(chunkIdx);
        chunk.setChunkNum(chunkNum);
        chunk.setName(curFileName);
        chunk.setPath(uploadPath);
        chunk.setMd5(md5);
        //存到数据库中
        chunkFileDao.save(chunk);

//
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//        //缓冲区大小。文件先写到缓冲区，再写到磁盘
//        factory.setSizeThreshold(1024);
//        //缓冲区路径
//        factory.setRepository(new File(uploadPath));
//        //解析request的工具类
//        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
//        servletFileUpload.setFileSizeMax(5L*1024L*1024L*1024L);
//        servletFileUpload.setSizeMax(10L*1024L*1024L*1024L);
//        //解析后得到一个FileItem类型的List
//        List<FileItem> fileItems = servletFileUpload.parseRequest(request);
//        for(FileItem item: fileItems){
//            System.out.println("first for loop");
//            if(item.isFormField()){
//                if("chunk".equals(item.getFieldName())){
//                    chunkIdx = Integer.parseInt(item.getString(UTF8));
//                }
//                if("chunks".equals(item.getFieldName())){
//                    chunkNum = Integer.parseInt(item.getString(UTF8));
//                }
//                if("name".equals(item.getFieldName())){
//                    name = item.getString(UTF8);
//                }
//                if("md5".equals(item.getFieldName())){
//                    md5 = item.getString(UTF8);
//                }
//            }
//        }
//        for(FileItem item: fileItems){
//            System.out.println("second for loop");
//            //如果这个item不是formfield，而是文件，就写入到临时目录
//            if(!item.isFormField()){
//                //chunk文件的名字
//                String curFileName = name;
//                //1. 文件名字不为null
//                if(name!= null){
//                    //2. 文件的chunkIdx不为null
//                    if(chunkIdx!= null){
//                        //3. 更新当前文件chunk的名字
//                        curFileName = chunkIdx+"_"+name;
//                    }
//                    //以当前chunk的名字和路径创建文件
//                    File curfile = new File(uploadPath, curFileName);
//                    //如果这个文件已经存在，则不再写入
//                    if(!curfile.exists()){
//                        //否则就写入uploads目录.
//                        //这里是断点续传的核心实现。
//                        item.write(curfile);
//                        FileChunk chunk= new FileChunk();
//                        chunk.setChunkIdx(chunkIdx);
//                        chunk.setChunkNum(chunkNum);
//                        chunk.setName(curFileName);
//                        chunk.setPath(uploadPath);
//                        chunk.setMd5(md5);
//                        //存到数据库中
//                        chunkFileDao.save(chunk);
//                    }
//                }
//
//            }
//        }
//        //至此把当前chunk已经写到临时目录里了。
//        //如果当前chunk是最后一个chunk，则开始chunk合并工作
//        if(chunkIdx!= null && chunkIdx == chunkNum-1){
//
//
//        }
//        response.getWriter().write(""+chunkIdx);
//        return response;



    }
    public void orderComposeFile(int chunkNum, String name, String md5) {
        //处理流，需要进一步学习记忆关于节点流和处理流的异同和常用IO类
        BufferedOutputStream os = null;
        try{
            System.out.println("if");
            File targetFile = new File(uploadPath, name);
            os = new BufferedOutputStream(new FileOutputStream(targetFile));

            for (int i = 0; i<chunkNum; i++){
                String chunkName = i+"_"+name;
                File chunkFile = new File(uploadPath, chunkName);
                //如果该chunk还没到达，则等待它到达
//                while(!chunkFile.exists()){
//                    Thread.sleep(100);
//                }
                //将文件读取到byte数组中
                byte[] bytes = FileUtils.readFileToByteArray(chunkFile);
                //将bytes数组提供给os写入流中
                os.write(bytes);
                os.flush();
                //把uploads目录里的临时文件删掉
                chunkFile.delete();
                //也把数据库的记录删掉
                chunkFileDao.deleteByMd5(md5);
            }
            os.flush();
            ComposedFile composedFile = new ComposedFile();
            composedFile.setMd5(md5);
            composedFile.setName(name);
            composedFile.setPath(uploadPath);
            //将composed file 记录保存到数据库
            composedFileDao.save(composedFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //当流不为null时，关闭这个流。关闭过程中处理可能出现的异常
            if (os!= null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
