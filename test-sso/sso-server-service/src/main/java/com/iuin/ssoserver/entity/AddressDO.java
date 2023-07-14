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
@Table(schema = "public", name = "ss_address")
@Comment("地址表")
@Entity
public class AddressDO extends BaseEntity {

    /**
     * 添加这个配置(mappedBy = "addressDO")表示，当前实体生成的表中，不会有(studentdo_id)的字段生成
     */
    @JsonManagedReference
    @OneToOne(mappedBy = "addressDO", orphanRemoval = true, fetch = FetchType.LAZY)
    @Comment("学生id")
    @ToString.Exclude
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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        AddressDO addressDO = (AddressDO) o;
        return getId() != null && Objects.equals(getId(), addressDO.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }

}