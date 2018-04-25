package com.loveqrc.constraintdemo

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.Guideline
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.WindowManager
import com.loveqrc.constraintdemo.R.id.recycler_view
import com.loveqrc.constraintdemo.adapter.ParallaxEffectAdapter
import kotlinx.android.synthetic.main.activity_parallax_effect.*
import kotlinx.android.synthetic.main.item_parallax_layout.*

class ParallaxEffectActivity : AppCompatActivity() {

    companion object {
        fun launchActivity(context: Context) {
            val intent = Intent(context, ParallaxEffectActivity::class.java)
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parallax_effect)
        setFullScreen()
        val images = arrayOf(R.drawable.one_bg, R.drawable.two_bg, R.drawable.three_bg,
                R.drawable.fourth_bg, R.drawable.fifth_bg)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recycler_view)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = ParallaxEffectAdapter(this, images)
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val manager = recyclerView!!.layoutManager as LinearLayoutManager
                //第一个可见的item位置
                val position = manager.findFirstVisibleItemPosition()
                Log.d("loveqrc", "firstPosition:$position")
                //最后一个可见的item位置
                val lastPosition = manager.findLastVisibleItemPosition()
                Log.d("loveqrc", "lastPosition:$lastPosition")
                //水平偏移量
                val offset = recyclerView.computeHorizontalScrollOffset()
                Log.d("loveqrc", "offset:$offset")

                for (i in 0..lastPosition - position) {
                    val layout = manager.findViewByPosition(position + i) as ConstraintLayout
                    val guideline = layout.findViewById<Guideline>(R.id.guideline)
                    val params = guideline.layoutParams as ConstraintLayout.LayoutParams
                    val width = recyclerView.width
                    val deltaPos = offset - (position + i) * width
                    val percent = deltaPos / width.toFloat()
                    params.guidePercent = Math.max(0.3f, Math.min(0.7f, 0.5f - percent))
                    guideline.layoutParams = params
                }
            }
        })
    }

    private fun setFullScreen() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}
