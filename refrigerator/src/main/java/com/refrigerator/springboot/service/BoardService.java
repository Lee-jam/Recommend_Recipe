package com.refrigerator.springboot.service;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.refrigerator.springboot.dto.DetailViewDTO;
import com.refrigerator.springboot.dto.RewriteDTO;
import com.refrigerator.springboot.dto.WriteFormDTO;
import com.refrigerator.springboot.constant.Delcheck;
import com.refrigerator.springboot.constant.RepImage;
import com.refrigerator.springboot.entity.Board;
import com.refrigerator.springboot.entity.Member;
import com.refrigerator.springboot.entity.RecipeBoard;
import com.refrigerator.springboot.entity.RecipeComment;
import com.refrigerator.springboot.entity.RecipeContent;
import com.refrigerator.springboot.entity.RecipeFollowWriting;
import com.refrigerator.springboot.entity.RecipeImage;
import com.refrigerator.springboot.entity.RecipeRecommend;
import com.refrigerator.springboot.repository.BoardRepository;
import com.refrigerator.springboot.repository.MemberRepository;
import com.refrigerator.springboot.repository.RecipeBoardRepository;
import com.refrigerator.springboot.repository.RecipeCommentRepository;
import com.refrigerator.springboot.repository.RecipeContentRepository;
import com.refrigerator.springboot.repository.RecipeFollow;
import com.refrigerator.springboot.repository.RecipeImageRepository;
import com.refrigerator.springboot.repository.RecipeRecommendRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	final FileService fileService;
	final MemberRepository memberRepository;
	final RecipeBoardRepository recipeBoardRepository;
	final RecipeContentRepository recipeContentRepository;
	final BoardRepository boardRepository;
	final RecipeImageRepository recipeImageRepository;
	final RecipeRecommendRepository recipeRecommendRepository;
	final RecipeFollow recipeFollow;
	final RecipeCommentRepository recipeCommentRepository;

	public void Recipewriting(WriteFormDTO writeFormDTO, String email, Long boardId, String recipeContent, List<MultipartFile> recipeImages) {
		Member member = memberRepository.findByEmail(email);
		Board board = boardRepository.findByBoardid(boardId);
		Integer writingCount = recipeBoardRepository.findByAllWriting();
		if(writingCount==null) {
			writingCount=1;
		}else if(writingCount!=null) {
			writingCount = recipeBoardRepository.findByAllWriting()+1;
		}
		writeFormDTO.setMember(member);
		writeFormDTO.setBoard(board);
		RecipeBoard newOne=RecipeBoard.writing(writeFormDTO,writingCount);
		recipeBoardRepository.save(newOne);
		String[] recipeContents = recipeContent.split(",");
		ArrayList<String> urls = new ArrayList<>();
		for(int i=0; i<recipeImages.size(); i++) {
			RecipeImage recipeImage = new RecipeImage();
			recipeImage.setRecipeboard(newOne);
			recipeImage.setRepimage("N");
			if(i==0) {
				recipeImage.setRepimage("Y");
			}
			String url = this.saveRecipeImage(recipeImage, recipeImages.get(i));
			urls.add(url);
		}

		List<RecipeContent> newContents = RecipeContent.createContents(recipeContents, newOne, urls);
		for(RecipeContent Content :newContents) {
			recipeContentRepository.save(Content);
		}


	}

	public String saveRecipeImage(RecipeImage recipeImage, MultipartFile Image) {
		String uploadPath = "C:/storage/project";
		String oriName = Image.getOriginalFilename();
		String imgName = "";
		String url = "";
		try {
			if(!StringUtils.isEmpty(oriName)) {
				imgName = fileService.uploadFile(uploadPath, oriName, Image.getBytes());
				url="/images/project/"+imgName;
			}

			recipeImage.saveRecipeImage(imgName, oriName, url);
			recipeImageRepository.save(recipeImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}

	public void deleteUpdate(String email, Long writingId) {
		Member member = memberRepository.findByEmail(email);
		RecipeBoard recipeBoard = recipeBoardRepository.findByRecipeDetail(writingId);
		if(member==recipeBoard.getMember()) {
			recipeBoard.setDelcheck(Delcheck.Y);
			recipeBoardRepository.save(recipeBoard);
		}

	}

	public void RecipeRewriting(WriteFormDTO writeFormDTO, String id, Long boardId, String recipeContent, List<MultipartFile> recipeImages, Long writingId, String imgDelCheck) {

		RecipeBoard recipeBoard =recipeBoardRepository.findByRecipeDetail(writingId);
		recipeBoard.updateWriting(writeFormDTO);
		List<RecipeContent> ContentsList = recipeContentRepository.findByRecipeContent(recipeBoard);
		List<RecipeImage> ImageList = recipeImageRepository.findRecipeImagesByWritingId(recipeBoard);
		String[] recipeContents = recipeContent.split(",");
		String[] delChecks = imgDelCheck.split(",");
		ArrayList<String> urls = new ArrayList<>();
		System.out.println("ㅅㅂ");
		System.out.println(recipeContents);

		for(int i=0; i<recipeImages.size(); i++) {
			RecipeImage recipeImage = new RecipeImage();
			recipeImage.setRecipeboard(recipeBoard);
			recipeImage.setRepimage("N");
			String url = this.saveRecipeImage(recipeImage, recipeImages.get(i));
			if(i==0 && !url.equals("")) {
				RecipeImage postRepImage = recipeImageRepository.findRepImage(recipeBoard);
				recipeImageRepository.delete(postRepImage);
				recipeImage.setRepimage("Y");
			}else if(i==0 && url.equals("") && delChecks[i].equals("Y")) {
				RecipeImage postRepImage = recipeImageRepository.findRepImage(recipeBoard);
				recipeImageRepository.delete(postRepImage);
				recipeImage.setRepimage("Y");
			}
			urls.add(url);
		}

		for(int i=0; i<recipeContents.length; i++) {
			if(i<ContentsList.size()) {
				System.out.println("ㅅㅂ");
				System.out.println(recipeContents[i]);
				RecipeContent content = ContentsList.get(i);
				content.setContent(recipeContents[i]);
				if(!urls.get(i).equals("")) {
					content.setUrl(urls.get(i));
					recipeContentRepository.save(content);
				}else if(urls.get(i).equals("") && delChecks[i].equals("Y")) {
					content.setUrl(urls.get(i));
					recipeContentRepository.save(content);
				}
				recipeContentRepository.save(content);
			}else {
				RecipeContent newContent = new RecipeContent();
				newContent.setContent(recipeContents[i]);
				newContent.setUrl(urls.get(i));
				newContent.setRecipeboard(recipeBoard);
				recipeContentRepository.save(newContent);
			}
		}

		for(int i=0; i<ImageList.size(); i++) {
			if(!urls.contains(ImageList.get(i).getUrl())) {
				recipeImageRepository.delete(ImageList.get(i));
			}
		}
	}

	public void updateSeenCount(DetailViewDTO detailViewDTO) {
		RecipeBoard recipeBoard = detailViewDTO.getRecipeBoard();
		int seenCount = recipeBoard.getSeencount()+1;
		recipeBoard.setSeencount(seenCount);
		recipeBoardRepository.save(recipeBoard);
	}

	public boolean updateRecomCount(DetailViewDTO detailViewDTO, Member member) {
		RecipeBoard recipeBoard = detailViewDTO.getRecipeBoard();
		if(recipeRecommendRepository.findByRecipeBoardAndMember(recipeBoard, member)==0) {
			RecipeRecommend recipeRecommend = RecipeRecommend.updateRecommend(recipeBoard, member);
			recipeRecommendRepository.save(recipeRecommend);
			int recomCount = recipeRecommendRepository.countRecommend(recipeBoard);
			recipeBoard.setRecomcount(recomCount);
			recipeBoardRepository.save(recipeBoard);
			return true;
		}
		return false;
	}

	public RewriteDTO getRewriteValue(Long writingId) {

		RecipeBoard recipeBoard = recipeBoardRepository.findByRecipeDetail(writingId);
		List<RecipeContent> recipeContents = recipeContentRepository.findByRecipeContent(recipeBoard);
		List<RecipeImage> recipeImages= recipeImageRepository.findRecipeImagesByWritingId(recipeBoard);
		RewriteDTO rewriteDTO = new RewriteDTO();
		rewriteDTO.setRecipeBoard(recipeBoard);
		rewriteDTO.setRecipeContents(recipeContents);
		rewriteDTO.setRecipeImages(recipeImages);
		return rewriteDTO;

	}

	public void updateFollow(String email, Long writingId) {

		Member member = memberRepository.findByEmail(email);
		RecipeBoard recipeBoard = recipeBoardRepository.findByRecipeDetail(writingId);
		RecipeFollowWriting recipeFollowWriting = new RecipeFollowWriting();
		if(recipeFollow.findFollowing(member, recipeBoard)==0) {
			recipeFollowWriting.setMember(member);
			recipeFollowWriting.setRecipeboard(recipeBoard);
			recipeFollow.save(recipeFollowWriting);
		}else if(recipeFollow.findFollowing(member, recipeBoard)!=0) {
			recipeFollow.delete(recipeFollow.findToDel(member, recipeBoard));
		}

	}

	public DetailViewDTO getDetailView(Long writingId) {
		DetailViewDTO detailViewDTO = new DetailViewDTO();
		RecipeBoard recipeBoard = recipeBoardRepository.findByRecipeDetail(writingId);
		List<RecipeContent> recipeContents = recipeContentRepository.findByRecipeContent(recipeBoard);
		List<RecipeImage> recipeImages = recipeImageRepository.findRecipeImagesByWritingId(recipeBoard);
		List<RecipeComment> recipeComments = recipeCommentRepository.findRecipeCommentByWritingId(recipeBoard);
		detailViewDTO.setRecipeBoard(recipeBoard);
		detailViewDTO.setRecipeContents(recipeContents);
		detailViewDTO.setRecipeImages(recipeImages);
		detailViewDTO.setRecipeComments(recipeComments);
		return detailViewDTO;
	}

	public boolean deleteComment(Long commentId, String email) {
		Member member = memberRepository.findByEmail(email);
		RecipeComment recipeComment = recipeCommentRepository.findCommentByCommentId(commentId);
		Member commentMember = recipeComment.getMember();
		if(recipeComment!=null) {
			if(member==commentMember) {
				recipeCommentRepository.delete(recipeComment);
			}else {
				return false;
			}
		}
		return true;
	}

	public void reWriteComment(Long commentId, String comment) {
		RecipeComment recipeComment =recipeCommentRepository.findCommentByCommentId(commentId);
		recipeComment.setContent(comment);
		recipeComment.setRegdate(LocalDateTime.now());
		recipeCommentRepository.save(recipeComment);
	}

}
