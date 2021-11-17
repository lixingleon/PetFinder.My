package com.example.demo.Model;

import javax.persistence.*;

@Entity
@Table(name="FileChunk")
public class FileChunk {

    public FileChunk(){

    }

    public FileChunk(String name, String path, int chunkIdx, int chunkNum, String md5) {
        this.name = name;
        this.path = path;
        this.chunkIdx = chunkIdx;
        this.chunkNum = chunkNum;
        this.md5 = md5;
    }

    //id
    //name
    //path
    //chunkIdx
    //chunkNum
    //md5
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "name")
    private String name;
    @Column(nullable = false, name = "path")
    private String path;
    @Column(nullable = false, name = "chunkIdx")
    private int chunkIdx;
    @Column(nullable = false, name = "chunkNum")
    private int chunkNum;
    @Column(nullable = false, name = "md5")
    private String md5;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getChunkIdx() {
        return chunkIdx;
    }

    public void setChunkIdx(int chunkIdx) {
        this.chunkIdx = chunkIdx;
    }

    public int getChunkNum() {
        return chunkNum;
    }

    public void setChunkNum(int chunkNum) {
        this.chunkNum = chunkNum;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

}
