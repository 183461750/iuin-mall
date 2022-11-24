package com.iuin.ssoserver.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author fa
 * @version 2.0.0
 * @date 2020/8/14 19:37
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(schema = "public", name = "ss_role")
@org.hibernate.annotations.Table(appliesTo = "ss_role", comment = "角色表")
public class RoleDO {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ss_role_seq")
    @SequenceGenerator(sequenceName = "ss_role_seq", name = "ss_role_seq", allocationSize = 1)
    private Long id;

    @JsonManagedReference
    @ManyToMany(mappedBy = "roleDOList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Comment("学生id")
    @ToString.Exclude
    private List<StudentDO> studentDOList = new ArrayList<>();

    /**
     * 角色名称
     */
    @Column(columnDefinition = "varchar(128)")
    @Comment("角色名称")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RoleDO roleDO = (RoleDO) o;
        return id != null && Objects.equals(id, roleDO.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
