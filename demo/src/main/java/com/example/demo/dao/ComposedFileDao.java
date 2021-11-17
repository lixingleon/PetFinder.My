package com.example.demo.dao;

import com.example.demo.Model.ComposedFile;
import com.example.demo.Model.ComposedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ComposedFileDao extends JpaRepository<ComposedFile, Long> {
    ComposedFile findByMd5(String md5);
}
