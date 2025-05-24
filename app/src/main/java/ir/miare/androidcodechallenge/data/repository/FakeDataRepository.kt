package ir.miare.androidcodechallenge.data.repository

import ir.miare.androidcodechallenge.data.model.FakeData
import kotlinx.coroutines.flow.Flow


interface FakeDataRepository {
    suspend fun loadFakeData(): Flow<Result<List<FakeData>>>
}