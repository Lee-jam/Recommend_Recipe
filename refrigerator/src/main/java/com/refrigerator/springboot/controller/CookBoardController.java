package com.refrigerator.springboot.controller;

import com.refrigerator.springboot.dto.*;
import com.refrigerator.springboot.entity.Board;
import com.refrigerator.springboot.entity.Member;
import com.refrigerator.springboot.repository.BoardRepository;
import com.refrigerator.springboot.repository.CookBoardRepository;
import com.refrigerator.springboot.repository.MemberRepository;
import com.refrigerator.springboot.repository.TemporaryImageRepository;
import com.refrigerator.springboot.service.CookBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("board/cook")
public class CookBoardController {
	
	final MemberRepository memberRepository;
	final CookBoardService cookBoardService;
	final BoardRepository boardRepository;
	final CookBoardRepository cookBoardRepository;
	final TemporaryImageRepository temporaryImageRepository;

	@GetMapping("/CookWriting")
	public String cookWriting(Model model, Principal principal, CookWritingDTO cookWritingDTO) {
		//String email = principal.getName();
		String email = "test@naver.com";
		Member member = memberRepository.findByEmail(email);
		Board board = boardRepository.findByBoardid(2l);
		model.addAttribute("member", member);
		model.addAttribute("board", board);
		return"community/cookWriting";
	}
	@PostMapping("/doCookWriting")
	public String doCookWriting(Model model, Principal principal, CookWritingDTO cookWritingDTO, @RequestParam("images") List<MultipartFile>images) {
		//String email = principal.getName();
		String email = "test@naver.com";
		Board board = boardRepository.findByBoardid(2l);
		Member member = memberRepository.findByEmail(email);
		if(cookWritingDTO.getTitle().trim().equals("")) {
			model.addAttribute("member", member);
			model.addAttribute("board", board);
			model.addAttribute("ErrorMessage", "제목을 입력해주세요");
			return"community/ErrorPage";
		}
		if(cookWritingDTO.getContent().trim().equals("<p><br></p>")) {
			model.addAttribute("member", member);
			model.addAttribute("board", board);
			model.addAttribute("ErrorMessage", "내용을 입력해주세요");
			return"community/ErrorPage";
		}
		System.out.println("cookWritingDTO");
		System.out.println(cookWritingDTO);
		cookBoardService.doCookWriting(cookWritingDTO, member, board, images);
		return"redirect:/";
	}
	@PostMapping("/doCookImageSet")
	public @ResponseBody ResponseEntity<String> imageSet(@ModelAttribute FormDataDTO formData) {
		MultipartFile image = formData.getImage();
		cookBoardService.saveTemporaryImage(image);
		Long maxCount = temporaryImageRepository.findMaxCount();
		String imageUrl = temporaryImageRepository.findByMaxId(maxCount);
		System.out.println("체크에용");
		System.out.println(imageUrl);
		return ResponseEntity.ok(imageUrl);
	}
	
