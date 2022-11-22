package com.iuin.testssoserver.entity.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @author fa
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;

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