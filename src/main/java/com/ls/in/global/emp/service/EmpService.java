package com.ls.in.global.emp.service;


import com.ls.in.global.emp.domain.dto.EmpDTO;
import com.ls.in.global.emp.exception.EmpNotFoundException;

import java.util.List;

public interface EmpService {
    public List<EmpDTO> getAllEmp() throws EmpNotFoundException;

    /**
     * @apiNote empId와 일치하는 사원 정보
     * @param empId
     * @return EmpDTO
     * @throws EmpNotFoundException
     */
    public EmpDTO getEmpById(int empId) throws EmpNotFoundException;

    /**
     * @apiNote empId와 일치하는 사원의 부서의 특정 직급과 일치하는 사원 정보
     * @param empId
     * @param positionId
     * @return EmpDTO
     * @throws EmpNotFoundException
     */
    public EmpDTO getEmpByIdAndDepartmentAndPosition(int empId, int positionId) throws EmpNotFoundException;

    /**
     * @apiNote position의 id가 일치하는 사원의 정보
     * @param id
     * @return EmpDTO
     * @throws EmpNotFoundException
     */
    public EmpDTO getEmpByPosition(Integer id) throws EmpNotFoundException;


}
