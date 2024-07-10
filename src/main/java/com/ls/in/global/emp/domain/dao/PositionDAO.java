package com.ls.in.global.emp.domain.dao;

import com.ls.in.global.emp.domain.model.Position;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface PositionDAO {
    List<Position> findALl() throws DataAccessException;
    Position findById(Integer id) throws DataAccessException;
}
