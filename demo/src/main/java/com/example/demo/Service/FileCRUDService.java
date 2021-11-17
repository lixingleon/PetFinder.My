package com.example.demo.Service;

import com.example.demo.Model.ComposedFile;
import com.example.demo.Model.FileChunk;
import com.example.demo.dao.ChunkFileDao;
import com.example.demo.dao.ComposedFileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileCRUDService {
    private ChunkFileDao chunkFileDao;
    private ComposedFileDao composedFileDao;
    private final Path root = Paths.get("uploads");
    @Autowired
    public FileCRUDService(ChunkFileDao chunkFileDao, ComposedFileDao composedFileDao){
        this.chunkFileDao = chunkFileDao;
        this.composedFileDao = composedFileDao;
    }
    public List<ComposedFile> getComposedFiles(){
        return composedFileDao.findAll();
    }
    public void deleteFileById(Long id){
        composedFileDao.deleteById(id);
    }
    public void deleteFileByName(String name)  {
        Path path = root.resolve(name);
        try {
            FileSystemUtils.deleteRecursively(path);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public boolean checkFile(String md5){
        ComposedFile composedFile = composedFileDao.findByMd5(md5);
        System.out.println("composedfile"+composedFile);
        if(composedFile == null){
            return false;
        }
        return true;
    }
    public boolean checkChunk(int index, String md5){
        FileChunk fileChunk = chunkFileDao.findByIndexAndMd5(index, md5);
        if(fileChunk == null){
            return false;
        }
        return true;
    }
}
