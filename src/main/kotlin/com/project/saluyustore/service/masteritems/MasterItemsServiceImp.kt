package com.project.saluyustore.service.masteritems

import com.project.saluyustore.repository.MasterItemsRepository
import com.project.saluyustore.util.HttpResponse
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
@Transactional
class MasterItemsServiceImp(private val masterItemsRepository: MasterItemsRepository) : MasterItemsService {

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