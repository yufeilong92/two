package com.zzzh.akhalteke_shipper.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.zzzh.akhalteke_shipper.bean.FragmentInfo


class PagerFragmentAdapter(var fm: FragmentManager, var mFragments:MutableList<FragmentInfo>): FragmentStatePagerAdapter(fm){


    override fun getItem(position: Int): Fragment {
        return mFragments[position].mFragment
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragments[position].title
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }



    fun setDate(mFragments:MutableList<FragmentInfo>){
        PagerFragmentAdapter@this.mFragments = mFragments
        notifyDataSetChanged()
    }



}