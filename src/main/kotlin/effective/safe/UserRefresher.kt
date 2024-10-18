package effective.safe

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserRefresher(
    private val scope: CoroutineScope,
    private val refreshData: suspend (Int) -> Unit,
) {
    private var refreshJob: Job? = null

    suspend fun refresh(userId: Int) {
        refreshJob?.join()
        refreshJob = scope.launch {
            refreshData(userId)
        }
    }
}
