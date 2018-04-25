package com.loveqrc.constraintdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_one -> {
                KeyFrameAnimationActivity.launchActivity(this)
            }
            R.id.tv_two -> {

            }
            R.id.tv_collapsing_toolbar -> {
                CollapsingToolbarActivity.launchActivity(this)
            }

            R.id.tv_parallax_effect -> {
                ParallaxEffectActivity.launchActivity(this)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_one.setOnClickListener(this)
        tv_two.setOnClickListener(this)
        tv_collapsing_toolbar.setOnClickListener(this)
        tv_parallax_effect.setOnClickListener(this)
    }
}
