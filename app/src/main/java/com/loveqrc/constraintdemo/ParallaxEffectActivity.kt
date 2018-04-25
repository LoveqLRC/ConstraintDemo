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
        val images = arrayOf(R.drawable.one_bg, R.drawable.two_bg, R.drawable.three_bg, R.drawable.fourth_bg, R.drawable.fifth_bg)
        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recycler_view)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = ParallaxEffectAdapter(this, images)
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var manager = recyclerView!!.layoutManager as LinearLayoutManager
                var position = manager.findFirstVisibleItemPosition()
                Log.d("loveqrc", "Position:$position")
                var lastPosition = manager.findLastVisibleItemPosition()
                Log.d("loveqrc", "lastPosition:$lastPosition")
                var offset = recyclerView.computeHorizontalScrollOffset()
                Log.d("loveqrc", "offset:$offset")

                for (i in 0..lastPosition - position) {
                    val layout = manager.findViewByPosition(position + i) as ConstraintLayout
                    val guideline = layout.findViewById<Guideline>(R.id.guideline)
                    val params = guideline.layoutParams as ConstraintLayout.LayoutParams
                    val width = recyclerView.width
                    val deltaPos = offset - (position + i) * width
                    val percent = deltaPos / width.toFloat()
                    params.guidePercent=Math.max(0.3f, Math.min(0.7f, 0.5f - percent))
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
