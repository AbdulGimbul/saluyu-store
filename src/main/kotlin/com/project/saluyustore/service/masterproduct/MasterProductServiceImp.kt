package com.project.saluyustore.service.masterproduct

import com.project.saluyustore.repository.MasterItemsRepository
import com.project.saluyustore.util.HttpResponse
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
@Transactional
class MasterProductServiceImp(private val masterItemsRepository: MasterItemsRepository) : MasterProductService {

    override fun getItems(): ResponseEntity<*> {
        return try {
            val items = masterItemsRepository.findAll();
            if (items.isEmpty()) {
                throw Exception("Items is empty")
            }
            HttpResponse.setResp(items, "Success", items.size, HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp<String>(message = e.message, status = HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

}