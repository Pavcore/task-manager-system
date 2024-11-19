package com.pavcore.task.management.system.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String description;

    private String status;

    private String priority;

    @OneToMany(mappedBy = "task")
    private List<Comment> comment;

    private long author;

    @ManyToOne
    @JoinColumn(name = "task")
    private User performer;
}
