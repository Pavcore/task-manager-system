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
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title" , nullable = false, length = 32)
    private String title;

    @Column(name = "description" , length = 512)
    private String description;

    @Column(name = "status" , nullable = false, length = 32)
    private String status;

    @Column(name = "priority" , nullable = false, length = 32)
    private String priority;

    @OneToMany(mappedBy = "task")
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "performer_id")
    private User performer;
}
