package ir.miare.androidcodechallenge.data.repository

import ir.miare.androidcodechallenge.data.api.ApiService
import ir.miare.androidcodechallenge.data.model.FakeData
import ir.miare.androidcodechallenge.data.repository.FakeDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FakeDataRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : FakeDataRepository {

    override suspend fun loadFakeData(): Flow<Result<List<FakeData>>> = flow {
        val result = apiService.loadFakeData()
        if (result.isSuccessful && result.body() != null) {
            emit(Result.success(result.body()!!))
        } else {
            val throwable = Throwable("API EXCEPTION")
            emit(Result.failure(throwable))
        }
    }

}