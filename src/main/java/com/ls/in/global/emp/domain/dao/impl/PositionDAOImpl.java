package com.ls.in.global.emp.domain.dao.impl;

import com.ls.in.global.emp.domain.dao.PositionDAO;
import com.ls.in.global.emp.domain.model.Position;
import com.ls.in.global.emp.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("positionDAO")
public class PositionDAOImpl implements PositionDAO {

    private final PositionRepository positionRepository;

    @Autowired
    public PositionDAOImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public List<Position> findALl() throws DataAccessException {
        return positionRepository.findAll();
    }

    @Override
    public Position findById(Integer id) throws DataAccessException {
        Optional<Position> result = positionRepository.findById(id);
        if(result.isEmpty()) return null;
        return result.get();
    }
}