	@GetMapping("/")
	public String CookBoard(Model model, Optional<Integer> page, CookSearchDTO cookSearchDTO, Principal principal) {
		try {
			//String email = principal.getName();
			String email = "test@naver.com";
			Member member = memberRepository.findByEmail(email);
			Pageable pageable = PageRequest.of(page.isPresent()? page.get() :0,8);
			Page<CookBoardDTO> cookBoards = cookBoardRepository.findByDelCheckIsNAndSearch(cookSearchDTO, pageable);
			if(cookBoards.getNumberOfElements()==0) {
				model.addAttribute("nonWriting", "등록된 게시물이 없습니다");
			}
			model.addAttribute("member", member);
			model.addAttribute("cookBoards", cookBoards);
			model.addAttribute("maxPage", 5);
			model.addAttribute("cookSearchDTO", cookSearchDTO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "community/CookBoard";
	}
	
	@GetMapping("/CookBoardDetail")
	public String cookBoardDetail(Model model, Principal principal, @RequestParam("writingId")Long writingId) {
		//String email = principal.getName();
		String email = "test@naver.com";
		Member member = memberRepository.findByEmail(email);
		Board board = boardRepository.findByBoardid(2l);
		CookDetailViewDTO cookDetailViewDTO = cookBoardService.getDetailBoard(writingId);
		cookBoardService.updateSeenCount(cookDetailViewDTO);
		model.addAttribute("board", board);
		model.addAttribute("cookDetailViewDTO", cookDetailViewDTO);
		model.addAttribute("member", member);
		return"community/CookBoardDetail";
	}
	
	@PostMapping("/CookCommentWriting/{writingId}")
	public @ResponseBody ResponseEntity cookCommentWriting(Principal principal, @PathVariable("writingId") Long writingId, @RequestParam("comment")String content){
		//String email = principal.getName();
		String email = "test@naver.com";
		Board board = boardRepository.findByBoardid(2l);
		cookBoardService.createComment(writingId, email, content, board);
		
		
		return new ResponseEntity<Long>(writingId, HttpStatus.OK);
	}
	
	@PostMapping("/doCookRecom/{writingId}")
	public @ResponseBody ResponseEntity<String> doCookRecom(Principal principal, @PathVariable("writingId")Long writingId) {
		//String email = principal.getName();
		String email = "test@naver.com";
		boolean recom = cookBoardService.doCookRecom(email, writingId);
		System.out.println(recom);
		System.out.println("체크2");
		if(recom==true) {
			return ResponseEntity.ok("true");
		}else {
			return ResponseEntity.ok("false");
		}
	}
	
	@GetMapping("/CookBoardDetail/delete")
	public String delCook(Principal principal, @RequestParam("writingId")Long writingId) {
		//String email = principal.getName();
		String email = "test@naver.com";
		cookBoardService.deleteWriting(email, writingId);
		return"redirect:/";
	}
	
	@GetMapping("/CookBoardDetail/rewrite")
		public String rewriteCook(Principal principal, @RequestParam("writingId")Long writingId, Model model) {
		//String email = principal.getName();
		String email = "test@naver.com";
			Member member = memberRepository.findByEmail(email);
			CookWritingDTO cookWritingDTO = cookBoardService.getCookRewrite(writingId);
			System.out.println(cookWritingDTO.getContent());
			model.addAttribute("writingId", writingId);
			model.addAttribute("member", member);
			model.addAttribute("cookWritingDTO", cookWritingDTO);
			return "community/cookRewriting";
		}
	@PostMapping("/doCookreWriting/{writingId}")
		public String doRewriteCook(Principal principal, @PathVariable("writingId") Long writingId, CookWritingDTO cookWritingDTO, List<MultipartFile> images, @RequestParam("repUrl")String repUrl) {
		System.out.println(repUrl);
		System.out.println("체크임니당");
		//String email = principal.getName();
		String email = "test@naver.com";
		Member member = memberRepository.findByEmail(email);
		cookBoardService.Rewrite(writingId ,cookWritingDTO, images, repUrl);
		return"redirect:/CookBoardDetail?writingId="+writingId;
	}
	
	@PostMapping("/CookCommentReWriting/{commentId}")
		public @ResponseBody ResponseEntity<Long> commentReWriting(Principal principal,@PathVariable("commentId")Long commentId, @RequestParam("content")String content) {
		//String email = principal.getName();
		String email = "test@naver.com";
		Member member = memberRepository.findByEmail(email);
		cookBoardService.reWriteComment(commentId, content);
		return ResponseEntity.ok(commentId);
	}
	@PostMapping("/CookCommentDelete/{commentId}")
	public @ResponseBody ResponseEntity<Long> commentDelete(Principal principal, @PathVariable("commentId")Long commentId){
		//String email = principal.getName();
		String email = "test@naver.com";
		Member member = memberRepository.findByEmail(email);
		boolean chekck = cookBoardService.deleteComment(commentId, email);
		return ResponseEntity.ok(commentId);
	}
	
	@PostMapping("/removeTemporalImage")
	public @ResponseBody ResponseEntity<String> removeTemporalImage(@RequestParam("oriName")String oriName){
		cookBoardService.removeTemporalImage(oriName);
		return ResponseEntity.ok("result");
	}
	
}
