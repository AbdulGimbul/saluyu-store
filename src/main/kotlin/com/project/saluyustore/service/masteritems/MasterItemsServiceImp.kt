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

    override fun getData(): ResponseEntity<*> {
        val dataResp = mutableMapOf<String, Any>()
        dataResp["name"] = "Saluyu Store"
        dataResp["description"] = "Saluyu Store is an online store that sells various kinds of products"
        dataResp["address"] = "Jl. Raya Cipadung No. 9, Cibiru, Bandung, Jawa Barat"
        dataResp["phone"] = 6281234567890

        return HttpResponse.setResp(dataResp, "Success", HttpStatus.OK)
    }

}