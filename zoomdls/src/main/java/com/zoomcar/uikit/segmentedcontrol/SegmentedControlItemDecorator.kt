package com.zoomcar.uikit.segmentedcontrol

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zoomcar.util.UiUtil

class SegmentedControlItemDecorator(context: Context) : RecyclerView.ItemDecoration() {
    private val divider: Drawable? = ContextCompat.getDrawable(context, com.zoomcar.zoomdls.R.drawable.horizontal_divider_line)
    private val dividerMargin = UiUtil.dpToPixels(8, context)

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val top = parent.paddingTop + dividerMargin
        val bottom = parent.height - parent.paddingBottom - dividerMargin

        val childCount = parent.childCount
        divider?.let { horizontalDivider ->
            for (i in 0 until childCount) {
                val isDividerReq = isDividerReqForViewHolder(i, parent)
                if(isDividerReq) {
                    val child = parent.getChildAt(i)
                    val params = child
                            .layoutParams as RecyclerView.LayoutParams
                    val left = child.right + params.rightMargin
                    val right = left + horizontalDivider.intrinsicWidth
                    horizontalDivider.setBounds(left, top, right, bottom)
                    horizontalDivider.draw(c)
                }
            }
        }
    }

    private fun isDividerReqForViewHolder(position: Int, parent: RecyclerView): Boolean {
        val itemCount = parent.childCount
        val child = parent.getChildAt(position)
        val childViewHolder = parent.getChildViewHolder(child)
        if(position == itemCount - 1
                || (childViewHolder as ZSegmentedControlAdapter.SegmentedButtonViewHolder).isSelected()) {
            return false
        } else {
            val nextChild = parent.getChildAt(position + 1)
            val nextChildViewHolder = parent.getChildViewHolder(nextChild)
            if((nextChildViewHolder as ZSegmentedControlAdapter.SegmentedButtonViewHolder).isSelected())
                return false
        }
        return true
    }
}