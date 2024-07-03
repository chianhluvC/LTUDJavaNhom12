package com.team12.DASpring.services;



import com.team12.DASpring.entity.Discount;
import com.team12.DASpring.repository.IDiscountRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DiscountService {

    private final IDiscountRepository discountRepository;

    public List<Discount> getAllDiscounts(){
        return discountRepository.findAll();
    }

    public Optional<Discount> getDiscount(Long id){
        return discountRepository.findById(id);
    }

    public void addDiscount(Discount discount){
        discountRepository.save(discount);
    }

    public void updateDiscount(@NotNull Discount discount){
        Discount existingDiscount = discountRepository.findById(discount.getId())
                .orElseThrow(()->new IllegalStateException("Discount with ID "
                        + discount.getId() +" does not exist."));
        existingDiscount.setName(discount.getName());
        existingDiscount.setDiscountPercent(discount.getDiscountPercent());
        discountRepository.save(existingDiscount);
    }

    public void deleteDiscountById(Long id){
        if(!discountRepository.existsById(id)){
            throw new IllegalStateException("Discount with ID "+id+" does not exist.");
        }
        discountRepository.deleteById(id);
    }
}
