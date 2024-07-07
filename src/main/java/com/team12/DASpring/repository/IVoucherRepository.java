package com.team12.DASpring.repository;


import com.team12.DASpring.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVoucherRepository extends JpaRepository<Voucher, Long> {

    @Query("select p from Voucher p where p.isActive = ?1")
    public List<Voucher> getVoucherByIsActive(boolean active);

}
