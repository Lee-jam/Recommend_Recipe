package com.refrigerator.springboot.repository;



import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.refrigerator.springboot.dto.QShowBoardDTO;
import com.refrigerator.springboot.dto.RecipeSearchDTO;
import com.refrigerator.springboot.dto.ShowBoardDTO;
import com.refrigerator.springboot.constant.Delcheck;
import com.refrigerator.springboot.entity.QRecipeImage;

public class RecipeBoardCustomImpl implements RecipeBoardCustom{

	private JPAQueryFactory queryFactory;

	public RecipeBoardCustomImpl(EntityManager em){
		this.queryFactory = new JPAQueryFactory(em);
	}

	private BooleanExpression searchByLike(String searchBy, String searchQuery) {

		if(StringUtils.equals("title", searchBy)) {
			return QRecipeImage.recipeImage.recipeboard.title.like("%"+searchQuery+"%");
		}else if(StringUtils.equals("nickName", searchBy)) {
			return QRecipeImage.recipeImage.recipeboard.member.nickname.like("%"+searchQuery+"%");
		}
		return null;
	}

	@Override
	public Page<ShowBoardDTO> findByDelCheckIsNAndSearch(RecipeSearchDTO recipeSearchDTO, Pageable pageable) {
		List<ShowBoardDTO> contents = queryFactory
				.select(new QShowBoardDTO(
						QRecipeImage.recipeImage.recipeboard.writingid,
						QRecipeImage.recipeImage.recipeboard.writingcount,
						QRecipeImage.recipeImage,
						QRecipeImage.recipeImage.recipeboard.title,
						QRecipeImage.recipeImage.recipeboard.difficulty,
						QRecipeImage.recipeImage.recipeboard.member,
						QRecipeImage.recipeImage.recipeboard.regdate,
						QRecipeImage.recipeImage.recipeboard.seencount,
						QRecipeImage.recipeImage.recipeboard.recomcount))
				.from(QRecipeImage.recipeImage)
				.join(QRecipeImage.recipeImage.recipeboard)
				.where(QRecipeImage.recipeImage.repimage.like("Y"),QRecipeImage.recipeImage.recipeboard.delcheck.eq(Delcheck.N),searchByLike(recipeSearchDTO.getSearchBy(), recipeSearchDTO.getSearchQuery()))
				.orderBy(QRecipeImage.recipeImage.recipeboard.writingid.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();

		Long total = queryFactory
				.select(Wildcard.count)
				.from(QRecipeImage.recipeImage)
				.join(QRecipeImage.recipeImage.recipeboard)
				.where(QRecipeImage.recipeImage.repimage.like("Y"), QRecipeImage.recipeImage.recipeboard.delcheck.eq(Delcheck.N),searchByLike(recipeSearchDTO.getSearchBy(), recipeSearchDTO.getSearchQuery()))
				.fetchOne();
		System.out.println(total);
		return new PageImpl<>(contents, pageable, total);
	}

}
