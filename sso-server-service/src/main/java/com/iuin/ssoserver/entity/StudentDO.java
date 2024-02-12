package com.iuin.ssoserver.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.iuin.ssoserver.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 学生表
 *
 * @author fa
 * @version 2.0.0
 * @date 2020/8/14 19:37
 */
@Getter
@Setter
@ToString
@Entity
@Table(schema = "public", name = "ss_student", indexes = {@Index(name = "ss_student_address_id_idx", columnList = "address_id")})
@Comment("地址表")
public class StudentDO extends BaseEntity {

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @ToString.Exclude
    private AddressDO addressDO;

    @JsonBackReference
    @OneToMany(mappedBy = "studentDO", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<PetDO> petDOList = new ArrayList<>();

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "ss_student_role_relation", joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    @ToString.Exclude
    private List<RoleDO> roleDOList = new ArrayList<>();

    /**
     * 年龄
     */
    @Column(columnDefinition = "int8")
    private Integer age;

    /**
     * 性别
     */
    @Column(columnDefinition = "int8")
    private Integer sex;

    /**
     * 姓名
     */
    @Column(columnDefinition = "varchar(128)")
    private String name;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        StudentDO studentDO = (StudentDO) o;
        return getId() != null && Objects.equals(getId(), studentDO.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }

}
