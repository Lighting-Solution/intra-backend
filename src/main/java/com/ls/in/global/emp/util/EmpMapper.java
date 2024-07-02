package com.ls.in.global.emp.util;

import com.ls.in.contact.dto.CompanyDTO;
import com.ls.in.contact.util.mapper.CompanyMapper;
import com.ls.in.global.emp.domain.dto.DepartmentDTO;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.domain.dto.PositionDTO;
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
            CompanyDTO companyDTO = CompanyMapper.toDTO(emp.getCompany());
            empDTO.setCompany(companyDTO);
        }

        if(emp.getPosition() != null) {
            PositionDTO positionDTO = PositionMapper.toDTO(emp.getPosition());
            empDTO.setPosition(positionDTO);
        }

        if(emp.getDepartment() != null) {
            DepartmentDTO departmentDTO = DepartmentMapper.toDTO(emp.getDepartment());
            empDTO.setDepartment(departmentDTO);
        }

        return empDTO;
    }
}
