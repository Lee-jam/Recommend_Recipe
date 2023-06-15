package com.refrigerator.springboot.repository;

import com.refrigerator.springboot.entity.Notify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotifyRepository extends JpaRepository<Notify, Long>, NotifyCustom {
	
	@Query("select n from Notify n order by notifiednum desc")
	List<Notify> findAllNotify();
	
	@Query("select count(n) from Notify n")
	int notifyingCount();
	
	@Query("select n from Notify n where n.writingid =:writingid and n.commentid is null")
	List<Notify> findByWritingId(@Param("writingid") Long writingid);
	
	@Query("select n from Notify n where n.commentid =:commentid")
	List<Notify> findByCommentId(@Param("commentid") Long commentid);

}
