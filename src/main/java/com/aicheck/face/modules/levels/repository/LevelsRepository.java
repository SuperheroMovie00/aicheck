package com.aicheck.face.modules.levels.repository;

import com.aicheck.face.modules.levels.entity.Levels;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelsRepository extends JpaRepository<Levels, Integer> {
	
}