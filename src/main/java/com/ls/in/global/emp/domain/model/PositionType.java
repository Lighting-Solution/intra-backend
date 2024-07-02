package com.ls.in.global.emp.domain.model;

public enum PositionType {
    ceo(1),
    departmentHead(2),
    manager(3),
    assistantManager(4),
    staff(5);

    private final Integer ID;

    PositionType(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }
}
