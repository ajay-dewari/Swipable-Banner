package me.ajay.swipable_banner

import java.util.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.widget.LinearLayout

import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import me.ajay.swipable_banner.databinding.ActivityMainBinding
import android.os.Handler
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*===================Initializing Banner Variables===============*/
        val mBannerArray = resources.obtainTypedArray(R.array.banner_img_array)
        val numberOfBannerImage = mBannerArray.length()
        val mBannerDotViews = Array(numberOfBannerImage) { View(this) }
        val mBannerViewPager = binding.bannerViewPager
        mBannerViewPager.adapter = BannerPagerAdapter(this@MainActivity, mBannerArray)

        for (i in 0 until numberOfBannerImage) {
            // create a new textview
            val bannerDotView = View(this)
            /*Creating the dynamic dots for banner*/
            val dotLayoutParam = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            dotLayoutParam.height = resources.getDimensionPixelSize(R.dimen.standard_10)
            dotLayoutParam.width = resources.getDimensionPixelSize(R.dimen.standard_10)
            dotLayoutParam.setMargins(resources.getDimensionPixelSize(R.dimen.standard_8), 0, 0, 0)
            bannerDotView.layoutParams = dotLayoutParam
            bannerDotView.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.shape_deselected_dot)

            // add the textview to the linearlayout
            binding.bannerDotsLayout.addView(bannerDotView)

            // save a reference to the textview for later
            mBannerDotViews[i] = bannerDotView
        }

        autoSwipeBanner(mBannerViewPager, numberOfBannerImage)
        mBannerViewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                changeDotBG(position, numberOfBannerImage, mBannerDotViews)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

    }

    private fun autoSwipeBanner(bannerViewPager: ViewPager, numberOfBannerImage :Int) {
        val handler = Handler()
        val update = Runnable {
            var currentPage = bannerViewPager.currentItem
            if (currentPage == numberOfBannerImage - 1) {
                currentPage = -1
            }
            bannerViewPager.setCurrentItem(currentPage + 1, true)
        }
        val swipeTimer = Timer()

        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 500, 3000)
    }

    private fun changeDotBG(position: Int, numberOfBannerImage : Int, mBannerDotViews: Array<View>) {
        for (i in 0 until numberOfBannerImage) {
            if (position == i) {
                mBannerDotViews[i].background =
                    ContextCompat.getDrawable(this@MainActivity, R.drawable.shape_selected_dot)
            } else {
                mBannerDotViews[i].background =
                    ContextCompat.getDrawable(this@MainActivity, R.drawable.shape_deselected_dot)
            }
        }
    }
}