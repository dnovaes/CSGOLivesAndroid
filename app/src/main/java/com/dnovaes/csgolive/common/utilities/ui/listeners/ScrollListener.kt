package com.dnovaes.csgolive.common.utilities.ui.listeners

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.Duration
import java.time.LocalTime

class InfiniteScrollListener(
    private val delayBetweenCallbackTrigger: Long = 4,
    private val onReachBottom: () -> Unit
): RecyclerView.OnScrollListener() {

    private var lastTimeBottomScrollWasReached = LocalTime.now().minusSeconds(delayBetweenCallbackTrigger)

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
            val dateNow = LocalTime.now()
            if (dateNow.has5secondsPassedSinceLastScroll()) {
                lastTimeBottomScrollWasReached = dateNow
                onReachBottom()
            }
        }
    }

    private fun LocalTime.has5secondsPassedSinceLastScroll(): Boolean
        = (Duration.between(lastTimeBottomScrollWasReached, this) > Duration.ofSeconds(5))

}