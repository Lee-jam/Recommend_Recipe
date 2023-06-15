package com.refrigerator.springboot.service;


import com.refrigerator.springboot.dto.CookDetailViewDTO;
import com.refrigerator.springboot.dto.CookWritingDTO;
import com.refrigerator.springboot.constant.Delcheck;
import com.refrigerator.springboot.entity.*;
import com.refrigerator.springboot.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CookBoardService {
	
	final CookBoardRepository cookBoardRepository;
	final FileService fileService;
	final CookImageRepository cookImageRepository;
	final CookCommentRepository cookCommentRepository;
	final MemberRepository memberRepository;
	final TemporaryImageRepository temporaryImageRepository;
	final CookRecommendRepository cookRecommendRepository;
	
	public void doCookWriting(CookWritingDTO cookWritingDTO, Member member, Board board, List<MultipartFile> images) {
		Integer writingCount = cookBoardRepository.findByAllWriting();
		if(writingCount==null) {
			writingCount=1;
		}else if(writingCount!=null) {
			writingCount = cookBoardRepository.findByAllWriting()+1;
		}
		CookBoard cookBoard = CookBoard.createCookWriting(cookWritingDTO, member, board);
		cookBoardRepository.save(cookBoard);
		for(int i=0; i<1; i++) {
			CookImage cookImage = new CookImage();
			cookImage.setCookboard(cookBoard);
			cookImage.setRepimage("Y");
			MultipartFile image = images.get(i);
			this.saveCookImage(cookImage, image);
		}
	}

	private void saveCookImage(CookImage cookImage, MultipartFile image) {
		String uploadPath = "C:/storage/project";
		String oriName = image.getOriginalFilename();
		String imgName = "";
		String url = "";
		try {
			if(!StringUtils.isEmpty(oriName)) {
				imgName = fileService.uploadFile(uploadPath, oriName, image.getBytes());
				url="/images/project/"+imgName;
			}
			cookImage.saveCookImage(imgName, oriName, url);
			cookImageRepository.save(cookImage);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public CookDetailViewDTO getDetailBoard(Long writingId) {
		CookDetailViewDTO cookDetailViewDTO = new CookDetailViewDTO();
		CookBoard cookBoard = cookBoardRepository.findByWritingId(writingId);
		List<CookImage> cookImages = cookImageRepository.findByCookBoard(cookBoard);
		List<CookComment> cookComments = cookCommentRepository.findByCookBoard(cookBoard);
		cookDetailViewDTO.setCookBoard(cookBoard);
		cookDetailViewDTO.setCookImage(cookImages);
		cookDetailViewDTO.setCookComment(cookComments);
		return cookDetailViewDTO;
	}

	public void createComment(Long writingId, String email, String content, Board board) {
		Member member = memberRepository.findByEmail(email);
		CookBoard cookBoard = cookBoardRepository.findByWritingId(writingId);
		CookComment cookComment = CookComment.createookComment(member, cookBoard, content, board);
		cookCommentRepository.save(cookComment);
		
	}
	
	public void saveTemporaryImage(MultipartFile Image) {
		TemporaryImage temporaryImage = new TemporaryImage();
		String uploadPath = "C:/storage/project";
		String oriName = Image.getOriginalFilename();
		String imgName = "";
		String url = "";
		try {
			if(!StringUtils.isEmpty(oriName)) {
				imgName = fileService.uploadFile(uploadPath, oriName, Image.getBytes());
				url="/images/project/"+imgName;
			}
			
			temporaryImage.createImage(imgName, oriName, url);
			temporaryImageRepository.save(temporaryImage);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void updateSeenCount(CookDetailViewDTO detailViewDTO) {
		CookBoard cookBoard = detailViewDTO.getCookBoard();
		int seenCount = cookBoard.getSeencount()+1;
		cookBoard.setSeencount(seenCount);
		cookBoardRepository.save(cookBoard);
	}

	public boolean doCookRecom(String email, Long writingId) {
		Member member = memberRepository.findByEmail(email);
		CookBoard cookBoard = cookBoardRepository.findByWritingId(writingId);
		int cookRecommend = cookRecommendRepository.checkingRecommend(cookBoard, member);
		System.out.println("체크");
		System.out.println(cookRecommend);
		if(cookRecommend==0) {
			CookRecommend recommend = new CookRecommend();
			recommend.setCookboard(cookBoard);
			recommend.setMember(member);
			cookRecommendRepository.save(recommend);
			int recomCount = cookRecommendRepository.countRecom(cookBoard);
			cookBoard.setRecomcount(recomCount);
			cookBoardRepository.save(cookBoard);
			return true;
		}
		return false;
	}

	public void deleteWriting(String email, Long writingId) {
		Member member = memberRepository.findByEmail(email);
		CookBoard cookBoard = cookBoardRepository.findByWritingId(writingId);
		Member checkMem = cookBoard.getMember();
		if(member==checkMem) {
			cookBoard.setDelcheck(Delcheck.Y);
			cookBoardRepository.save(cookBoard);
		}
	}

	public CookWritingDTO getCookRewrite(Long writingId) {
		CookWritingDTO cookWritingDTO = new CookWritingDTO();
		CookBoard cookBoard = cookBoardRepository.findByWritingId(writingId);
		cookWritingDTO.setTitle(cookBoard.getTitle());
		cookWritingDTO.setContent(cookBoard.getContent());
		cookWritingDTO.setNoticeCheck(cookBoard.getNoticecheck());
		return cookWritingDTO;
	}

	public void Rewrite(Long writingId, CookWritingDTO cookWritingDTO, List<MultipartFile> images, String repUrl) {
		CookBoard cookBoard = cookBoardRepository.findByWritingId(writingId);
		cookBoard.setTitle(cookWritingDTO.getTitle());
		cookBoard.setContent(cookWritingDTO.getContent());
		cookBoard.setNoticecheck(cookWritingDTO.getNoticeCheck());
		cookBoardRepository.save(cookBoard);
		if(!repUrl.trim().equals("")) {
		CookImage oriCookImage = cookImageRepository.findRepImage(cookBoard);
		oriCookImage.setUrl(repUrl);
		cookImageRepository.save(oriCookImage);
		}
	}

	public void reWriteComment(Long commentId, String content) {
		CookComment cookComment = cookCommentRepository.findByCommentId(commentId);
		cookComment.setContent(content);
		cookComment.setRegdate(LocalDateTime.now());
		cookCommentRepository.save(cookComment);
	}

	public boolean deleteComment(Long commentId, String email) {
		CookComment cookComment = cookCommentRepository.findByCommentId(commentId);
		Member member = memberRepository.findByEmail(email);
		if(cookComment!=null) {
			if(member==cookComment.getMember()) {
				cookCommentRepository.delete(cookComment);
				return true;
			}
		}
		return false;
	}

	public void removeTemporalImage(String oriName) {
		List<TemporaryImage>tempoList = temporaryImageRepository.findByOriName(oriName);
		for(int i=0; i<1; i++) {
			temporaryImageRepository.delete(tempoList.get(i));			
		}
	}
	
}
