package com.refrigerator.springboot.service;

import org.springframework.stereotype.Service;

import com.refrigerator.springboot.entity.Board;
import com.refrigerator.springboot.entity.Member;
import com.refrigerator.springboot.entity.RecipeBoard;
import com.refrigerator.springboot.entity.RecipeComment;
import com.refrigerator.springboot.repository.MemberRepository;
import com.refrigerator.springboot.repository.RecipeBoardRepository;
import com.refrigerator.springboot.repository.RecipeCommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeCommentService {

	final RecipeCommentRepository recipeCommentRepository;
	final RecipeBoardRepository recipeBoardRepository;
	final MemberRepository memberRepository;

	public void writeComment(Board board, Long writingId, String comment, String email) {
		RecipeBoard recipeBoard = recipeBoardRepository.findByRecipeDetail(writingId);
		Member member = memberRepository.findByEmail(email);
		RecipeComment recipeComment = RecipeComment.createRecipeComment(comment, recipeBoard, member, board);
		recipeCommentRepository.save(recipeComment);
	}

}
