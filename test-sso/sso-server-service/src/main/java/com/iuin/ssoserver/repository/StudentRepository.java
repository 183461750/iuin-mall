package com.iuin.ssoserver.repository;

import com.iuin.ssoserver.entity.StudentDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author fa
 */
@Repository
public interface StudentRepository extends JpaRepository<StudentDO, Long>, JpaSpecificationExecutor<StudentDO> {

}
