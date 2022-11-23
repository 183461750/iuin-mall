package com.iuin.ssoserver.entity;

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
@Table(schema = "public", name = "ss_address")
@org.hibernate.annotations.Table(appliesTo = "ss_address", comment = "地址表")
@Entity
public class AddressDO extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ss_address_seq")
    @SequenceGenerator(name = "ss_address_seq", sequenceName = "ss_address_seq", allocationSize = 1)
    private Long id;

    /**
     * 添加这个配置(mappedBy = "addressDO")表示，当前实体生成的表中，不会有(studentdo_id)的字段生成
     */
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AddressDO addressDO = (AddressDO) o;
        return id != null && Objects.equals(id, addressDO.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}