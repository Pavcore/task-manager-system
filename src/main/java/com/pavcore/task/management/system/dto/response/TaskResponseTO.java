package com.pavcore.task.management.system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskResponseTO {

    private long id;

    private String title;

    private String description;

    private String status;

    private String priority;

    private String comment;

    private long author;

    private long performer;

}
