package com.zzzh.akhalteke_shipper.bean

import com.scwang.smartrefresh.layout.SmartRefreshLayout

class PageInfo<T>(
    var status:Int = 0,
    var pageInfo: PageDtoInfo = PageDtoInfo(),
    var list: MutableList<T> = mutableListOf()
) {

    companion object {
        fun <T> pageLoad(pInfos: PageInfo<T>, list: MutableList<T>, refreshView: SmartRefreshLayout): Int {
            if (pInfos.pageInfo.page == 0) {
                list.clear()
            }
            if (pInfos.pageInfo.last) {
                refreshView.setEnableLoadMore(false)
            } else {
                refreshView.setEnableLoadMore(true)
            }
            pInfos.list?.apply {
                for (info in this) {
                    list.add(info)
                }
            }
            return pInfos.pageInfo.page
        }
    }

}