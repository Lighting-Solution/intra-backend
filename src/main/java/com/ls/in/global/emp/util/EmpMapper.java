package com.ls.in.global.emp.util;

import com.ls.in.contact.dto.CompanyDTO;
import com.ls.in.contact.util.mapper.CompanyMapper;
import com.ls.in.global.emp.domain.dto.DepartmentDTO;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.domain.dto.PositionDTO;
import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.util.Formats;

import java.text.Format;

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

    public static Emp toEntity(EmpDTO empDTO) {
        if(empDTO == null) return null;
        Integer id = Formats.toInteger(empDTO.getEmpId());
        return Emp.builder()
                .empId(id)
                .empName(empDTO.getEmpName())
                .empEmail(empDTO.getEmpEmail())
                .empMP(empDTO.getEmpMP())
                .empMemo(empDTO.getEmpMemo())
                .empHP(empDTO.getEmpHP())
                .empHomeAddress(empDTO.getEmpHomeAddress())
                .empHomeFax(empDTO.getEmpHomeFax())
                .empBirthday(empDTO.getEmpBirthday())
                .empSign(empDTO.getEmpSign())
                .company(CompanyMapper.toEntity(empDTO.getCompany()))
                .position(PositionMapper.toEntity(empDTO.getPosition()))
                .department(DepartmentMapper.toEntity(empDTO.getDepartment()))
                .build();
    }
}
