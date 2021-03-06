package com.example.demo.Service;
import java.io.*;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.example.demo.Model.ComposedFile;
import com.example.demo.Model.FileChunk;
import com.example.demo.dao.ChunkFileDao;
import com.example.demo.dao.ComposedFileDao;
import com.opencsv.CSVReader;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;


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
        //1. ????????????
        String curFileName = chunkIdx+"_"+name;
        Path path = Paths.get(uploadPath, curFileName);
        OutputStream os = null;
        try{
            //2. ???????????????
            os = Files.newOutputStream(path);
            //3. ?????????????????????????????????
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
        //?????????????????????????????????
        FileChunk chunk= new FileChunk();
        chunk.setChunkIdx(chunkIdx);
        chunk.setChunkNum(chunkNum);
        chunk.setName(curFileName);
        chunk.setPath(uploadPath);
        chunk.setMd5(md5);
        //??????????????????
        chunkFileDao.save(chunk);



    }
    public void  orderComposeFile(int chunkNum, String name, String md5) {
        //????????????????????????????????????????????????????????????????????????????????????IO???
        BufferedOutputStream os = null;
        try{
            System.out.println("if");
            File targetFile = new File(uploadPath, name);
            os = new BufferedOutputStream(new FileOutputStream(targetFile));

            for (int i = 0; i<chunkNum; i++){
                String chunkName = i+"_"+name;
                File chunkFile = new File(uploadPath, chunkName);
                //?????????chunk?????????????????????????????????
//                while(!chunkFile.exists()){
//                    Thread.sleep(100);
//                }
                //??????????????????byte?????????
                byte[] bytes = FileUtils.readFileToByteArray(chunkFile);
                //???bytes???????????????os????????????
                os.write(bytes);
                os.flush();
                //???uploads??????????????????????????????
                chunkFile.delete();
                //??????????????????????????????
                chunkFileDao.deleteByMd5(md5);
            }
            os.flush();
            ComposedFile composedFile = new ComposedFile();
            composedFile.setMd5(md5);
            composedFile.setName(name);
            composedFile.setPath(uploadPath);
            //???composed file ????????????????????????
            composedFileDao.save(composedFile);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //????????????null??????????????????????????????????????????????????????????????????
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
