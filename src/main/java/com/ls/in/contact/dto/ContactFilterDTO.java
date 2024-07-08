package com.ls.in.contact.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactFilterDTO {
    private Integer groupId;
    private Integer departmentId;
    private Integer empId;
    private String groupType;

    private String filterType; // 검색 필터 조건 : 이름, 휴대폰, 회사
    private String filterContent;

    private String sortType; // 정렬 정보 : 이름, 회사

    public static ContactFilterDTO toDTO(Integer groupId, Integer departmentId, String filterType, String filterContent, String sortType, Integer empId, String groupType) {
        ContactFilterDTO responseDTO = new ContactFilterDTO();
        responseDTO.setGroupId(groupId);
        responseDTO.setDepartmentId(departmentId);
        responseDTO.setFilterType(filterType);
        responseDTO.setFilterContent(filterContent);
        responseDTO.setSortType(sortType);
        responseDTO.setEmpId(empId);
        responseDTO.setGroupType(groupType);
        return responseDTO;
    }
}
