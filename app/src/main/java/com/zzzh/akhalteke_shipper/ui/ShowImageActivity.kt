package com.zzzh.akhalteke_shipper.ui

import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import com.facebook.drawee.view.SimpleDraweeView
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.StringInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.utils.ImageLoadingUtils
import kotlinx.android.synthetic.main.activity_show_image.*
import me.relex.photodraweeview.PhotoDraweeView

/**
 * 显示图片，可点击查看大图
 */
class ShowImageActivity : BaseActivity() {

    private var imageList: ArrayList<StringInfo> = arrayListOf()
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_image)

        imageList = intent.getParcelableArrayListExtra("image_list")
        position = intent.getIntExtra("data_position", 0)

        initView()
    }

    private fun initView(){
        showimage_top.toSetTitleName("图片预览(${position+1}/${imageList.size})")


        showimage_pager.adapter = ImagePagerAdapter()
        showimage_pager.addOnPageChangeListener(object:ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                showimage_top.toSetTitleName("图片预览(${p0+1}/${imageList.size})")
            }
        })
        showimage_pager.currentItem = position
    }

    inner class ImagePagerAdapter : PagerAdapter() {

        override fun isViewFromObject(p0: View, p1: Any): Boolean {
            return p0 == p1
        }

        override fun getCount(): Int {
            return imageList.size
        }

        override fun destroyItem(container: ViewGroup, position: Int, dView: Any){
            container.removeView(dView as View)
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val photoView = PhotoDraweeView(mContext)
            photoView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            val imageUrl = imageList[position].name
            if(imageUrl.startsWith("images")){
                photoView.setPhotoUri(Uri.parse(RetrofitFactory.BASE_URL+imageUrl))
            }else{
                photoView.setPhotoUri(Uri.parse("file://$imageUrl"))
            }
            container.addView(photoView)
            return photoView
        }
    }


}
