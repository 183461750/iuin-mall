package com.iuin.ssoserver.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iuin.ssoserver.entity.base.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author fa
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(schema = "public", name = "ss_pet")
@org.hibernate.annotations.Table(appliesTo = "ss_pet", comment = "宠物表")
@Entity
public class PetDO extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ss_pet_seq")
    @SequenceGenerator(name = "ss_pet_seq", sequenceName = "ss_pet_seq", allocationSize = 1)
    private Long id;

    @JsonManagedReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @Comment("学生id")
    @ToString.Exclude
    private StudentDO studentDO;

    /**
     * 年龄
     */
    @Column(columnDefinition = "int8")
    @Comment("年龄")
    private Integer age;

    /**
     * 性别
     */
    @Column(columnDefinition = "int8")
    @Comment("性别")
    private Integer sex;

    @Column(columnDefinition = "varchar(128)")
    @Comment("宠物名")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PetDO petDO = (PetDO) o;
        return id != null && Objects.equals(id, petDO.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}