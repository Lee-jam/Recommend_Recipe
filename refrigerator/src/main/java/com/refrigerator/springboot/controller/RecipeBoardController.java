package com.refrigerator.springboot.controller;

import com.refrigerator.springboot.dto.*;
import com.refrigerator.springboot.entity.Board;
import com.refrigerator.springboot.entity.Member;
import com.refrigerator.springboot.entity.RecipeComment;
import com.refrigerator.springboot.repository.*;
import com.refrigerator.springboot.service.BoardService;
import com.refrigerator.springboot.service.RecipeCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class RecipeBoardController {
	
	
	final BoardService boardService;
	final RecipeCommentService recipeCommentService;
	final BoardRepository boardRepository;
	final MemberRepository memberRepository;
	final RecipeBoardRepository recipeBoardRepository;
	final RecipeContentRepository recipeContentRepository;
	final RecipeImageRepository recipeImageRepository;
	final RecipeCommentRepository recipecommentRepository;
	final RecipeFollow recipeFollow;
	
	@GetMapping("/recipeWrite")
	public String letsWrite(Model model, WriteFormDTO writeFormDTO, Principal principal) {
		
		Board board = boardRepository.findByBoardid(1l);
		//String email = principal.getName();
		String email = "test@naver.com";
		Member member = memberRepository.findByEmail(email);
		model.addAttribute("board",board);
		model.addAttribute("member",member);
		
		return "board/recipe/writing";
		
	}
	
	@PostMapping("/doRecipeWrite")
	public String doRecipeWrite(WriteFormDTO writeFormDTO, BindingResult bindingResult, Principal principal, @RequestParam("recipeImages") List<MultipartFile> recipeImages) {
		System.out.println(writeFormDTO);
		System.out.println(recipeImages);
		if(bindingResult.hasErrors()) {
			return "board/community/writing";
		}
		String recipeContent = writeFormDTO.getRecipeContent();
		//String email = principal.getName();
		String email = "test@naver.com";
		Long boardId = 1l;
		boardService.Recipewriting(writeFormDTO, email, boardId, recipeContent, recipeImages);
		return "redirect:board/";
	}
	
	@GetMapping("/")
	public String recipeBoard(Model model, Optional<Integer> page, RecipeSearchDTO recipeSearchDTO,Principal principal) {
		try {
			Pageable pageable = PageRequest.of(page.isPresent()? page.get() :0,20);
			Page<ShowBoardDTO> recipeBoards = recipeBoardRepository.findByDelCheckIsNAndSearch(recipeSearchDTO, pageable);
			//String email = principal.getName();
			String email = "test@naver.com";
			Member member = memberRepository.findByEmail(email);
			if(recipeBoards.getNumberOfElements()==0) {
				model.addAttribute("nonWriting", "등록된 게시물이 없습니다");
			}
			model.addAttribute("member", member);
			model.addAttribute("recipeBoards", recipeBoards);
			model.addAttribute("now", LocalDateTime.now());
			model.addAttribute("recipeSearchDTO", recipeSearchDTO);
			model.addAttribute("maxPage",5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "board/recipe/recipeBoard";
	}
	@GetMapping("/recipeView")
	public String recipeBoardDetail(@RequestParam("writingId")Long writingId, Model model, Principal principal, WriteCommentDTO writeCommentDTO) {
		
		//String email = principal.getName();
		String email = "test@naver.com";
		Member member = memberRepository.findByEmail(email);
		Board board = boardRepository.findByBoardid(1l);
		DetailViewDTO detailViewDTO = boardService.getDetailView(writingId);
		boardService.updateSeenCount(detailViewDTO);
		Integer recipeFollowWriting = recipeFollow.findFollowing(member, detailViewDTO.getRecipeBoard());
		model.addAttribute("Following", recipeFollowWriting);
		model.addAttribute("member", member);
		model.addAttribute("detailViewDTO", detailViewDTO);
		model.addAttribute("board", board);
		
		List<RecipeComment> comments = detailViewDTO.getRecipeComments();
		for(RecipeComment comment : comments) {
			System.out.println(comment.getCommentid() instanceof Long);
		}
		
		return "board/recipe/recipeBoardDetail";
		
	}
	@PostMapping("/recipeCommentWriting/{writingId}")
	public @ResponseBody ResponseEntity<Long> recipeCommentWriting(Principal principal, @PathVariable("writingId")Long writingId, @RequestParam("comment") String comment) {
		//String email = principal.getName();
		String email = "test@naver.com";
		Board board = boardRepository.findByBoardid(1l);
		recipeCommentService.writeComment(board, writingId, comment, email);
		return ResponseEntity.ok(writingId);
	}
	@GetMapping("/recipeView/delete")
	public String delete(@RequestParam("writingId")Long writingId, Principal principal,Model model) {
//		RecipeBoard recipeBoard = recipeBoardRepository.findByRecipeDetail(writingId);
//		if(recipeBoard.getMember().getMemId()!=principal.getName()) {
//			String ErrorMessage ="계정이 올바르지 않습니다.";
//			model.addAttribute("ErrorMessage", ErrorMessage);
//			return "redirect:/recipeView?writingId="+writingId;
//		}
//계정체크입니다. 추후 해제 바람
		//String email = principal.getName();
		String email = "test@naver.com";
		boardService.deleteUpdate(email,writingId);
		return "redirect:/recipeBoard";
	}
	@GetMapping("/recipeView/rewrite")
	public String rewrite(@RequestParam("writingId")Long writingId, Principal principal, Model model) {
//		RecipeBoard recipeBoard = recipeBoardRepository.findByRecipeDetail(writingId);
//		if(recipeBoard.getMember().getMemId()!=principal.getName()) {
//			String ErrorMessage ="계정이 올바르지 않습니다.";
//			model.addAttribute("ErrorMessage", ErrorMessage);
//			return "redirect:/recipeView?writingId="+writingId;
//		}
//계정체크입니다. 추후 해제 바람
		//String email = principal.getName();
		String email = "test@naver.com";
		Member member = memberRepository.findByEmail(email);
		RewriteDTO rewriteDTO = boardService.getRewriteValue(writingId);
		model.addAttribute("rewriteDTO", rewriteDTO);
		model.addAttribute("member", member);

		return "community/recipeRewriting";
	}

	@PostMapping("/doRecipeReWrite")
	public String doRecipeReWrite(WriteFormDTO writeFormDTO, Principal principal, @RequestParam("recipeImages") List<MultipartFile> recipeImages, @RequestParam("writingId")Long writingId, @RequestParam("imgdelCheck")String imgdelCheck) {
		String recipeContent = writeFormDTO.getRecipeContent();
		//String email = principal.getName();
		String email = "test@naver.com";
		Long boardId = 1l;
		boardService.RecipeRewriting(writeFormDTO, email, boardId, recipeContent, recipeImages, writingId, imgdelCheck);
		return "redirect:/recipeBoard";
	}

	@PostMapping("/updateRecomCount/{writingId}")
	public @ResponseBody ResponseEntity<String> updateRecomCount(Principal principal, @PathVariable("writingId") Long writingId) {
		//String email = principal.getName();
		String email = "test@naver.com";
		Member member = memberRepository.findByEmail(email);
		DetailViewDTO detailViewDTO = boardService.getDetailView(writingId);
		boolean updateRecom = boardService.updateRecomCount(detailViewDTO, member);
		if(updateRecom==true) {
			return ResponseEntity.ok("true");
		}else {
			return ResponseEntity.ok("false");
		}
	}

	@PostMapping("/updateFollow/{writingId}")
	public @ResponseBody ResponseEntity updateFollow(@PathVariable("writingId") Long writingId, Principal principal) {
		//String email = principal.getName();
		String email = "test@naver.com";
		boardService.updateFollow(email, writingId);
		return new ResponseEntity<Long>(writingId, HttpStatus.OK);
	}

	@PostMapping("/recipeCommentDelete/{commentId}")
	public @ResponseBody ResponseEntity<String> deleteRecCom(Principal principal, @PathVariable("commentId") Long commentId){
		//String email = principal.getName();
		String email = "test@naver.com";
		boolean deleteComment = boardService.deleteComment(commentId, email);
		if(deleteComment==true) {
			return ResponseEntity.ok("true");
		}else {
			return ResponseEntity.ok("false");
		}
	}

	@PostMapping("/recipeCommentResend/{commentId}")
	public @ResponseBody ResponseEntity<Long> recipeReComment(Principal principal, @PathVariable("commentId")Long commentId, @RequestParam("content")String comment){
		//String email = principal.getName();
		String email = "test@naver.com";
		Member member = memberRepository.findByEmail(email);
		boardService.reWriteComment(commentId, comment);
		return ResponseEntity.ok(commentId);
	}

	@PostMapping("/randomRecipeSave")
	public String randomRecipeWrite(Principal principal){
		if(principal==null){
			return "redirect:/loginCheck";
		}

		return "redirect:board/recipeWrite";
	}

}
