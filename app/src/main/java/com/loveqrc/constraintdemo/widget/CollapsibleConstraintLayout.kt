package com.loveqrc.constraintdemo.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.design.widget.AppBarLayout
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.loveqrc.constraintdemo.R
import kotlinx.android.synthetic.main.toolbar_layout.view.*
import java.util.jar.Attributes

/**
 * Created by Rc on 2018/4/24.
 * Email:664215432@qq.com
 * Description:
 */
class CollapsibleConstraintLayout : ConstraintLayout, AppBarLayout.OnOffsetChangedListener {


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    private val mOpenToolBarSet: ConstraintSet = ConstraintSet()
    private val mCloseToolBarSet: ConstraintSet = ConstraintSet()
    private var mBackground: ImageView? = null
    private var mTvTitle: TextView? = null
    private var mIvIcon: ImageView? = null
    private var mLastPosition: Int = 0
    private var mToolBarOpen = true
    private var mTransitionThreshold = 0.35f

    private var showImageAnimator: Animator? = null
    private var hideImageAnimator: Animator? = null

    private var mTranslationTitle: AnimationHelper? = null
    private var mTranslationIcon: AnimationHelper? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (parent is AppBarLayout) {
            val appBarLayout = parent as AppBarLayout
            appBarLayout.addOnOffsetChangedListener(this)
            mOpenToolBarSet.clone(context, R.layout.toolbar_layout)
            mCloseToolBarSet.clone(context, R.layout.layout_close_toolbar)

            mBackground = findViewById(R.id.iv_product_image)
            mTvTitle = findViewById(R.id.tv_title)
            mIvIcon = findViewById(R.id.iv_icon)

            showImageAnimator = ObjectAnimator.ofFloat(mBackground, "alpha", 0f, 1f)
            showImageAnimator?.duration = 600
            hideImageAnimator = ObjectAnimator.ofFloat(mBackground, "alpha", 1f, 0f)
            hideImageAnimator?.duration = 600

        }
    }


    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        Log.d("tag", "verticalOffset:$verticalOffset")

        if (mLastPosition == verticalOffset) {
            return
        }

        mLastPosition = verticalOffset
        val progress = Math.abs(verticalOffset / appBarLayout?.height?.toFloat()!!)

        val params = layoutParams as AppBarLayout.LayoutParams

        params.topMargin = -verticalOffset
        layoutParams = params

        if (mToolBarOpen && progress > mTransitionThreshold) {
            mCloseToolBarSet.applyTo(this)
            hideImageAnimator?.start()
            mToolBarOpen = false
        } else if (!mToolBarOpen && progress < mTransitionThreshold) {
            mOpenToolBarSet.applyTo(this)
            showImageAnimator?.start()
            mToolBarOpen = true
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.e("tag", "onLayout")
        if (mTvTitle != null && mTranslationTitle == null) {
            mTranslationTitle = AnimationHelper(mTvTitle!!)
        }
        if (mIvIcon != null && mTranslationIcon == null) {
            mTranslationIcon = AnimationHelper(mIvIcon!!)
        }
        mTranslationTitle?.evaluate()
        mTranslationIcon?.evaluate()
    }

    class AnimationHelper(view: View) {
        private var initialValue = 0
        private var traget = view

        init {
            initialValue = traget.left
        }

        fun evaluate() {
            if (initialValue != traget.left) {
                val delta = (initialValue - traget.left).toFloat()
                val anim = ObjectAnimator.ofFloat(traget, "translationX", delta, 0f)
                anim.duration = 400
                anim.start()
                initialValue = traget.left
            }
        }

    }
}