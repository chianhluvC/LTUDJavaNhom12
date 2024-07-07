package com.team12.DASpring.repository;


import com.team12.DASpring.entity.ElectronicDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IElectronicDeviceRepository extends JpaRepository<ElectronicDevice, Long> {

    @Query("select p from ElectronicDevice  p where concat(p.name,p.category.name, p.description,p.price, p.discountPrice) like %?1%")
    List<ElectronicDevice> search(String name);

    @Query("select p from  ElectronicDevice  p")
    Page<ElectronicDevice> findAll(Pageable pageable);
}
