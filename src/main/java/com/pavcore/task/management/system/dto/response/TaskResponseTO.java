package com.pavcore.task.management.system.dto.response;

import com.pavcore.task.management.system.dao.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    private List<Comment> comments;

    private long author;

    private long performer;

}
