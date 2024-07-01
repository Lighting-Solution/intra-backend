package com.ls.in.global.emp.domain.dto;

import com.ls.in.contact.dto.CompanyDTO;
import com.ls.in.global.emp.domain.model.Emp;

public class EmpMapper {
    public static EmpDTO toDto(Emp emp) {
        if(emp == null) return null;

        EmpDTO empDTO = new EmpDTO();
        empDTO.setEmpId(emp.getEmpId());
        empDTO.setEmpName(emp.getEmpName());
        empDTO.setEmpEmail(emp.getEmpEmail());
        empDTO.setEmpMP(emp.getEmpMP());
        empDTO.setEmpMemo(emp.getEmpMemo());
        empDTO.setEmpHP(emp.getEmpHP());
        empDTO.setEmpHomeAddress(emp.getEmpHomeAddress());
        empDTO.setEmpHomeFax(emp.getEmpHomeFax());
        empDTO.setEmpBirthday(emp.getEmpBirthday());
        empDTO.setEmpSign(emp.getEmpSign());
        empDTO.setEmpAdmin(emp.isEmpAdmin());

        if(emp.getCompany() != null) {
            CompanyDTO companyDTO = new CompanyDTO();
            companyDTO.setCompanyId(emp.getCompany().getCompanyId());
            companyDTO.setCompanyName(emp.getCompany().getCompanyName());
            companyDTO.setCompanyAddress(emp.getCompany().getCompanyAddress());
            companyDTO.setCompanyURL(emp.getCompany().getCompanyURL());
            companyDTO.setCompanyNumber(emp.getCompany().getCompanyNumber());
            companyDTO.setCompanyFax(emp.getCompany().getCompanyFax());
            empDTO.setCompany(companyDTO);
        }

        if(emp.getPosition() != null) {
            PositionDTO positionDTO = new PositionDTO();
            positionDTO.setPositionId(emp.getPosition().getPositionId());
            positionDTO.setPositionName(emp.getPosition().getPositionName());
            empDTO.setPosition(positionDTO);
        }

        if(emp.getDepartment() != null) {
            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setDepartmentId(emp.getDepartment().getDepartmentId());
            departmentDTO.setDepartmentName(emp.getDepartment().getDepartmentName());
            empDTO.setDepartment(departmentDTO);
        }

        return empDTO;
    }

    public static Emp toEmp(EmpDTO empDTO) {
        return null;
    }

}
