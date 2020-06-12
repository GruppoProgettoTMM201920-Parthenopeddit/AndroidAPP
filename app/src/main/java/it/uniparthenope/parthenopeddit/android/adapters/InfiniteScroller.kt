package it.uniparthenope.parthenopeddit.android.adapters

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfiniteScroller(
    private val layoutManager: LinearLayoutManager,
    private val updater: Updater,
    val page_size: Int,
    val visibleThreshold: Int = 10
) : RecyclerView.OnScrollListener() {
    var previousTotal = 0
    var loading = true
    var firstVisibleItem = 0
    var visibleItemCount = 0
    var totalItemCount = 0
    var theresMore = true
    var currentPage = 1

    interface Updater {
        fun updateData(totalItemCount: Int)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.childCount
        totalItemCount = layoutManager.itemCount
        firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }

        if (!loading && theresMore && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            updater.updateData(totalItemCount)
            loading = true
        }
    }
}