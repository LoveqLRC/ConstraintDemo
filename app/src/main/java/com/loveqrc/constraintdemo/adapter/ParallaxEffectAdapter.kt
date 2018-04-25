package com.loveqrc.constraintdemo.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loveqrc.constraintdemo.R
import kotlinx.android.synthetic.main.item_parallax_layout.view.*

/**
 * Created by Rc on 2018/4/25.
 * Email:664215432@qq.com
 * Description:
 */
class ParallaxEffectAdapter(context: Context, images: Array<Int>) : RecyclerView.Adapter<ParallaxEffectAdapter.ViewHolder>() {

    private var mImages: Array<Int>
    private var mContext: Context? = null

    init {
        mContext = context
        mImages = images
    }

    override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_parallax_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var image = mImages[position]
        holder.imageView.setImageDrawable(ContextCompat.getDrawable(mContext!!, image))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.iv_background_chip!!
    }


}