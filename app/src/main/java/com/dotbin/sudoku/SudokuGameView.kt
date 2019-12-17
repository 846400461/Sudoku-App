package com.dotbin.sudoku

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

typealias OnCellClickedListener = ((Boolean) -> Int)

class SudokuGameView : View {
    companion object {
        const val TAG = "SudokuGameView"
        const val DEFAULT_GAME_SIZE = 100
    }

    private var onCellClicked: OnCellClickedListener? = null
    private val sectionLine = Paint()
    private val cellLine = Paint()
    private val cellPaint = Paint()
    private val cellTextPaint = Paint()
    private var cellHeight = 0F
    private var cellWidth = 0F
    var cellIfs = Array(9) {
        Array(9) {
            SudokuCell(
                Color.BLACK,
                0,
                true,
                Color.WHITE
            )
        }
    }
    private var textColor = 0
    private var errorTextColor = 0
    private var gameBackgroundColor = 0
    private var cellSameTextColor = 0
    private var cellSameXYColor = 0
    private var selectedCellColor = 0
    private var backgroundDisable = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        Log.i(TAG, "third constructor")
        val a = context.obtainStyledAttributes(attrs, R.styleable.SudokuGameView)
        sectionLine.color = a.getColor(R.styleable.SudokuGameView_sectionLineColor, Color.GRAY)
        cellLine.color = a.getColor(R.styleable.SudokuGameView_cellLineColor, Color.DKGRAY)
        gameBackgroundColor = a.getColor(R.styleable.SudokuGameView_backgroundColor, Color.rgb(54,54,54))
        cellSameTextColor =
            a.getColor(R.styleable.SudokuGameView_backgroundSameText, Color.rgb(159, 182, 205))
        cellSameXYColor =
            a.getColor(R.styleable.SudokuGameView_backgroundSameXY, Color.rgb(0x57, 0x70, 0xb2))
        selectedCellColor =
            a.getColor(R.styleable.SudokuGameView_selectedCellColor, Color.rgb(176, 224, 230))
        cellTextPaint.color = a.getColor(R.styleable.SudokuGameView_textColor, Color.WHITE)
        backgroundDisable =
            a.getColor(R.styleable.SudokuGameView_backgroundDisable, Color.rgb(105, 105, 105))
        errorTextColor = a.getColor(R.styleable.SudokuGameView_errorTextColor, Color.RED)
        textColor = a.getColor(R.styleable.SudokuGameView_textColor, Color.WHITE)
        a.recycle()
        sectionLine.isAntiAlias = true
        cellLine.isAntiAlias = true
        cellPaint.isAntiAlias = true
        cellTextPaint.isAntiAlias = true
        for (x in 0 until cellIfs.count()) {
            for (y in 0 until cellIfs[x].count())
                cellIfs[x][y].backgroundColor =
                    if (cellIfs[x][y].enabled) gameBackgroundColor else backgroundDisable
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        var width = if (widthMode == MeasureSpec.EXACTLY) widthSize else {
            if (widthMode == MeasureSpec.AT_MOST && DEFAULT_GAME_SIZE > widthSize) widthSize
            else DEFAULT_GAME_SIZE
        }
        var height = if (heightMode == MeasureSpec.EXACTLY) heightSize else {
            if (heightMode == MeasureSpec.AT_MOST && DEFAULT_GAME_SIZE > heightSize) heightSize
            else DEFAULT_GAME_SIZE
        }

        if (widthSize < heightSize) {
            height = width
        } else {
            width = height
        }
        setMeasuredDimension(width, height)
        cellHeight = (height - paddingBottom - paddingTop) / 9f
        cellWidth = (width - paddingLeft - paddingRight) / 9f
        cellLine.strokeWidth = cellWidth / 30f
        sectionLine.strokeWidth = cellWidth / 20f
        cellTextPaint.textSize = cellWidth / 2f
        cellTextPaint.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //draw cell
        for (x in cellIfs.indices) {
            for (y in cellIfs[x].indices) {
                cellPaint.color = cellIfs[x][y].backgroundColor
                canvas?.drawRect(
                    x * cellWidth,
                    y * cellHeight,
                    (x + 1) * cellWidth,
                    (y + 1) * cellHeight,
                    cellPaint
                )
                //draw text
                val centerX = (2 * x + 1) * cellWidth / 2f
                val centerY = (2 * y + 1) * cellHeight / 2f
                cellTextPaint.color = cellIfs[x][y].valueColor
                canvas?.drawText(
                    if (cellIfs[x][y].value == 0) "" else cellIfs[x][y].value.toString(),
                    centerX,
                    centerY - cellTextPaint.ascent() / 2f - cellTextPaint.descent() / 2f,
                    cellTextPaint
                )
            }
        }
        //draw line
        for (i in 0..9) {
            canvas?.drawLine(i * cellWidth, 0F, i * cellWidth, 9 * cellHeight, cellLine)
            canvas?.drawLine(0F, i * cellHeight, 9 * cellWidth, i * cellHeight, cellLine)
        }
        for (i in 1..2) {
            canvas?.drawLine(i * 3 * cellWidth, 0F, i * 3 * cellWidth, 9 * cellWidth, sectionLine)
            canvas?.drawLine(0F, i * 3 * cellHeight, 9 * cellWidth, i * 3 * cellHeight, sectionLine)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        val row = (((event?.x ?: 0f) - paddingLeft) / cellWidth).toInt()
        val col = (((event?.y ?: 0f) - paddingTop) / cellHeight).toInt()
        if (row >= 9 || col >= 9 || row < 0 || col < 0)
            return false
        when (event?.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                handleActionMove(row, col)
                handleSameValue(row, col)
            }
            MotionEvent.ACTION_UP -> {
                handleActionUp(row, col)
                val lastValue = cellIfs[row][col].value
                onCellClicked?.let {
                    val cellValue = it(cellIfs[row][col].enabled)
                    if (cellValue in 1..9)
                        cellIfs[row][col].value = cellValue
                    1
                }
                handleSameValue(row, col)
                handleConflictValue(row, col, lastValue)
            }
            else -> return super.onTouchEvent(event)
        }
        postInvalidate()
        return true
    }

