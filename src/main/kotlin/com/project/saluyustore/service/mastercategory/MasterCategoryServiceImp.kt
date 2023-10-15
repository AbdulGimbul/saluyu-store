package com.project.saluyustore.service.mastercategory

import com.project.saluyustore.entity.MasterCategories
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
        return try {
            val categories = masterCategoryRepository.findAll()
            if (categories.isEmpty()) {
                throw Exception("Category is empty")
            }
            HttpResponse.setResp(categories, "Success", categories.size, HttpStatus.OK)
        } catch (e: Exception) {
            return HttpResponse.setResp<String>(message = e.message, total = 0, status = HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    override fun addCategory(categoryRequest: CategoryRequest): ResponseEntity<*> {
        return try {
            val masterCategories = MasterCategories()
            masterCategories.categoryDesc = categoryRequest.categoryDesc
            val saveCategory = masterCategoryRepository.save(masterCategories)
            HttpResponse.setResp(saveCategory, "Success", status = HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp<String>(message = e.message, status = HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}