package com.ls.in.contact.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactFilterPageDTO {
    private Integer groupId;
    private Integer departmentId;

    private Integer pageOffset;
    private Integer pageSize;

    private String filterType; // 검색 필터 조건 : 이름, 휴대폰, 회사
    private String filterContent;

    private String sortType; // 정렬 정보 : 이름, 회사

    public static ContactFilterPageDTO toDTO(Integer groupId, Integer departmentId, Integer pageOffset, Integer pageSize, String filterType, String filterContent, String sortType) {
        ContactFilterPageDTO responseDTO = new ContactFilterPageDTO();
        responseDTO.setGroupId(groupId);
        responseDTO.setDepartmentId(departmentId);
        responseDTO.setPageOffset(pageOffset);
        responseDTO.setPageSize(pageSize);
        responseDTO.setFilterType(filterType);
        responseDTO.setFilterContent(filterContent);
        responseDTO.setSortType(sortType);
        return responseDTO;
    }
}
