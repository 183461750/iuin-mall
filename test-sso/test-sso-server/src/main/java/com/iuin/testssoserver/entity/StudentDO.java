package com.iuin.testssoserver.entity;

import com.iuin.testssoserver.entity.base.BaseEntity;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
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
@RequiredArgsConstructor
@Entity
@Table(schema = "public", name = "ss_student", indexes = {@Index(name = "ss_student_address_id_idx", columnList = "address_id")})
@org.hibernate.annotations.Table(appliesTo = "ss_student", comment = "地址表")
public class StudentDO extends BaseEntity {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
    @SequenceGenerator(sequenceName = "student_seq", name = "student_seq", allocationSize = 1)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @ToString.Exclude
    private AddressDO addressDO;

    @OneToMany(mappedBy = "studentDO", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<PetDO> petDOList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name="ss_student_role_relation", joinColumns={ @JoinColumn(name="student_id", referencedColumnName="id")}, inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")})
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StudentDO studentDO = (StudentDO) o;
        return id != null && Objects.equals(id, studentDO.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
