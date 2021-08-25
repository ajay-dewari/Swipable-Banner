package me.ajay.swipable_banner

import android.content.Context
import android.content.res.TypedArray
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import android.widget.Toast
import android.view.LayoutInflater
import android.widget.ImageView


class BannerPagerAdapter(private val context: Context, private val bannerArray: TypedArray) :
    PagerAdapter() {

    override fun getCount(): Int {
        return bannerArray.length()
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val bannerLayout: ImageView = LayoutInflater.from(context)
            .inflate(R.layout.banner_fragment_layout, container, false) as ImageView
        bannerLayout.setImageResource(bannerArray.getResourceId(position, 0))
        bannerLayout.setOnClickListener {
            Toast.makeText(
                context,
                "swipe clicked$position",
                Toast.LENGTH_LONG
            ).show()
        }
        container.addView(bannerLayout)
        return bannerLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View?)
    }

}