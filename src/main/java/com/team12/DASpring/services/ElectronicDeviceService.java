package com.team12.DASpring.services;


import com.team12.DASpring.entity.Discount;
import com.team12.DASpring.entity.ElectronicDevice;
import com.team12.DASpring.repository.IDiscountRepository;
import com.team12.DASpring.repository.IElectronicDeviceRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional

public class ElectronicDeviceService {

    private final IElectronicDeviceRepository electronicDeviceRepository;
    private final IDiscountRepository discountRepository;


    public List<ElectronicDevice> getAllElectronicDevices(){
        return electronicDeviceRepository.findAll();
    }

    public Optional<ElectronicDevice> getElectronicDeviceById(Long id){
        return electronicDeviceRepository.findById(id);
    }

    public ElectronicDevice addElectronicDevice(ElectronicDevice electronicDevice){

        Discount discount = discountRepository.findById(electronicDevice.getDiscount().getId())
                .orElseThrow(null);
        if(electronicDevice.getIsActive()==null)
            electronicDevice.setIsActive(false);
        if(discount!=null && electronicDevice.getIsActive())
            electronicDevice.setDiscountPrice(electronicDevice.getPrice() * (100 - discount.getDiscountPercent())/100);
        else
            electronicDevice.setDiscountPrice(electronicDevice.getPrice());
        return electronicDeviceRepository.save(electronicDevice);
    }

    public ElectronicDevice updateElectronicDevice(@NotNull ElectronicDevice electronicDevice){
        ElectronicDevice existingElectronicDevice = electronicDeviceRepository.findById(electronicDevice.getId())
                .orElseThrow(()->new IllegalStateException("Electronic Device with ID " + electronicDevice.getId()
                        + "does not exist."));
        var discount = discountRepository.findById(electronicDevice.getDiscount().getId())
                .orElseThrow(null);
        if(electronicDevice.getIsActive()==null)
            electronicDevice.setIsActive(false);
        if(discount!=null && electronicDevice.getIsActive())
            existingElectronicDevice.setDiscountPrice(electronicDevice.getPrice() * (100 - discount.getDiscountPercent())/100);
        else
            existingElectronicDevice.setDiscountPrice(electronicDevice.getPrice());
        existingElectronicDevice.setName(electronicDevice.getName());
        existingElectronicDevice.setImage(electronicDevice.getImage());
        existingElectronicDevice.setImage1(electronicDevice.getImage1());
        existingElectronicDevice.setImage2(electronicDevice.getImage2());
        existingElectronicDevice.setImage3(electronicDevice.getImage3());
        existingElectronicDevice.setCategory(electronicDevice.getCategory());
        existingElectronicDevice.setPrice(electronicDevice.getPrice());
        existingElectronicDevice.setDescription(electronicDevice.getDescription());
        existingElectronicDevice.setDiscount(electronicDevice.getDiscount());
        existingElectronicDevice.setIsActive(electronicDevice.getIsActive());
        return electronicDeviceRepository.save(existingElectronicDevice);

    }

    public void deleteElectronicDeviceByID(Long id){
        if(!electronicDeviceRepository.existsById(id)){
            throw new IllegalStateException("Electronic Device with ID " + id
                    + "does not exist.");
        }
        electronicDeviceRepository.deleteById(id);
    }



}
