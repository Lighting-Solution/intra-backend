package com.ls.in.contact.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CompanyType {
    OUR_COMPANY(1, "자사");

    private final Integer ID;
    private final String NAME;
}
