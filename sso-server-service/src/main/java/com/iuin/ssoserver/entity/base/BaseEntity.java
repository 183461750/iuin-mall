package com.iuin.ssoserver.entity.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * @author fa
 */
@Getter
@Setter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreatedDate
    @Column(columnDefinition = "timestamp")
    @Comment("创建时间")
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(columnDefinition = "timestamp")
    @Comment("更新时间")
    private LocalDateTime updatedTime;

    @NotNull
    private Boolean deleted = false;

}
