package com.team12.DASpring.services;


import com.team12.DASpring.entity.Discount;
import com.team12.DASpring.entity.ElectronicDevice;
import com.team12.DASpring.repository.IDiscountRepository;
import com.team12.DASpring.repository.IElectronicDeviceRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional

public class ElectronicDeviceService {

    @Autowired
    private  IElectronicDeviceRepository electronicDeviceRepository;
    @Autowired
    private  IDiscountRepository discountRepository;


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
        electronicDevice.setStarReview(0);
        electronicDevice.setQuantityReview(0);
        electronicDevice.setAverageStar(0);
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
        existingElectronicDevice.setStarReview(electronicDevice.getStarReview());
        existingElectronicDevice.setQuantityReview(electronicDevice.getQuantityReview());
        existingElectronicDevice.setAverageStar(electronicDevice.getAverageStar());
        return electronicDeviceRepository.save(existingElectronicDevice);

    }

    public void deleteElectronicDeviceByID(Long id){
        if(!electronicDeviceRepository.existsById(id)){
            throw new IllegalStateException("Electronic Device with ID " + id
                    + "does not exist.");
        }
        electronicDeviceRepository.deleteById(id);
    }

    public List<ElectronicDevice> searchElectronicDevice(String name){
        return electronicDeviceRepository.search(name);
    }

    public Page<ElectronicDevice> getAll(Integer pageNo){
        Pageable pageable = PageRequest.of(pageNo-1,6);
        return electronicDeviceRepository.findAll(pageable);
    }

    public void updateStarRatingAndQuantityReview(Long id, int star, int quantity){
        ElectronicDevice electronicDevice = electronicDeviceRepository.findById(id).orElseThrow(()->new IllegalArgumentException("can't not find device with id: "+id));
        int existingStar = electronicDevice.getStarReview();
        int existingQuantity = electronicDevice.getQuantityReview();
        electronicDevice.setStarReview(existingStar+star);
        electronicDevice.setQuantityReview(existingQuantity+quantity);
        electronicDeviceRepository.save(electronicDevice);
    }

    public void saveAverageStar(Long id){
        ElectronicDevice electronicDevice = electronicDeviceRepository.findById(id).orElseThrow(null);
        if(electronicDevice.getQuantityReview()==0)
            electronicDevice.setAverageStar(0);
        else {
            int average = (int)electronicDevice.getStarReview() / electronicDevice.getQuantityReview();
            electronicDevice.setAverageStar(average);
        }
        electronicDeviceRepository.save(electronicDevice);
    }



}
