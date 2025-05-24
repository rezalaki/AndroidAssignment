package ir.miare.androidcodechallenge.data.api

import ir.logicbase.mockfit.Mock
import ir.miare.androidcodechallenge.data.model.response.FakeData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @Mock("data.json")
    @GET("list")
    suspend fun loadFakeData(): Response<List<FakeData>>

}