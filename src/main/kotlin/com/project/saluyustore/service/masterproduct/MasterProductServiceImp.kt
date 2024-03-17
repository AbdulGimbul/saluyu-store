package com.project.saluyustore.service.masterproduct

import com.project.saluyustore.config.ValidationUtil
import com.project.saluyustore.entity.MasterProduct
import com.project.saluyustore.entity.toProductResponse
import com.project.saluyustore.model.request.CreateProductRequest
import com.project.saluyustore.model.request.UpdateProductRequest
import com.project.saluyustore.model.response.ProductResponse
import com.project.saluyustore.repository.MasterCategoryRepository
import com.project.saluyustore.repository.MasterProductRepository
import com.project.saluyustore.repository.MasterUserRepository
import com.project.saluyustore.util.HttpResponse
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
@Transactional
class MasterProductServiceImp(
    private val masterProductRepository: MasterProductRepository,
    private val masterUserRepository: MasterUserRepository,
    private val masterCategoryRepository: MasterCategoryRepository,
    private val validationUtil: ValidationUtil
) : MasterProductService {

    override fun create(productRequest: CreateProductRequest): ProductResponse {
        validationUtil.validate(productRequest)

        val user = masterUserRepository.findById(productRequest.userId).orElseThrow {
            RuntimeException("User not found for ID ${productRequest.userId}")
        }

        val category = masterCategoryRepository.findById(productRequest.categoryId).orElseThrow {
            RuntimeException("Category not found for ID ${productRequest.categoryId}")
        }

        val masterProduct = MasterProduct(
            userId = user,
            productName = productRequest.productName,
            categoryId = category,
            unit = productRequest.unit,
            unitPrice = productRequest.unitPrice,
            productStock = productRequest.productStock,
            pictureProduct = productRequest.pictureProduct,
            updatedAt = java.util.Date()
        )

        masterProductRepository.save(masterProduct)

        return masterProduct.toProductResponse()
    }


    override fun getProducts(): ResponseEntity<*> {
        return try {
            val items = masterProductRepository.findAll()
            if (items.isEmpty()) {
                throw Exception("Items is empty")
            }
            val productResponses = items.map { data -> data.toProductResponse() }
            HttpResponse.setResp(productResponses, "Success", productResponses.size, HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp<String>(message = e.message, status = HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    override fun update(productId: Int, updateProductRequest: UpdateProductRequest): ProductResponse {
        val product = masterProductRepository.findById(productId).orElseThrow {
            RuntimeException("Product not found for ID $productId")
        }

        product.apply {
            updateProductRequest.productName?.let { productName = it }
            updateProductRequest.categoryId?.let {
                val category = masterCategoryRepository.findById(it).orElseThrow {
                    RuntimeException("Category not found for ID $it")
                }
                categoryId = category
            }
            updateProductRequest.unit?.let { unit = it }
            updateProductRequest.unitPrice?.let { unitPrice = it }
            updateProductRequest.productStock?.let { productStock = it }
            updateProductRequest.pictureProduct?.let { pictureProduct = it }
        }

        masterProductRepository.save(product)

        return product.toProductResponse()
    }

    override fun initData(): List<ProductResponse> {
        val data = listOf(

            // Sayuran
            CreateProductRequest(
                productName = "Bayam",
                categoryId = 1,
                unitPrice = 4000,
                productStock = 10,
                unit = "Ikat",
                userId = 852,
                pictureProduct = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcbZn6k4E95SKum3zHik6jW9O9lR3hXADFTEdr32DQzXXPTkBdlfDuuMZfaTdwmOoa9os&usqp=CAU"
            ),
            CreateProductRequest(
                productName = "Kangkung",
                categoryId = 1,
                unitPrice = 3000,
                productStock = 20,
                unit = "Ikat",
                userId = 852,
                pictureProduct = "https://i0.wp.com/umsu.ac.id/berita/wp-content/uploads/2023/07/Kangkung.jpg?fit=1200%2C900&ssl=1"
            ),
            CreateProductRequest(
                productName = "Wortel",
                categoryId = 1,
                unitPrice = 5000,
                productStock = 15,
                unit = "Kg",
                userId = 852,
                pictureProduct = "https://pict-a.sindonews.net/dyn/850/pena/news/2023/11/12/155/1249729/mengenal-karotenemia-keracunan-wortel-yang-bikin-warna-kulit-jadi-kuning-bqs.jpg"
            ),
            CreateProductRequest(
                productName = "Kentang",
                categoryId = 1,
                unitPrice = 6000,
                productStock = 25,
                unit = "Kg",
                userId = 852,
                pictureProduct = "https://i0.wp.com/umsu.ac.id/berita/wp-content/uploads/2023/08/kentang-manfaat-dan-kandungan-gizi-untuk-kesehatan.jpg?fit=960%2C640&ssl=1"
            ),
            CreateProductRequest(
                productName = "Buncis",
                categoryId = 1,
                unitPrice = 4500,
                productStock = 12,
                unit = "Kg",
                userId = 852,
                pictureProduct = "https://res.cloudinary.com/dk0z4ums3/image/upload/v1645002933/attached_image/manfaat-buncis-ternyata-lebih-kaya-daripada-sayuran-kacang-sejenis.jpg"
            ),

            // Buah-buahan
            CreateProductRequest(
                productName = "Pisang",
                categoryId = 2,
                unitPrice = 5000,
                productStock = 30,
                unit = "Sisir",
                userId = 852,
                pictureProduct = "https://asset-a.grid.id/crop/0x0:0x0/945x630/photo/2020/10/25/1977343241.jpg"
            ),
            CreateProductRequest(
                productName = "Apel",
                categoryId = 2,
                unitPrice = 7000,
                productStock = 25,
                unit = "Buah",
                userId = 852,
                pictureProduct = "https://asset.kompas.com/crops/smfd25xgXRE3HpMLb2aamPeulYM=/21x0:1476x970/1200x800/data/photo/2022/08/30/630d7ae5d041f.jpg"
            ),
            CreateProductRequest(
                productName = "Jeruk",
                categoryId = 2,
                unitPrice = 6000,
                productStock = 20,
                unit = "Buah",
                userId = 852,
                pictureProduct = "https://awsimages.detik.net.id/customthumb/2015/11/07/297/134520_mandarincover.jpg?w=600&q=90"
            ),
            CreateProductRequest(
                productName = "Anggur",
                categoryId = 2,
                unitPrice = 10000,
                productStock = 15,
                unit = "Kg",
                userId = 852,
                pictureProduct = "https://d1vbn70lmn1nqe.cloudfront.net/prod/wp-content/uploads/2021/10/25032152/Ini-X-Manfaat-Buah-Anggur-yang-Jarang-Diketahui-01.jpg.webp"
            ),
            CreateProductRequest(
                productName = "Melon",
                categoryId = 2,
                unitPrice = 12000,
                productStock = 10,
                unit = "Kg",
                userId = 852,
                pictureProduct = "https://www.melissas.com/cdn/shop/products/image-of-crown-melon-fruit-30682105085996_512x512.jpg?v=1656017228"
            ),

            // Umbi-umbian
            CreateProductRequest(
                productName = "Singkong",
                categoryId = 3,
                unitPrice = 2000,
                productStock = 35,
                unit = "Kg",
                userId = 852,
                pictureProduct = "https://ik.imagekit.io/waters2021/sehataqua/uploads/20230818062343_original.jpg?tr=w-660,h-371,q-50"
            ),
            CreateProductRequest(
                productName = "Ubi Jalar",
                categoryId = 3,
                unitPrice = 3000,
                productStock = 30,
                unit = "Kg",
                userId = 852,
                pictureProduct = "https://img-cdn.medkomtek.com/pcHSwZaXyJVk-vK8xjVjNkT0Zsg=/690x387/smart/filters:quality(100):format(webp)/article/KSvPMRrp7E5X8oLTsao2y/original/029007600_1483460306-Benarkah-Ubi-Bisa-Bantu-Atasi-Osteoporosis.jpg"
            ),
            CreateProductRequest(
                productName = "Talas",
                categoryId = 3,
                unitPrice = 4000,
                productStock = 25,
                unit = "Kg",
                userId = 852,
                pictureProduct = "https://asset.kompas.com/crops/qTM1f6xB6L4AosTWsf-qkRf3CS8=/48x17:952x620/750x500/data/photo/2020/04/22/5e9fd1879cfc6.jpg"
            ),
            CreateProductRequest(
                productName = "Kentang",
                categoryId = 3,
                unitPrice = 6000,
                productStock = 25,
                unit = "Kg",
                userId = 852,
                pictureProduct = "https://asset.kompas.com/crops/CQ4in1DzrNCB1YXeB-FrzW6af8o=/9x43:909x643/750x500/data/photo/2023/03/14/640ffdb851f50.jpg"
            ),
            CreateProductRequest(
                productName = "Gembili",
                categoryId = 3,
                unitPrice = 5000,
                productStock = 20,
                unit = "Kg",
                userId = 852,
                pictureProduct = "https://asset-2.tstatic.net/manado/foto/bank/images/apa-itu-gembili-sumber-karbohidrat-yang-baik-untuk-tubuh.jpg"
            ),

            // Pakan
            CreateProductRequest(
                productName = "Pakan Ayam",
                categoryId = 4,
                unitPrice = 15000,
                productStock = 50,
                unit = "Pcs",
                userId = 852,
                pictureProduct = "https://image1ws.indotrading.com/s3/productimages/webp/co251460/p1109200/w425-h425/94f4351b-4817-46a0-b4f2-9271aabe324d.jpeg"
            ),
            CreateProductRequest(
                productName = "Pakan Sapi",
                categoryId = 4,
                unitPrice = 20000,
                productStock = 40,
                unit = "Pcs",
                userId = 852,
                pictureProduct = "https://www.sikumis.com/media/frontend/products/CALFEED_(1).jpg"
            ),
            CreateProductRequest(
                productName = "Pakan Ikan",
                categoryId = 4,
                unitPrice = 12000,
                productStock = 35,
                unit = "Pcs",
                userId = 852,
                pictureProduct = "https://gratisongkir-storage.com/products/900x900/1x3SqRcHa6G6.jpg"
            ),
            CreateProductRequest(
                productName = "Pakan Kelinci",
                categoryId = 4,
                unitPrice = 10000,
                productStock = 30,
                unit = "Pcs",
                userId = 852,
                pictureProduct = "https://makassarhobi.com/wp-content/uploads/2020/04/vital-rabbit-zal.jpg"
            ),
            CreateProductRequest(
                productName = "Pakan Kucing",
                categoryId = 4,
                unitPrice = 18000,
                productStock = 25,
                unit = "Pcs",
                userId = 852,
                pictureProduct = "https://images.tokopedia.net/img/cache/500-square/VqbcmM/2022/11/21/bc056380-6528-4957-8dea-930d77d8f786.jpg"
            )
        )

        val transform1: (CreateProductRequest) -> ProductResponse = { req ->
            create(req)
        }
        return data.map(transform1)
    }
}