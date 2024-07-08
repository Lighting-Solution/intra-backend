package com.ls.in.global.emp.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DepartmentType {
    SERVICE_BUSINESS_DIVISION(100, "서비스 사업부"),
    SBD_WEB_SERVICE(101, "웹 서비스팀"),
    SBD_CONSULTATION_SERVICE(102, "상담 서비스팀"),

    MANAGEMENT_SUPPORT_DIVISION(200, "관리 지원부"),
    MSD_HELLO(201, "인사팀"),
    MSD_MONEY(202, "회계팀"),
    MSD_SALES(203, "영업팀"),

    SOLUTION_DEVELOPMENT_DIVISION(300, "솔루션 개발부"),
    SDD_DEVELOPMENT_1(301, "개발 1팀"),
    SDD_DEVELOPMENT_2(302, "개발 2팀"),
    SDD_ENGINEER_1(303, "설계 1팀"),
    SDD_ENGINEER_2(304, "설계 2팀"),
    SDD_DESIGN(305, "디자인팀");

    private final Integer ID;
    private final String NAME;
}
