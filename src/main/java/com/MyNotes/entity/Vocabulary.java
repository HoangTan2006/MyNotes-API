package com.MyNotes.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "vocabularies")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vocabulary extends AbstractEntity<Long> {
    @Column(name = "word")
    private String word;

    @Column(name = "note")
    private String note;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;
}
