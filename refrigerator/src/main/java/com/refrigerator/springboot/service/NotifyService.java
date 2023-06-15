package com.refrigerator.springboot.service;

import com.refrigerator.springboot.dto.DoNotifyingDTO;
import com.refrigerator.springboot.entity.Member;
import com.refrigerator.springboot.entity.Notify;
import com.refrigerator.springboot.repository.MemberRepository;
import com.refrigerator.springboot.repository.NotifyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotifyService {

	final NotifyRepository notifyRepository;
	final MemberRepository memberRepository;
	public void doNotifying(DoNotifyingDTO doNotifyingDTO, String email) {
		Member member = memberRepository.findByEmail(email);
		int notifyingCount = notifyRepository.notifyingCount();
		notifyingCount+=1;
		Notify notify = Notify.createNotifying(doNotifyingDTO, notifyingCount, member);
		notifyRepository.save(notify);
	}
	public void changeBann(String email, Long writingId, Long commentId, String removeDate) {
		Member member = memberRepository.findByEmail(email);
		member.setBanned("Y");
		memberRepository.save(member);
		if(commentId==null) {;
			List<Notify> notifies = notifyRepository.findByWritingId(writingId);
			for(Notify notify : notifies) {
			notify.setDid("Y");
			notify.setDiddate(LocalDateTime.now());
			if(removeDate.equals("1일")) {
				member.setBanndate(LocalDateTime.now().plusDays(1));
			}else if(removeDate.equals("7일")) {
				member.setBanndate(LocalDateTime.now().plusDays(7));
			}else if(removeDate.equals("30일")) {
				member.setBanndate(LocalDateTime.now().plusDays(30));
			}
			notifyRepository.save(notify);
			memberRepository.save(member);
			}
		}else {
			List<Notify> notifies = notifyRepository.findByCommentId(commentId);
			for(Notify notify : notifies) {
			notify.setDid("Y");
			notify.setDiddate(LocalDateTime.now());
			if(removeDate.equals("1일")) {
				member.setBanndate(LocalDateTime.now().plusDays(1));
			}else if(removeDate.equals("7일")) {
				member.setBanndate(LocalDateTime.now().plusDays(7));
			}else if(removeDate.equals("30일")) {
				member.setBanndate(LocalDateTime.now().plusDays(30));
			}
			notifyRepository.save(notify);
			memberRepository.save(member);
			}
		}
	}
	
}
