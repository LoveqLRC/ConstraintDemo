package com.loveqrc.constraintdemo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.animation.OvershootInterpolator
import kotlinx.android.synthetic.main.layout_shopping_activity_invisble_size.*

/**
 * Created by Rc on 2018/4/26.
 * Email:664215432@qq.com
 * Description:
 */
class ShoppingKeyFrameActivity : AppCompatActivity() {
    companion object {
        fun launchActivity(context: Context) {
            val intent = Intent(context, ShoppingKeyFrameActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_key_frame)
        setupAnimations()
    }

    @SuppressLint("SetTextI18n")
    private fun setupAnimations() {
        var show = false
        btn_add_to_bag.setOnClickListener {
            show = if (show) {
                updateConstraints(R.layout.layout_shopping_activity_invisble_size)
                btn_add_to_bag.text = resources.getString(R.string.add_to_bag) + " $125"
                false
            } else {
                updateConstraints(R.layout.layout_shopping_activity_select_size)
                btn_add_to_bag.text = "SELECT SIZE"
                true
            }
        }

    }

    private fun updateConstraints(layoutId: Int) {
        var constraintSet = ConstraintSet()
        constraintSet.clone(this, layoutId)
        constraintSet.applyTo(shopping_cc)
        var changeBounds = ChangeBounds()
        changeBounds.interpolator = OvershootInterpolator()
        changeBounds.duration = 1200
        TransitionManager.beginDelayedTransition(shopping_cc, changeBounds)

    }

}