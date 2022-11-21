package com.iuin.testssoserver.entity;

import lombok.Data;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author fa
 */
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(schema = "public", name = "address")
@org.hibernate.annotations.Table(appliesTo = "address", comment = "地址表")
@Entity
public class AddressDO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @SequenceGenerator(name = "address_seq", sequenceName = "address_seq", allocationSize = 1)
    private Long id;

    @OneToOne(mappedBy = "addressDO", orphanRemoval = true, fetch = FetchType.LAZY)
    private StudentDO studentDO;

    @Column(length = 128, columnDefinition = "varchar")
    @Comment("省")
    private String province;

    @Column(length = 128, columnDefinition = "varchar")
    @Comment("市")
    private String city;

    @Column(length = 128, columnDefinition = "varchar")
    @Comment("区")
    private String area;

    @CreatedDate
    @Column(columnDefinition = "timestamp")
    @Comment("创建时间")
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(columnDefinition = "timestamp")
    @Comment("更新时间")
    private LocalDateTime updatedTime;

}