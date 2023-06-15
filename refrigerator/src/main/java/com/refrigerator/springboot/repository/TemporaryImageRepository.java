package com.refrigerator.springboot.repository;

import com.refrigerator.springboot.entity.TemporaryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TemporaryImageRepository extends JpaRepository<TemporaryImage, Long> {

	@Query("select t.url from TemporaryImage t where t.imageid = :maxcount")
	String findByMaxId(@Param("maxcount") Long maxcount);
	
	@Query("select Max(t.imageid) from TemporaryImage t")
	Long findMaxCount();
	
	@Query("select t from TemporaryImage t where t.oriname = :oriname")
	List<TemporaryImage>findByOriName(@Param("oriname")String oriname);
	
}
