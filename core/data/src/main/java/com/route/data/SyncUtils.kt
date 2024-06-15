package com.route.data

import android.util.Log
import com.route.datastore.DataVersion
import kotlin.coroutines.cancellation.CancellationException


private suspend fun <T> suspendRunCatching(block: suspend () -> T): Result<T> =
    try {
        Result.success(block())
    } catch (cancellationException: CancellationException) {
        throw cancellationException
    } catch (e: Exception) {

        Log.i(
            "suspendRunCatching",
            "Failed to evaluate a suspendRunCatchingBlock. Returning failure Result",
            e
        )
        Result.failure(e)
    }

interface Syncable {
    suspend fun syncWith(synchronizer: Synchronizer): Boolean
}

interface Synchronizer {
    suspend fun getDataVersion(): DataVersion
    suspend fun updateDataVersion(update: DataVersion.() -> DataVersion)
    suspend fun Syncable.sync() = this@sync.syncWith(this@Synchronizer)
}

suspend fun <T> Synchronizer.dataVersionSync(
    versionReader: (DataVersion) -> Int,
    fetchDataList: suspend (Int) -> List<T>,
    updateModelData: suspend (List<T>) -> Unit,
    versionUpdater: DataVersion.(Int) -> DataVersion
) = suspendRunCatching {

    val currentVersion = versionReader(getDataVersion())

    val newDataList = fetchDataList(currentVersion)

    updateModelData(newDataList)

    val latestVersion = currentVersion + 1

    updateDataVersion(
        update = {
            versionUpdater(latestVersion)
        }
    )

}.isSuccess