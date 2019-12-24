package com.dotbin.sudoku.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

typealias NumClicked = ((Int) -> Unit)

class NumKeyBoardView : View {

    companion object {
        const val TAG = "NumKeyBoardView"
    }

    private val linePaint = Paint()
    private val textPaint = Paint()
    private val selectedPaint = Paint()
    private var mBackground = 0
    private var selectedNum = 11
    private var numClicked: NumClicked? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    @SuppressLint("ResourceType")
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mBackground = Color.rgb(0x36, 36, 36)
        textPaint.color = Color.WHITE
        selectedPaint.color = Color.rgb(0x91, 0x8E, 0x8E)
        linePaint.color = Color.rgb(0x1c, 0x1c, 0x1c)
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //canvas?.drawColor(mBackground)
        val cellHeight = height / 4F
        val cellWidth = width / 3F
        val bigCellWith = width / 2F
        val lx = ((selectedNum - 1) % 3)
        val ly = ((selectedNum - 1) / 3)
        textPaint.textSize = cellHeight / 2F
        if (selectedNum < 10) {
            canvas?.drawRect(
                lx * cellWidth,
                ly * cellHeight,
                (lx + 1) * cellWidth,
                (ly + 1) * cellHeight,
                selectedPaint
            )
        } else {
            canvas?.drawRect(
                lx * bigCellWith,
                ly * cellHeight,
                (lx + 1) * bigCellWith,
                (ly + 1) * cellHeight,
                selectedPaint
            )
        }

        for (i in 1..9) {
            val dx = ((i - 1) % 3)
            val dy = ((i - 1) / 3)
            val centerX = (2 * dx + 1) * cellWidth / 2f
            val centerY = (2 * dy + 1) * cellHeight / 2f
            canvas?.drawText(
                i.toString(),
                centerX,
                centerY - textPaint.ascent() / 2f - textPaint.descent() / 2f,
                textPaint
            )
        }

        canvas?.drawText(
            "C",
            (2 * 0 + 1) * bigCellWith / 2f,
            (2 * 3 + 1) * cellHeight / 2f - textPaint.ascent() / 2f - textPaint.descent() / 2f,
            textPaint
        )
        canvas?.drawText(
            "S",
            (2 * 1 + 1) * bigCellWith / 2f,
            (2 * 3 + 1) * cellHeight / 2f - textPaint.ascent() / 2f - textPaint.descent() / 2f,
            textPaint
        )

        for (i in 1..3) {
            canvas?.drawLine(
                i.toFloat() * cellWidth,
                0F,
                i.toFloat() * cellWidth,
                3F * cellHeight,
                linePaint
            )
            canvas?.drawLine(
                0F,
                i.toFloat() * cellHeight,
                3F * cellWidth,
                i.toFloat() * cellHeight,
                linePaint
            )
        }
        canvas?.drawLine(
            1.5F * cellWidth,
            3 * cellHeight,
            1.5F * cellWidth,
            4 * cellHeight,
            linePaint
        )
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val cellHeight = height / 4f
        val cellWidth = width / 3f
        val bigCellWith = width / 2f
        val col = (((event?.y ?: 0f) - paddingTop) / cellHeight).toInt()
        val row = (((event?.x ?: 0f) - paddingLeft) / (if (col < 3) cellWidth else bigCellWith)).toInt()
        when (event?.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP -> {
                selectedNum = (col * 3) + row+1
                numClicked?.invoke(selectedNum)
            }
            else -> return super.onTouchEvent(event)
        }
        postInvalidate()
        return true
    }

    fun getNum() = selectedNum

    fun setNumclickListener(listen: NumClicked){
        numClicked=listen
    }
}