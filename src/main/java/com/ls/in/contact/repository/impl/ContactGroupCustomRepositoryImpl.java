package com.ls.in.contact.repository.impl;

import com.ls.in.contact.domain.model.ContactGroup;
import com.ls.in.contact.domain.model.PersonalContact;
import com.ls.in.contact.domain.model.QContactGroup;
import com.ls.in.contact.domain.model.QPersonalContact;
import com.ls.in.contact.dto.ContactFilterPageDTO;
import com.ls.in.contact.repository.ContactGroupCustomRepository;
import com.ls.in.global.util.Utils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;


public class ContactGroupCustomRepositoryImpl implements ContactGroupCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Autowired
    public ContactGroupCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<PersonalContact> search(ContactFilterPageDTO data) {
        QContactGroup contactGroup = QContactGroup.contactGroup;
        QPersonalContact personalContact = QPersonalContact.personalContact;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(contactGroup.personalGroup.personalGroupId.eq(data.getGroupId()));

        if(Utils.checkIntegerNull(data.getPageOffset())) data.setPageOffset(0);
        if(Utils.checkIntegerNull(data.getPageSize())) data.setPageSize(10);

        if (!(Utils.checkStringNull(data.getFilterType()) || Utils.checkStringNull(data.getFilterContent()))) {
            switch (data.getFilterType()) {
                case "name":
                    builder.and(personalContact.personalContactName.containsIgnoreCase(data.getFilterContent()));
                    break;
                case "mp":
                    builder.and(personalContact.personalContactMP.containsIgnoreCase(data.getFilterContent()));
                    break;
                case "company":
                    builder.and(personalContact.company.companyName.containsIgnoreCase(data.getFilterContent()));
                    break;
            }
        }

        JPAQuery<PersonalContact> query = queryFactory.select(contactGroup.personalContact)
                .from(contactGroup)
                .join(contactGroup.personalContact, personalContact)
                .where(builder)
                .offset(data.getPageOffset())
                .limit(data.getPageSize());

        if (!Utils.checkStringNull(data.getSortType())) {
            switch (data.getSortType()) {
                case "nameAsc":
                    query.orderBy(personalContact.personalContactName.asc());
                    break;
                case "nameDesc":
                    query.orderBy(personalContact.personalContactName.desc());
                    break;
                case "companyAsc":
                    query.orderBy(personalContact.company.companyName.asc());
                    break;
                case "companyDesc":
                    query.orderBy(personalContact.company.companyName.desc());
                    break;
            }
        }

        List<PersonalContact> results = query.fetch();
        long total = queryFactory.selectFrom(contactGroup)
                .join(contactGroup.personalContact, personalContact)
                .where(builder)
                .fetch().size();
        Pageable pageable = PageRequest.of(data.getPageOffset(), data.getPageSize());
        return new PageImpl<>(results, pageable, total);
    }

    public List<ContactGroup> findByPersonalContactAndPersonalGroup(List<Integer> contactIds, List<Integer> groupIds) {
        QContactGroup contactGroup = QContactGroup.contactGroup;

        return queryFactory.selectFrom(contactGroup)
                .where(contactGroup.personalContact.personalContactId.in(contactIds)
                        .and(contactGroup.personalGroup.personalGroupId.in(groupIds)))
                .fetch();
    }
}
