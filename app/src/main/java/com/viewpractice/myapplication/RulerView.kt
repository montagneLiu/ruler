package com.viewpractice.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.lang.String.format

class RulerView: View {
    var paint= Paint(Paint.ANTI_ALIAS_FLAG)
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    public var pointer:Float= 0F
        set(value){
            if(value<0){
                field=0F
                invalidate()
                return
            }
            field=value
            invalidate()
        }
    var border:Int=50;
    var gap:Float=10F

    init{

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        border= (width/gap).toInt()
        var closest=Math.ceil(pointer.toDouble())-pointer
        //绘制短线
        paint.setColor(Color.BLACK)
        paint.strokeWidth=1F
        paint.textAlign=Paint.Align.CENTER
        paint.strokeCap= Paint.Cap.ROUND
        paint.textSize=20F
        for(index in 0..border){
            canvas.drawLine(width/2+(closest.toFloat()*10+index)*gap,height/2.toFloat(),width/2+(closest.toFloat()*10+index)*gap,height/2+20F,paint)
            if (pointer - index/10 > 0) {
                canvas.drawLine(
                    width / 2 + (closest.toFloat() * 10 - index) * gap,
                    height / 2.toFloat(),
                    width / 2 + (closest.toFloat() * 10 - index) * gap,
                    height / 2 + 20F,
                    paint
                )
            }
        }
        //绘制长线
        paint.strokeWidth=2F
        for(index in 0..border){
            if(index%10==0) {
                canvas.drawLine(
                    width / 2 + (closest.toFloat() * 10 + index) * gap,
                    height / 2.toFloat(),
                    width / 2 + (closest.toFloat() * 10 + index) * gap,
                    height / 2 + 40F,
                    paint
                )
                if (pointer - index / 10 > -1) {
                    canvas.drawLine(
                        width / 2 + (closest.toFloat() * 10 - index) * gap,
                        height / 2.toFloat(),
                        width / 2 + (closest.toFloat() * 10 - index) * gap,
                        height / 2 + 40F,
                        paint
                    )
                }
                //绘制数字
                canvas.drawText(
                    (pointer + closest + index / 10).toInt().toString(),
                    width / 2 + (closest.toFloat() * 10 + index) * gap,
                    height / 2 + 40F + paint.textSize * 3 / 2,
                    paint
                )
                if (pointer - index / 10 > -1) {
                    canvas.drawText(
                        (pointer + closest - index / 10).toInt().toString(),
                        width / 2 + (closest.toFloat() * 10 - index) * gap,
                        height / 2 + 40F + paint.textSize * 3 / 2,
                        paint
                    )
                }
            }
        }
        //绘制指针
        paint.setColor(Color.GREEN)
        paint.strokeWidth=3F
        paint.textSize=40F
        var textHeight:Float=paint.textSize
        canvas.drawLine((width/2).toFloat(), (height/2).toFloat(), (width/2).toFloat(),height/2+44F,paint)
        canvas.drawText(format("%.1f", pointer),width/2.toFloat(),height/2-textHeight*3/2,paint)
        var length:Float=paint.measureText(format("%.1f", pointer))
        paint.textSize=15F
        canvas.drawText("kg",width/2.toFloat()+length*2/3,height/2-textHeight*2,paint)
    }
}