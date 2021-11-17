package com.example.demo.dao;

import com.example.demo.Model.FileChunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

public interface ChunkFileDao extends JpaRepository<FileChunk, Long> {
    @Transactional
    Long deleteByMd5(String md5);

    @Transactional
    @Query(value = "select * from file_chunk where chunk_idx = :index and md5= :md5", nativeQuery = true)
    FileChunk findByIndexAndMd5(@Param("index") int index, @Param("md5") String md5);
}
