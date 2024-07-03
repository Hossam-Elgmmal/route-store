package com.route.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import com.route.data.Synchronizer
import com.route.data.reposetory.BrandRepository
import com.route.data.reposetory.CartRepository
import com.route.data.reposetory.CategoryRepository
import com.route.data.reposetory.ProductRepository
import com.route.data.reposetory.SubCategoryRepository
import com.route.datastore.DataVersion
import com.route.datastore.UserPreferencesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext


@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val categoryRepository: CategoryRepository,
    private val subCategoryRepository: SubCategoryRepository,
    private val brandRepository: BrandRepository,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : CoroutineWorker(appContext, workerParameters), Synchronizer {
    override suspend fun doWork(): Result =
        withContext(Dispatchers.IO) {

            Log.i("SyncWorker", "doWork")
            val syncedSuccessfully = awaitAll(
                async { categoryRepository.sync() },
                async { subCategoryRepository.sync() },
                async { brandRepository.sync() },
                async { productRepository.sync() },
                async { cartRepository.sync() },
            ).all { it }

            if (syncedSuccessfully)
                Result.success()
            else
                Result.retry()
        }

    override suspend fun getDataVersion() =
        userPreferencesRepository.getDataVersion()

    override suspend fun updateDataVersion(
        update: DataVersion.() -> DataVersion
    ) = userPreferencesRepository.updateDataVersion(update)

    companion object {
        fun startUpSyncWork() =
            OneTimeWorkRequestBuilder<DelegatingWorker>()
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setConstraints(syncConstraints)
                .setInputData(SyncWorker::class.delegateData())
                .build()
    }

}
