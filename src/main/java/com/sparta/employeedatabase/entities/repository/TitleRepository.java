package com.sparta.employeedatabase.entities.repository;

import com.sparta.employeedatabase.entities.dto.Title;
import com.sparta.employeedatabase.entities.dto.TitleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, TitleId> {
}