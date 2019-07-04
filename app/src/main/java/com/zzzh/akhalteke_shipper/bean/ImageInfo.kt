package com.zzzh.akhalteke_shipper.bean

class ImageInfo(
    var id:String = "",
    var imagePath:String = "",
    var imageUrl:String = "",
    var imageStr:String = "",
    var dataType:Int = 0
){

    companion object {
        const val IMAGE_TYPE = 0
        const val ADD_TYPE = 1
    }

}