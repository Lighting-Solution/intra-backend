package com.ls.in.global.aspect;

import com.ls.in.global.exception.BaseException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataAccessExceptionAspect {
    /**
     * @apiNote DataAccessException 발생 후 처리
     * @param ex
     */
    @AfterThrowing(pointcut = "execution(* com.ls.in..*(..))", throwing = "ex")
    public void handleDataAccessException(DataAccessException ex) {

        System.err.println("Data access error: " + ex.getMessage());
        throw new BaseException("Database error occurred : " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}