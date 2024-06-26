package com.iuin.ssoserver.repository;

import com.iuin.ssoserver.entity.AddressDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author fa
 */
public interface AddressRepository extends JpaRepository<AddressDO, Long>, JpaSpecificationExecutor<AddressDO> {

}