package com.refrigerator.springboot.controller;

import com.refrigerator.springboot.dto.*;
import com.refrigerator.springboot.entity.Member;
import com.refrigerator.springboot.repository.MemberRepository;
import com.refrigerator.springboot.repository.NotifyRepository;
import com.refrigerator.springboot.service.BoardService;
import com.refrigerator.springboot.service.CookBoardService;
import com.refrigerator.springboot.service.NotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class NotifyingController {
	
	final NotifyService notifyService;
	final NotifyRepository notifyRepository;
	final MemberRepository memberRepository;
	final BoardService boardService;
	final CookBoardService cookBoardService;

	@PostMapping("/Notifying")
	public String notifying(Principal principal, Model model, @ModelAttribute NotifyingDTO notifyingDTO) {
		//String email = principal.getName();
		String email = "test@naver.com";
		Member member = memberRepository.findByEmail(email);
		model.addAttribute("member", member);
		model.addAttribute("notifyingDTO", notifyingDTO);
		return"community/Notifying";
	}
	
	@PostMapping("/doNotifying")
	public @ResponseBody ResponseEntity<String> doNotifying(Principal principal, DoNotifyingDTO doNotifyingDTO){
		//String email = principal.getName();
		String email = "test@naver.com";
		notifyService.doNotifying(doNotifyingDTO,email);
		
		return ResponseEntity.ok("ok");
		
	}
	
	@GetMapping("/NotifiedList")
	public String notifiedList(Principal principal, Model model, Optional<Integer> page) {
		try {
			Pageable pageable = PageRequest.of(page.isPresent() ? page.get():0,20);
			Page<NotifyListDTO> notify = notifyRepository.getNotifyList(pageable);
			if(notify.getNumberOfElements()==0) {
				model.addAttribute("nonWriting", "등록된 게시물이 없습니다");
			}	
		model.addAttribute("notifies", notify);
		model.addAttribute("maxPage",5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//List<Notify> notify = notifyRepository.findAllNotify();
		return"community/notifiedList";
	}
	
	@PostMapping("/MemBannAndContentDel")
	public @ResponseBody ResponseEntity<String> MemBannAndContentDel(@ModelAttribute MemBannDTO memBannDTO){
		Long boardCheck = memBannDTO.getBoardId();
		String id = memBannDTO.getMemMail();
		Long writingId = memBannDTO.getWritingId();
		Long commentId = memBannDTO.getCommentId();
		String removeDate = memBannDTO.getSelectDate();
		if(boardCheck.equals(1l)) {
			boardService.deleteUpdate(id,writingId);
			notifyService.changeBann(id, writingId, commentId,removeDate);
		}else if(boardCheck.equals(2l)) {
			cookBoardService.deleteWriting(id, writingId);
			notifyService.changeBann(id, writingId, commentId,removeDate);
		}
		
		return ResponseEntity.ok("MemBannAndContentDel");

	}
	
	@PostMapping("/MemBannAndCommentDel")
	public @ResponseBody ResponseEntity<String> MemBannAndCommentDel(@ModelAttribute MemBannDTO memBannDTO){
		
		System.out.println(memBannDTO);
		System.out.println("쳌쳌");
		Long boardCheck = memBannDTO.getBoardId();
		String id = memBannDTO.getMemMail();
		Long writingId = memBannDTO.getWritingId();
		Long commentId = memBannDTO.getCommentId();
		String removeDate = memBannDTO.getSelectDate();
		if(boardCheck.equals(1l)) {
			boardService.deleteComment(commentId, id);
			notifyService.changeBann(id, writingId, commentId,removeDate);
		}else if(boardCheck.equals(2l)) {
			System.out.println("체크임니당");
			cookBoardService.deleteComment(commentId, id);
			notifyService.changeBann(id, writingId, commentId,removeDate);
		}
		
		return ResponseEntity.ok("MemBannAndCommentDel");

	}
	
	@PostMapping("/notifyingCheck")
	public String notifyingCheck(NotifyingCheckDTO notifyingCheckDTO, Model model) {
		model.addAttribute("notifyingCheckDTO", notifyingCheckDTO);
		return"community/NotifyingCheck";
	}
	
}
