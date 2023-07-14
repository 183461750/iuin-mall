//package com.iuin.component.jpa.config;
//
//import org.hibernate.dialect.PostgreSQLDialect;
//
///**
// * 配置关联实体不生成外键
// * <p>要在application.yml里面的spring.jpa.database-platform中设置</p>
// *
// * @author Fa
// */
//public class PostgreSQL95DialectWithoutForeignKeys extends PostgreSQLDialect {
//    @Override
//    public String getAddForeignKeyConstraintString(
//            String constraintName,
//            String[] foreignKey,
//            String referencedTable,
//            String[] primaryKey,
//            boolean referencesPrimaryKey) {
//        //设置foreign key对应的列值可以为空
//        return " alter "+ foreignKey[0] +" set default null " ;
//    }
//}
