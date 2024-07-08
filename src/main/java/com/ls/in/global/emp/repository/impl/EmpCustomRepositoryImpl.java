package com.ls.in.global.emp.repository.impl;

import com.ls.in.contact.dto.ContactFilterDTO;
import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.domain.model.QEmp;
import com.ls.in.global.emp.repository.EmpCustomRepository;
import com.ls.in.global.util.Utils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Emp> search(ContactFilterDTO data) {
        QEmp emp = QEmp.emp;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(emp.department.departmentId.eq(data.getDepartmentId()));

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

        JPAQuery<Emp> query = queryFactory.selectFrom(emp)
                .where(builder);

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

        return query.fetch();
    }
}
