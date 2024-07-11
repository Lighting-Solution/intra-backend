package com.ls.in.global.emp.service.impl;

import com.ls.in.global.emp.domain.dao.EmpDAO;
import com.ls.in.global.emp.domain.dto.EmpByDepartmentDTO;
import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.domain.dto.UserDTO;
import com.ls.in.global.emp.domain.model.DepartmentType;
import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.global.emp.exception.EmpNotFoundException;
import com.ls.in.global.emp.service.EmpService;
import com.ls.in.global.emp.util.EmpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("empService")
public class EmpServiceImpl implements EmpService {

    private final EmpDAO empDAO;

    @Autowired
    public EmpServiceImpl(EmpDAO empDAO) {
        this.empDAO = empDAO;
    }


    @Override
    public List<EmpDTO> getAllEmp() throws EmpNotFoundException {
        List<EmpDTO> returnDTOList = new ArrayList<>();
        List<Emp> resultEmpList = empDAO.findAll();
        for (Emp emp : resultEmpList) {
            EmpDTO tempDTO = EmpMapper.toDto(emp);
            returnDTOList.add(tempDTO);
        }
        return returnDTOList;
    }

    @Override
    public EmpDTO getEmpById(int empId) throws EmpNotFoundException {
        Emp resultEmp = empDAO.findById(empId);
        return EmpMapper.toDto(resultEmp);
    }

    @Override
    public EmpDTO getEmpByIdAndDepartmentAndPosition(int empId, int positionId) throws EmpNotFoundException {
        Emp result = empDAO.findByIdAndDepartmentAndPosition(empId, positionId);
        return EmpMapper.toDto(result);
    }

    @Override
    public EmpDTO getEmpByPosition(Integer id) throws EmpNotFoundException {
        Emp result = empDAO.findByPosition(id);
        return EmpMapper.toDto(result);
    }

    @Override
    public UserDTO findByAccountId(String accountId) {
        Optional<Emp> empOptional = empDAO.findByAccountId(accountId);
        Emp emp = empOptional.get();
        UserDTO userDTO = new UserDTO();
        userDTO.setAccountId(emp.getAccountId());
        userDTO.setAccountPw(emp.getAccountPw());
        userDTO.setEmpId(emp.getEmpId());
        userDTO.setPositionId(emp.getPosition().getPositionId());
        userDTO.setEmpName(emp.getEmpName());
        userDTO.setEmpAdmin(emp.isEmpAdmin());
        userDTO.setDepartmentName(emp.getDepartment().getDepartmentName());
        userDTO.setEmpEmail(emp.getEmpEmail());

        if(emp.getPosition().getPositionId().equals(1)){
           userDTO.setDepartmentId(0);
        } else {
            userDTO.setDepartmentId(emp.getDepartment().getDepartmentId());
        }

        return userDTO;
    }

    public boolean createEmp(EmpDTO empDTO) throws EmpNotFoundException {
        Emp emp = EmpMapper.toEntity(empDTO);
        return empDAO.save(emp);
    }

    @Override
    public EmpByDepartmentDTO getAllByDepartment() throws EmpNotFoundException {
        EmpByDepartmentDTO responseDTO = new EmpByDepartmentDTO();

        List<Emp> result = empDAO.findAllByDepartment(DepartmentType.SBD_WEB_SERVICE.getID());
        List<EmpDTO> tempList = forList(result);
        responseDTO.setSbd_webService(tempList);

        result = empDAO.findAllByDepartment(DepartmentType.SBD_CONSULTATION_SERVICE.getID());
        tempList = forList(result);
        responseDTO.setSbd_consultationService(tempList);

        result = empDAO.findAllByDepartment(DepartmentType.MSD_HELLO.getID());
        tempList = forList(result);
        responseDTO.setMsd_hello(tempList);

        result = empDAO.findAllByDepartment(DepartmentType.MSD_MONEY.getID());
        tempList = forList(result);
        responseDTO.setMsd_money(tempList);

        result = empDAO.findAllByDepartment(DepartmentType.MSD_SALES.getID());
        tempList = forList(result);
        responseDTO.setMsd_sales(tempList);

        result = empDAO.findAllByDepartment(DepartmentType.SDD_DEVELOPMENT_1.getID());
        tempList = forList(result);
        responseDTO.setSdd_development_1(tempList);

        result = empDAO.findAllByDepartment(DepartmentType.SDD_DEVELOPMENT_2.getID());
        tempList = forList(result);
        responseDTO.setSdd_development_2(tempList);

        result = empDAO.findAllByDepartment(DepartmentType.SDD_ENGINEER_1.getID());
        tempList = forList(result);
        responseDTO.setSdd_engineer_1(tempList);

        result = empDAO.findAllByDepartment(DepartmentType.SDD_ENGINEER_2.getID());
        tempList = forList(result);
        responseDTO.setSdd_engineer_2(tempList);

        result = empDAO.findAllByDepartment(DepartmentType.SDD_DESIGN.getID());
        tempList = forList(result);
        responseDTO.setSdd_design(tempList);

        return responseDTO;
    }

    @Override
    public EmpDTO findByPositionIdAndDepartmentId(int i, Integer departmentId) {
        Emp emp = empDAO.findByPositionIdAndDepartmentId(i, departmentId);
        return EmpMapper.toDto(emp);
    }

    private List<EmpDTO> forList(List<Emp> emps) {
        List<EmpDTO> tempList = new ArrayList<>();
        for (Emp emp : emps) {
            EmpDTO empDTO = EmpMapper.toDto(emp);
            tempList.add(empDTO);
        }
        return tempList;
    }
}
