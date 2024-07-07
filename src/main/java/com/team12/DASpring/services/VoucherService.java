package com.team12.DASpring.services;



import com.team12.DASpring.entity.ElectronicDevice;
import com.team12.DASpring.entity.Voucher;
import com.team12.DASpring.repository.IVoucherRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VoucherService {

    private final IVoucherRepository voucherRepository;

    public List<Voucher> getAllVouchers(){
        return voucherRepository.findAll();
    }

    public Optional<Voucher> getVoucher(Long id){
        return voucherRepository.findById(id);
    }

    public Optional<Voucher> getVoucherByCode(String code){
        return getAllVouchers().stream().filter(p->p.getCode().equals(code)).findFirst();
    }

    public void addVoucher(Voucher voucher){
        if(voucher.getIsActive()==null)
            voucher.setIsActive(false);
        voucherRepository.save(voucher);
    }

    public void updateVoucher(@NotNull Voucher voucher){
        Voucher existingVoucher = voucherRepository.findById(voucher.getId())
                .orElseThrow(()->new IllegalStateException("Voucher with ID "
                        + voucher.getId() +" does not exist."));
        if(voucher.getIsActive()==null)
            voucher.setIsActive(false);
        existingVoucher.setDescription(voucher.getDescription());
        existingVoucher.setVoucherPercent(voucher.getVoucherPercent());
        existingVoucher.setCode(voucher.getCode());
        existingVoucher.setEndDate(voucher.getEndDate());
        existingVoucher.setMaxVoucher(voucher.getMaxVoucher());
        existingVoucher.setIsActive(voucher.getIsActive());
        voucherRepository.save(existingVoucher);
    }

    public void deleteVoucherById(Long id){
        if(!voucherRepository.existsById(id)){
            throw new IllegalStateException("Voucher with ID "+id+" does not exist.");
        }
        voucherRepository.deleteById(id);
    }

    public List<Voucher> getVoucherIsActive(){
        return voucherRepository.getVoucherByIsActive(true);
    }


}
