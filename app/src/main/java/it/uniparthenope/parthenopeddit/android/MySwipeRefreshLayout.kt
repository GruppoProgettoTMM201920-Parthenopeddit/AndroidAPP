package it.uniparthenope.parthenopeddit.android

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MySwipeRefreshLayout : SwipeRefreshLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    var child:View? = null

    override fun canChildScrollUp(): Boolean {
        return child?.canScrollVertically(-1)?:false
    }
}