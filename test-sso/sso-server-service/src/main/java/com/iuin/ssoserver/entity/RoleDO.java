package com.iuin.ssoserver.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
 * @author fa
 * @version 2.0.0
 * @date 2020/8/14 19:37
 */
@Getter
@Setter
@ToString
@Entity
@Table(schema = "public", name = "ss_role")
@Comment("角色表")
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
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        RoleDO roleDO = (RoleDO) o;
        return getId() != null && Objects.equals(getId(), roleDO.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }

}
