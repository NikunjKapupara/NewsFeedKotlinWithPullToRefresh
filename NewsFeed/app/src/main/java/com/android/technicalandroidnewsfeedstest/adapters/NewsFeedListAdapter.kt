package com.android.technicalandroidnewsfeedstest.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.technicalandroidnewsfeedstest.R
import com.android.technicalandroidnewsfeedstest.ws_models.NewsFeedModel
import com.bumptech.glide.Glide
import com.echannels.moismartservices.utils.AnimUtils


/**
 * Created by N!K$ on 7/30/18.
 */
class NewsFeedListAdapter(private val cotext:Context,
                          private val newsFeedListData: List<NewsFeedModel.Row>):
        RecyclerView.Adapter<NewsFeedViewHolder>(){

    var previousPosition = 0

    override fun onBindViewHolder(holder: NewsFeedViewHolder, position: Int) {
        if (newsFeedListData[position].title != null)
            holder.newsTitleTV.text = newsFeedListData[position].title

        if (newsFeedListData[position].description != null)
            holder.newsDescTV.text = newsFeedListData[position].description

        if (newsFeedListData[position].imageHref != null) {
            Glide.with(cotext).load(newsFeedListData[position].imageHref).into(holder.newsThumbIV)
            
        }

        /**
         * List Item Smooth Animation
         */
        //// it means scrolling down
        if (position > previousPosition){
            AnimUtils.animateItemToUp(holder, true)
        }
        //// it means scrolling up side
        else{
            AnimUtils.animateItemToUp(holder, false)
        }
        previousPosition = position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedViewHolder {
        return NewsFeedViewHolder(LayoutInflater.from(cotext).inflate(R.layout.news_feeds_listitem, parent, false))
    }

    override fun getItemCount(): Int {
        return newsFeedListData.size
    }

}