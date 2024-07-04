package com.ls.in.global.emp.repository.impl;

import com.ls.in.contact.dto.ContactFilterPageDTO;
import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.domain.model.QEmp;
import com.ls.in.global.emp.repository.EmpCustomRepository;
import com.ls.in.global.util.Utils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmpCustomRepositoryImpl implements EmpCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public EmpCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Emp> search(ContactFilterPageDTO data) {
        QEmp emp = QEmp.emp;

        BooleanBuilder builder = new BooleanBuilder();
        if (!(Utils.checkStringNull(data.getFilterType()) || Utils.checkStringNull(data.getFilterContent()))) {
            switch (data.getFilterType()) {
                case "name":
                    builder.and(emp.empName.containsIgnoreCase(data.getFilterContent()));
                    break;
                case "mp":
                    builder.and(emp.empMP.containsIgnoreCase(data.getFilterContent()));
                    break;
                case "company":
                    builder.and(emp.company.companyName.containsIgnoreCase(data.getFilterContent()));
                    break;
            }
        }

        if(Utils.checkIntegerNull(data.getPageOffset())) data.setPageOffset(0);
        if(Utils.checkIntegerNull(data.getPageSize())) data.setPageSize(10);

        JPAQuery<Emp> query = queryFactory.selectFrom(emp)
                .where(builder)
                .offset(data.getPageOffset())
                .limit(data.getPageSize());

        if (!Utils.checkStringNull(data.getSortType())) {
            switch (data.getSortType()) {
                case "nameAsc":
                    query.orderBy(emp.empName.asc());
                    break;
                case "nameDesc":
                    query.orderBy(emp.empName.desc());
                    break;
                case "companyAsc":
                    query.orderBy(emp.company.companyName.asc());
                    break;
                case "companyDesc":
                    query.orderBy(emp.company.companyName.desc());
                    break;
            }
        }

        List<Emp> results = query.fetch();
        long total = queryFactory.selectFrom(emp).where(builder).fetch().size();
        Pageable pageable = PageRequest.of(data.getPageOffset(), data.getPageSize());

        return new PageImpl<>(results, pageable, total);
    }
}
