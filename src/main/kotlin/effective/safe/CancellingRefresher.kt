package effective.safe

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CancellingRefresher(
    private val scope: CoroutineScope,
    private val refreshData: suspend () -> Unit,
) {
    private var refreshJob: Job? = null

    fun refresh() {
        refreshJob?.cancel()
        refreshJob = scope.launch {
            refreshData()
        }
    }
}
