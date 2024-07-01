package com.ls.in.global.emp.domain.dao;

import com.ls.in.global.emp.domain.model.Position;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface PositionDAO {
    public List<Position> findALl() throws DataAccessException;
    public Position findById(Integer id) throws DataAccessException;
}
