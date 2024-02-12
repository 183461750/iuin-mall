package com.iuin.ssoserver.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iuin.ssoserver.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

/**
 * @author fa
 */
@Getter
@Setter
@ToString
@Table(schema = "public", name = "ss_pet")
@Comment("宠物表")
@Entity
public class PetDO extends BaseEntity {

    @JsonManagedReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
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
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        PetDO petDO = (PetDO) o;
        return getId() != null && Objects.equals(getId(), petDO.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }

}