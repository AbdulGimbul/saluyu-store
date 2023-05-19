package com.project.saluyustore.service.mastercategory

import com.project.saluyustore.entity.MasterCategory
import com.project.saluyustore.model.request.CategoryRequest
import com.project.saluyustore.repository.MasterCategoryRepository
import com.project.saluyustore.util.HttpResponse
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
@Transactional
class MasterCategoryServiceImp(val masterCategoryRepository: MasterCategoryRepository) : MasterCategoryService {
    override fun getCategory(): ResponseEntity<*> {
        val categories = masterCategoryRepository.findAll()
        if (categories.isEmpty()) {
            return HttpResponse.setResp(null, "Failed", 0, HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return HttpResponse.setResp(categories, "Success", categories.size, HttpStatus.OK)
    }

    override fun addCategory(categoryRequest: CategoryRequest): ResponseEntity<*> {
        val masterCategory = MasterCategory()
        masterCategory.categoryId = masterCategoryRepository.getSeqCategoryId()
        masterCategory.categoryDesc = categoryRequest.categoryDesc
        val saveCategory = masterCategoryRepository.save(masterCategory)
        return HttpResponse.setResp(saveCategory, "Success", HttpStatus.OK)
    }
}