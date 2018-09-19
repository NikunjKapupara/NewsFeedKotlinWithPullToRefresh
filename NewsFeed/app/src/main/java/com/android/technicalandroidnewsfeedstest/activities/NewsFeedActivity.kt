package com.android.technicalandroidnewsfeedstest.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.android.mytaxi.app_utility.dialog_utils.LoaderDialog
import com.android.technicalandroidnewsfeedstest.R
import com.android.technicalandroidnewsfeedstest.adapters.NewsFeedListAdapter
import com.android.technicalandroidnewsfeedstest.retrofit.RetrofitApiService
import com.android.technicalandroidnewsfeedstest.utils.LogUtils
import com.android.technicalandroidnewsfeedstest.utils.NetworkUtils
import com.android.technicalandroidnewsfeedstest.ws_models.NewsFeedModel
import com.echannels.moismartservices.utils.DialogUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_news_feed.*


class NewsFeedActivity : AppCompatActivity() {

    private var disposable: Disposable? = null
    private var newsFeedList: MutableList<NewsFeedModel.Row> = mutableListOf()

    /**
     * Create Instance of the Retrofit API
     */
    private val RetrofitAPI by lazy {
        RetrofitApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_feed)

        initPullToRefresh()
        getAllNewsFeed()
    }


    /**
     * Init Pull to Refresh
     */
    private fun initPullToRefresh() {

        /// Handle on Swipe Down / PULL To REFRESH
        swipe_refresh_layout.setOnRefreshListener {
            newsFeedList.clear()
            newsFeedRV.adapter.notifyDataSetChanged()
            getAllNewsFeed()
        }

    }


    /**
     * make API call to get News Feed Data
     */
    private fun getAllNewsFeed() {

        //Checking internet connection is available in device or not
        if(!NetworkUtils.isInternetAvailable(this)){
            DialogUtils.showAlertDialogOnly(this, getString(R.string.error_no_internet))
            return
        }

        ////showing Activity Loader while getting News data.
        LoaderDialog.getInstance().showLoader(this)


        ///// call service API to get the News data
        disposable = RetrofitAPI.getNewsFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            LoaderDialog.getInstance().hideLoader()
                            LogUtils.getInstance().logError("SUCCESS: ", "result found")
                            if (result!= null && result.rows.isNotEmpty()) {
                                updateTitleBar(result)
                                newsFeedList = result.rows as MutableList<NewsFeedModel.Row>
                                bindNewsData()
                            }
                            else
                                DialogUtils.showAlertDialogOnly(this, getString(R.string.no_news_data))
                        },
                        { error ->
                            LoaderDialog.getInstance().hideLoader()
                            DialogUtils.showAlertDialogOnly(this, getString(R.string.error_getting_data))
                        })

    }

    /**
     * UPDATE THE ACTIVITY TITLE BAR FROM NEWS FEED data
     */
    private fun updateTitleBar(newsData:NewsFeedModel) {
        val actionBar = supportActionBar
        actionBar!!.title = newsData.title
    }


    /**
     * Bind News Feed data in Recyclerview
     */
    private fun bindNewsData() {
        try {
            LogUtils.getInstance().logError("News data Length: ", newsFeedList!!.size.toString())
            val linearLayoutManager = LinearLayoutManager(this)
            newsFeedRV.layoutManager = linearLayoutManager
            newsFeedRV!!.adapter = NewsFeedListAdapter(this, newsFeedList!!)
            swipe_refresh_layout.isRefreshing = false
        }
        catch (e:Exception){
            LogUtils.getInstance().logError("ERROR: ", "Error occured while binding data..")
        }
    }

    /**
     * Ask user to EXIT from app on Back Button Pressed
     */
    override fun onBackPressed() {
        DialogUtils.showConfirmationDialog(this, getString(R.string.exit_confirmation_msg),
                View.OnClickListener { p0 ->
                    if (p0!!.id == R.id.btnOK){
                        finish()
                    }
                })
    }


}
