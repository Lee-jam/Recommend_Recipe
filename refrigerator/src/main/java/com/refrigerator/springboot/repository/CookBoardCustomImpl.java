package com.refrigerator.springboot.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.refrigerator.springboot.dto.CookBoardDTO;
import com.refrigerator.springboot.dto.CookSearchDTO;
import com.refrigerator.springboot.dto.QCookBoardDTO;
import com.refrigerator.springboot.constant.Delcheck;
import com.refrigerator.springboot.entity.QCookImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class CookBoardCustomImpl implements CookBoardCustom {
	
	private JPAQueryFactory queryFactory;
	
	 public CookBoardCustomImpl(EntityManager em){
	        this.queryFactory = new JPAQueryFactory(em);
	    }

	 private BooleanExpression searchByLike(String searchBy, String searchQuery) {
			
		 if(StringUtils.equals("title", searchBy)) {
			 return QCookImage.cookImage.cookboard.title.like("%"+searchQuery+"%");
		 }else if(StringUtils.equals("nickName", searchBy)) {
			 return QCookImage.cookImage.cookboard.member.nickname.like("%"+searchQuery+"%");
		 }
		 return null;
	 }
	 
	@Override
	public Page<CookBoardDTO> findByDelCheckIsNAndSearch(CookSearchDTO cookSearchDTO, Pageable pageable) {
		List<CookBoardDTO> contents = queryFactory
				.select(new QCookBoardDTO(
						QCookImage.cookImage.cookboard.writingid,
						QCookImage.cookImage.cookboard.writingcount,
						QCookImage.cookImage, 
						QCookImage.cookImage.cookboard.title,
						QCookImage.cookImage.cookboard.member,
						QCookImage.cookImage.cookboard.regdate,
						QCookImage.cookImage.cookboard.seencount,
						QCookImage.cookImage.cookboard.recomcount))
				.from(QCookImage.cookImage)
				.join(QCookImage.cookImage.cookboard)
				.where(QCookImage.cookImage.repimage.like("Y"),QCookImage.cookImage.cookboard.delcheck.eq(Delcheck.N),searchByLike(cookSearchDTO.getSearchBy(), cookSearchDTO.getSearchQuery()))
				.orderBy(QCookImage.cookImage.cookboard.writingid.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		Long total = queryFactory
				.select(Wildcard.count)
				.from(QCookImage.cookImage)
				.join(QCookImage.cookImage.cookboard)
				.where(QCookImage.cookImage.repimage.like("Y"),QCookImage.cookImage.cookboard.delcheck.eq(Delcheck.N),searchByLike(cookSearchDTO.getSearchBy(), cookSearchDTO.getSearchQuery()))
				.fetchOne();
		
		return new PageImpl<>(contents, pageable, total);
				
	}

}
