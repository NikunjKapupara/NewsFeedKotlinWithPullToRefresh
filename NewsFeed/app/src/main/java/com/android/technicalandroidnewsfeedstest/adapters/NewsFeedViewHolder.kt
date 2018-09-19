package com.android.technicalandroidnewsfeedstest.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.news_feeds_listitem.view.*


/**
 * Created by Nikunj on 7/30/18.
 */
class NewsFeedViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val newsTitleTV = view.newsTitleTV
    val newsDescTV = view.newsDescTV
    val newsThumbIV = view.newsThumbIV

}