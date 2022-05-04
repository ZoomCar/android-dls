package com.zoomcar.uikit.imagesegmentedcontrol

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zoomcar.uikit.interfaces.IRadioButton
import com.zoomcar.uikit.segmentedcontrol.ZSegmentedControlAdapter
import com.zoomcar.util.UiUtil
import com.zoomcar.zoomdls.R

class ImageSegmentedControlItemDecorator(context: Context) : RecyclerView.ItemDecoration() {
    private val divider: Drawable? = ContextCompat.getDrawable(context, R.drawable.horizontal_divider_line)
    private val dividerMargin = UiUtil.dpToPixels(
            context.resources.getDimensionPixelSize(R.dimen.segmented_control_divider_margin),
            context)

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val top = parent.paddingTop + dividerMargin
        val bottom = parent.height - parent.paddingBottom - dividerMargin

        val childCount = parent.childCount
        divider?.let { verticalDivider ->
            for (i in 0 until childCount) {
                val isDividerReq = isDividerReqForViewHolder(i, parent)
                if (isDividerReq) {
                    val child = parent.getChildAt(i)
                    val params = child
                            .layoutParams as RecyclerView.LayoutParams
                    val left = child.right + params.rightMargin
                    val right = left + verticalDivider.intrinsicWidth
                    verticalDivider.setBounds(left, top, right, bottom)
                    verticalDivider.draw(c)
                }
            }
        }
    }

    private fun isDividerReqForViewHolder(position: Int, parent: RecyclerView): Boolean {
        val itemCount = parent.childCount
        val child = parent.getChildAt(position)
        val childViewHolder = parent.getChildViewHolder(child)
        if (position == itemCount - 1
                || (childViewHolder is IRadioButton && childViewHolder.isSelected())) {
            return false
        } else {
            val nextChild = parent.getChildAt(position + 1)
            val nextChildViewHolder = parent.getChildViewHolder(nextChild)
            if ((nextChildViewHolder as ZImageSegmentedControlAdapter.ImageSegmentedButtonViewHolder).isSelected())
                return false
        }
        return true
    }
}