package com.ls.in.global.emp.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PositionType {
    CEO(1, "대표이사"),
    DEPARTMENT_HEAD(2, "부장"),
    MANAGER(3, "과장"),
    ASSISTANT_MANAGER(4, "대리"),
    STAFF(5, "사원");

    private final Integer ID;
    private final String NAME;
}
