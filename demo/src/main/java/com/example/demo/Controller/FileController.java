package com.example.demo.Controller;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.Model.ComposedFile;
import com.example.demo.Model.FileInfo;
import com.example.demo.Service.FileCRUDService;
import com.example.demo.Service.FileStorageService;
import com.example.demo.message.ResponseMessage;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@CrossOrigin("http://localhost:3000")
public class FileController {
//    private final static String UTF8 = "utf-8";
    private FileStorageService storageService;
    private FileCRUDService fileCRUDService;
    @Autowired
    public FileController(FileStorageService storageService, FileCRUDService fileCRUDService){
        this.storageService = storageService;
        this.fileCRUDService = fileCRUDService;
    }
    @GetMapping("/api/checkFile")
    public ResponseEntity<Boolean> checkFile(@RequestParam String md5){
        boolean result = fileCRUDService.checkFile(md5);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @GetMapping("/api/checkChunk")
    public ResponseEntity<Boolean> checkChunk(@RequestParam int index, String md5){
        boolean result = fileCRUDService.checkChunk(index, md5);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/api/delete")
    public ResponseEntity<Boolean> deleteFileById(@RequestParam Long id, String name){
        try{
            fileCRUDService.deleteFileById(id);
            fileCRUDService.deleteFileByName(name);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        catch (Exception e){
            System.out.println(e);
        }

        return ResponseEntity.status(HttpStatus.OK).body(false);
    }

    @GetMapping("/api/Files")
    public ResponseEntity<List<ComposedFile>> getComposedFiles(){
        List<ComposedFile> composedFiles = fileCRUDService.getComposedFiles();
        return ResponseEntity.status(HttpStatus.OK).body(composedFiles);
    }
//    @PostMapping("/upload")
//    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
//        String message = "";
//        try {
//            storageService.save(file);
//
//            message = "Uploaded the file successfully: " + file.getOriginalFilename();
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
//        } catch (Exception e) {
//            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
//        }
//    }




    @RequestMapping(value = "/uploadChunkedFile", method = RequestMethod.POST)
    @ResponseBody
    public void uploadChunkedFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("chunk") int chunk,
            @RequestParam("chunks") int chunks,
            @RequestParam("name") String name,
            @RequestParam("md5") String md5
    ) {



        storageService.writeChunk(chunk, chunks, name, md5, file);

    }
    @RequestMapping(value = "/orderComposeFile", method = RequestMethod.POST)
    @ResponseBody
    public void  orderCompose( @RequestParam("chunks") int chunks,
                              @RequestParam("name") String name,
                              @RequestParam("md5") String md5
                            ){
        storageService.orderComposeFile(chunks, name, md5);

    }



//    @GetMapping("/files")
//    public ResponseEntity<List<FileInfo>> getListFiles() {
//        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
//            String filename = path.getFileName().toString();
//            String url = MvcUriComponentsBuilder
//                    .fromMethodName(FileController.class, "getFile", path.getFileName().toString()).build().toString();
//
//            return new FileInfo(filename, url);
//        }).collect(Collectors.toList());
//
//        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
//    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
