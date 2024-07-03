package com.team12.DASpring.repository;


import com.team12.DASpring.entity.ElectronicDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IElectronicDeviceRepository extends JpaRepository<ElectronicDevice, Long> {

}
