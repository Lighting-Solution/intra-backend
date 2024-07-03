package com.ls.in.approval.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class DigitalApprovalDTO {
    private int digital_approval_id;
    private boolean ceo_status;
    private String name;
    private String path;
    private boolean type;
    private int draft_id;
    private boolean drafter_status;
    private boolean manager_status;
    private int emp_id;
}
