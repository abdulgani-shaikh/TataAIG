package com.shaikhabdulgani.practice

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max


class DotsIndicatorDecoration(
    radius: Int,
    padding: Int,
    indicatorHeight: Int,
    @ColorInt colorInactive: Int,
    @ColorInt colorActive: Int
) : RecyclerView.ItemDecoration() {

    companion object{
        const val TAG = "DotsIndicatorDecoration"
    }

    private var indicatorHeight = 0
    private var indicatorItemPadding = 0
    private var radius = 0

    private val inactivePaint = Paint()
    private val activePaint = Paint()

    init {
        val strokeWidth = Resources.getSystem().displayMetrics.density * 1
        this.radius = radius
        inactivePaint.strokeCap = Paint.Cap.ROUND
        inactivePaint.strokeWidth = strokeWidth
        inactivePaint.style = Paint.Style.FILL
        inactivePaint.isAntiAlias = true
        inactivePaint.color = colorInactive

        activePaint.strokeCap = Paint.Cap.ROUND
        activePaint.strokeWidth = strokeWidth
        activePaint.style = Paint.Style.FILL
        activePaint.isAntiAlias = true
        activePaint.color = colorActive

        this.indicatorItemPadding = padding
        this.indicatorHeight = indicatorHeight
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val adapter = parent.adapter ?: return

        val itemCount = adapter.itemCount

        val totalLength = (this.radius * 2 * itemCount).toFloat()
        val paddingBetweenItems =
            (max(0.0, (itemCount - 1).toDouble()) * indicatorItemPadding).toFloat()
        val indicatorTotalWidth = totalLength + paddingBetweenItems
        val indicatorStartX = (parent.width - indicatorTotalWidth) / 2f

        val indicatorPosY = parent.height - indicatorHeight / 2f

        drawInactiveDots(c, indicatorStartX, indicatorPosY, itemCount)

        val activePosition = if (parent.layoutManager is GridLayoutManager) {
            (parent.layoutManager as GridLayoutManager?)!!.findFirstVisibleItemPosition()
        } else if (parent.layoutManager is LinearLayoutManager) {
            (parent.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
        } else {
            return
        }

        if (activePosition == RecyclerView.NO_POSITION) {
            return
        }

        Log.d(TAG,"onDrawOver called")

        parent.layoutManager!!.findViewByPosition(activePosition) ?: return

        drawActiveDot(c, indicatorStartX, indicatorPosY, activePosition)
    }

    private fun drawInactiveDots(
        c: Canvas,
        indicatorStartX: Float,
        indicatorPosY: Float,
        itemCount: Int
    ) {
        val itemWidth = (this.radius * 2 + indicatorItemPadding).toFloat()

        var start = indicatorStartX + radius
        for (i in 0 until itemCount) {
            c.drawCircle(start, indicatorPosY, radius.toFloat(), inactivePaint)
            start += itemWidth
        }
    }

    private fun drawActiveDot(
        c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
        highlightPosition: Int
    ) {
        val itemWidth = (this.radius * 2 + indicatorItemPadding).toFloat()
        val highlightStart = indicatorStartX + radius + itemWidth * highlightPosition
        c.drawCircle(highlightStart, indicatorPosY, radius.toFloat(), activePaint)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = indicatorHeight
    }
}