    private fun handleActionMove(row: Int, col: Int) {
        for (dx in cellIfs.indices) {
            for (dy in cellIfs[dx].indices) {
                cellIfs[dx][dy].backgroundColor =
                    if (cellIfs[dx][dy].enabled) gameBackgroundColor else backgroundDisable
            }
        }
        for (i in cellIfs.indices) {
            cellIfs[row][i].backgroundColor = cellSameXYColor
            cellIfs[i][col].backgroundColor = cellSameXYColor
        }
        cellIfs[row][col].backgroundColor = selectedCellColor
    }

    private fun handleActionUp(row: Int, col: Int) {
        for (dx in cellIfs.indices) {
            for (dy in cellIfs[dx].indices) {
                cellIfs[dx][dy].backgroundColor =
                    if (cellIfs[dx][dy].enabled) gameBackgroundColor else backgroundDisable
            }
        }
        cellIfs[row][col].backgroundColor = selectedCellColor
    }

    private fun handleSameValue(row: Int, col: Int) {
        for (dx in cellIfs.indices) {
            for (dy in cellIfs[dx].indices) {
                if (cellIfs[dx][dy].value == cellIfs[row][col].value && cellIfs[dx][dy].value != 0)
                    cellIfs[dx][dy].backgroundColor = cellSameTextColor
            }
        }
        cellIfs[row][col].backgroundColor = selectedCellColor
    }

    private fun handleConflictValue(row: Int, col: Int, lastValue: Int) {
        var count = 0
        for (i in cellIfs.indices) {
            if (cellIfs[row][i].value == cellIfs[row][col].value) {
                cellIfs[row][i].valueColor = errorTextColor
                count++
            } else if (cellIfs[row][i].value == lastValue) {
                cellIfs[row][i].valueColor = textColor
            }
            if (cellIfs[i][col].value == cellIfs[row][col].value) {
                cellIfs[i][col].valueColor = errorTextColor
                count++
            } else if (cellIfs[i][col].value == lastValue) {
                cellIfs[i][col].valueColor = textColor
            }
        }
        if (count == 2) cellIfs[row][col].valueColor = textColor
    }

    fun setOnCellClickedListener(listener: OnCellClickedListener) {
        onCellClicked = listener
    }

    fun postCellIfs() {
        for (i in cellIfs.indices) {
            for (j in cellIfs[i].indices) {
                cellIfs[i][j].backgroundColor = if (cellIfs[i][j].enabled) gameBackgroundColor else backgroundDisable
            }
        }
        postInvalidate()
    }

}

data class SudokuCell(
    var backgroundColor: Int,
    var value: Int,
    var enabled: Boolean,
    var valueColor: Int
)