package com.viewpractice.myapplication

import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(),View.OnTouchListener{
    var rulerView:RulerView?=null
    var lastTouch: Float? =null
    var lastLastTouch: Float? =null
    var upTime:Long?=null
    var moveTime:Long?=null
    var animator: ObjectAnimator?=null
//    var listener: GestureDetector.OnGestureListener = object : GestureDetector.OnGestureListener {
//        override fun onDown(p0: MotionEvent?): Boolean {
//            return false
//        }
//        override fun onShowPress(p0: MotionEvent?) {
//
//        }
//        override fun onSingleTapUp(p0: MotionEvent?): Boolean {
//            return false;
//        }
//        override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
//
//            var animator: ObjectAnimator= ObjectAnimator.ofFloat(rulerView!!, "pointer", rulerView!!.pointer,rulerView!!.pointer+p2/10)
//            animator.setInterpolator (DecelerateInterpolator ())
//            animator.start()
//            return true;
//        }
//        override fun onLongPress(p0: MotionEvent?) {
//            p0.x
//        }
//        override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
//            return false;
//        }
//    }
//    var mGestureDetector:GestureDetector=GestureDetector(listener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rulerView=findViewById(R.id.rulerView)
        rulerView?.setOnTouchListener(this)
//        rulerView?.setFocusable(true);
//        rulerView?.setClickable(true);
//        rulerView?.setLongClickable(true);
    }


    override fun onTouch(v: View?, event: MotionEvent): Boolean {
//            return mGestureDetector.onTouchEvent(event);
       when (event.action) {
            MotionEvent.ACTION_DOWN -> {
//                Log.i("ACTION_DOWN",event.x.toString())
                animator?.pause()
                lastTouch = event.x
            }
            MotionEvent.ACTION_MOVE -> {
//                Log.i("ACTION_MOVE",event.x.toString())
                rulerView!!.pointer += (lastTouch!! - event.x) / 100
                lastLastTouch=lastTouch
                lastTouch = event.x
                moveTime=event.eventTime
            }
            MotionEvent.ACTION_UP -> {
                upTime=event.eventTime
                if(upTime!! - moveTime!! <200) {
                    animator =
                        ObjectAnimator.ofFloat(rulerView!!, "pointer", rulerView!!.pointer, rulerView!!.pointer - (event.x - lastLastTouch!!) / 10)
                    animator!!.setInterpolator(DecelerateInterpolator())
                    animator!!.setDuration(1000L)
                    animator!!.start()
                }
            }
            else -> {
            }
        }
        return true
    }
}
