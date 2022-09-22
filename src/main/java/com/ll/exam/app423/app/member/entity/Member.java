package com.ll.exam.app423.app.member.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.ll.exam.app423.app.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Member extends BaseEntity {
    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;
    private String email;

    @JsonIgnore
    private String accessToken;

    public Member(long id) {
        super(id);
    }
}