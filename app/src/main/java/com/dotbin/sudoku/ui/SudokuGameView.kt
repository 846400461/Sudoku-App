package com.dotbin.sudoku.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.dotbin.sudoku.R

typealias OnCellClickedListener = (() -> Int)
typealias OnSudokuSuccess = (() -> Unit)

class SudokuGameView : View {
    companion object {
        const val TAG = "SudokuGameView"
        const val DEFAULT_GAME_SIZE = 100
    }

    private var onCellClicked: OnCellClickedListener? = null
    private var onSudokuSuccess: OnSudokuSuccess? = null
    private val sectionLine = Paint()
    private val cellLine = Paint()
    private val cellPaint = Paint()
    private val cellTextPaint = Paint()
    private var cellHeight = 0F
    private var cellWidth = 0F
    var cellIfs = Array(9) {
        Array(9) { SudokuCell(Color.BLACK, 0, true, Color.WHITE) }
    }
    private var textColor = 0
    private var errorTextColor = 0
    private var gameBackgroundColor = 0
    private var cellSameTextColor = 0
    private var cellSameXYColor = 0
    private var selectedCellColor = 0
    private var backgroundDisable = 0
    var touchEnale = true
    private val selectCell = IntArray(2) { -1 }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        Log.i(TAG, "third constructor")
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.SudokuGameView
        )
        sectionLine.color = a.getColor(R.styleable.SudokuGameView_sectionLineColor, Color.GRAY)
        cellLine.color = a.getColor(R.styleable.SudokuGameView_cellLineColor, Color.DKGRAY)
        gameBackgroundColor =
            a.getColor(R.styleable.SudokuGameView_backgroundColor, Color.rgb(54, 54, 54))
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
        var erroCount = 0
        var numCount = 0
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
                if (cellPaint.color == selectedCellColor) {
                    selectCell[0] = x
                    selectCell[1] = y
                }
                //draw text
                val centerX = (2 * x + 1) * cellWidth / 2f
                val centerY = (2 * y + 1) * cellHeight / 2f
                cellTextPaint.color = cellIfs[x][y].valueColor
                if (cellIfs[x][y].value != 0) numCount++
                if (cellIfs[x][y].valueColor == errorTextColor) erroCount++
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

        if(numCount==81&&erroCount==0){
            onSudokuSuccess?.invoke()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!touchEnale) return false
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
                if (cellIfs[row][col].enabled)
                    onCellClicked?.let {
                        val cellValue = it()
                        if (cellValue in 0..9)
                            cellIfs[row][col].value = cellValue
                        1
                    }
                handleSameValue(row, col)
                handleConflictValue(row, col)
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
//        for (dx in cellIfs.indices) {
//            for (dy in cellIfs[dx].indices) {
//                if (cellIfs[dx][dy].value == cellIfs[row][col].value && cellIfs[dx][dy].value != 0)
//                    cellIfs[dx][dy].backgroundColor = cellSameTextColor
//            }
//        }
//        cellIfs[row][col].backgroundColor = selectedCellColor
    }

    private fun handleConflictValue(lx: Int, ly: Int) {
        for (i in cellIfs.indices) {
            cellIfs[lx][i].valueColor = if (isErrorValue(lx, i)) errorTextColor else textColor
            cellIfs[i][ly].valueColor = if (isErrorValue(i, ly)) errorTextColor else textColor
        }
        val baseLx = lx - lx % 3
        val baseLy = ly - ly % 3
        for (i in 0..2)
            for (j in 0..2) {
                cellIfs[baseLx + i][baseLy + j].valueColor =
                    if (isErrorValue(baseLx + i, baseLy + j)) errorTextColor else textColor
            }
    }

    private fun isErrorValue(lx: Int, ly: Int): Boolean {
        for (i in cellIfs.indices) {
            if ((cellIfs[lx][ly].value == cellIfs[lx][i].value && ly != i) || (cellIfs[lx][ly].value == cellIfs[i][ly].value && lx != i))
                return true
        }
        val baseLx = lx - lx % 3
        val baseLy = ly - ly % 3
        for (i in 0..2)
            for (j in 0..2) {
                if (baseLx + i == lx && baseLy + j == ly) continue
                if (cellIfs[baseLx + i][baseLy + j].value == cellIfs[lx][ly].value)
                    return true
            }
        return false
    }

    fun setOnClickListener(listener: OnCellClickedListener) {
        onCellClicked = listener
    }

    fun setOnSuccessListener(listener: OnSudokuSuccess){
        onSudokuSuccess=listener
    }

    fun setCurrentCellValue(value: Int) {
        if (value !in 0..9 || selectCell[0] == -1 || selectCell[1] == -1 || !cellIfs[selectCell[0]][selectCell[1]].enabled) return
        cellIfs[selectCell[0]][selectCell[1]].value = value
        handleActionUp(selectCell[0], selectCell[1])
        handleSameValue(selectCell[0], selectCell[1])
        handleConflictValue(selectCell[0], selectCell[1])
        postInvalidate()
    }
}

data class SudokuCell(
    var backgroundColor: Int,
    var value: Int,
    var enabled: Boolean,
    var valueColor: Int
)