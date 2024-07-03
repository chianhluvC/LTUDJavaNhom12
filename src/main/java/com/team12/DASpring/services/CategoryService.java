package com.team12.DASpring.services;


import com.team12.DASpring.entity.Category;
import com.team12.DASpring.repository.ICategoryRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final ICategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategory(Long id){
        return categoryRepository.findById(id);
    }

    public void addCategory(Category category){
        categoryRepository.save(category);
    }

    public void updateCategory(@NotNull Category category){
        Category existingCategory = categoryRepository.findById(category.getId())
                .orElseThrow(()->new IllegalStateException("Category with ID "
                + category.getId() +" does not exist."));
        existingCategory.setName(category.getName());
        categoryRepository.save(existingCategory);
    }

    public void deleteCategoryById(Long id){
        if(!categoryRepository.existsById(id)){
            throw new IllegalStateException("Category with ID "+id+" does not exist.");
        }
        categoryRepository.deleteById(id);
    }


}
