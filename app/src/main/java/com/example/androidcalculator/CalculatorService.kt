package com.example.androidcalculator

import retrofit2.http.*

data class PostOperationRequest(val expression: String, val result: Double, val timestamp: Long)
data class PostoperationResponse(val message: String)

data class GetOperationsResponse(
    val uuid: String,
    val expression: String,
    val result: Double,
    val timestamp: Long
)
data class DeleteOperationByIdResponse(val message: String)

interface CalculatorService {
    @Headers("apiKey:8270435acfead39ccb03e8aafbf37c49359dfbbcac4ef4769ae82c9531da0e17")
    @POST("operations")
    suspend fun insert(@Body body:PostOperationRequest) : PostoperationResponse

    @Headers("apiKey:8270435acfead39ccb03e8aafbf37c49359dfbbcac4ef4769ae82c9531da0e17")
    @GET("operations")
    suspend fun getAll(): List<GetOperationsResponse>

    @Headers("apiKey:8270435acfead39ccb03e8aafbf37c49359dfbbcac4ef4769ae82c9531da0e17")
    @DELETE("operations/{uuid}")
    suspend fun deleteById(@Path("uuid") uuid: String) : DeleteOperationByIdResponse

}