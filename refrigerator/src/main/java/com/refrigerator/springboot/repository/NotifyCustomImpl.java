package com.refrigerator.springboot.repository;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.refrigerator.springboot.dto.NotifyListDTO;
import com.refrigerator.springboot.dto.QNotifyListDTO;
import com.refrigerator.springboot.entity.QNotify;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

public class NotifyCustomImpl implements NotifyCustom {
	
	private JPAQueryFactory queryFactory;
	
	 public NotifyCustomImpl(EntityManager em){
	        this.queryFactory = new JPAQueryFactory(em);
	    }

	@Override
	public Page<NotifyListDTO> getNotifyList(Pageable pageable) {
		List<NotifyListDTO> contents = queryFactory
				.select(new QNotifyListDTO(
						QNotify.notify.notifingid,
						QNotify.notify.notifiednum,
						QNotify.notify.member,
						QNotify.notify.notifiedmember,
						QNotify.notify.title,
						QNotify.notify.notifiedcontent,
						QNotify.notify.notifiedcase,
						QNotify.notify.notifiedreason,
						QNotify.notify.regdate,
						QNotify.notify.diddate,
						QNotify.notify.did,
						QNotify.notify.writingid,
						QNotify.notify.commentid,
						QNotify.notify.boardid))
				.from(QNotify.notify)
				.orderBy(QNotify.notify.writingid.asc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		Long total = queryFactory
				.select(Wildcard.count)
				.from(QNotify.notify)
				.fetchOne();
		System.out.println(total);
		return new PageImpl<>(contents, pageable, total);
	}


}
