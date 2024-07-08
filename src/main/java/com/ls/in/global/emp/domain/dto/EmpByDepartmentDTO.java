package com.ls.in.global.emp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpByDepartmentDTO {
    List<EmpDTO> sbd_webService;
    List<EmpDTO> sbd_consultationService;
    List<EmpDTO> msd_hello;
    List<EmpDTO> msd_money;
    List<EmpDTO> msd_sales;
    List<EmpDTO> sdd_development_1;
    List<EmpDTO> sdd_development_2;
    List<EmpDTO> sdd_engineer_1;
    List<EmpDTO> sdd_engineer_2;
    List<EmpDTO> sdd_design;
    final String SERVICE_BUSINESS_DIVISION = "sbd";
    final String MANAGEMENT_SUPPORT_DIVISION = "msd";
    final String SOLUTION_DEVELOPMENT_DIVISION = "sdd";
}
