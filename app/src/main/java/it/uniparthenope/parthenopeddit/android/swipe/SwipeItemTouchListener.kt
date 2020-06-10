package it.uniparthenope.parthenopeddit.android.swipe

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchUIUtil
import androidx.recyclerview.widget.RecyclerView

interface SwipeItemTouchListener {
    fun onSwiped(holder: RecyclerView.ViewHolder, direction: Int)
    fun onSelectedChanged(holder: RecyclerView.ViewHolder?, actionState: Int, uiUtil: ItemTouchUIUtil)
    fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView, holder: RecyclerView.ViewHolder, dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
        uiUtil: ItemTouchUIUtil
    )

    fun onChildDrawOver(
        c: Canvas, recyclerView: RecyclerView, holder: RecyclerView.ViewHolder?, dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
        uiUtil: ItemTouchUIUtil
    )

    fun clearView(recyclerView: RecyclerView, holder: RecyclerView.ViewHolder, uiUtil: ItemTouchUIUtil)
}