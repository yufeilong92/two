package com.zzzh.akhalteke_shipper.bean

class MessageEvent(
    var message:String = ""
){
    companion object {
        const val MESSAGEINFO = "test_message"
        const val DRIVER_APPOINT = "driver_appoint"
        const val GOODS_DELETE = "goods_delete"
        const val RELOAD_OFTEN = "reload_OFTEN"

        const val ADDRESS_LIST_UPDATA = "address_list_update"

        const val BANK_CARD_ADD = "bank_card_add"
        const val DAO_ADD_SUCCESS = "dao_add_success"
        const val FAMILY_CAR_UPDADE = "family_car_update"
        const val PUBLISH_SUCCESS = "publish_success"
        const val ORDER_CONFIRM = "order_confirm"
        const val ORDER_PAY = "order_pay"
        const val ORDER_CANCEL = "order_cancel"

        const val INVOICE_ADD = "invoice_add"
        const val WITHDRAW_SUCCESS = "withdraw_success"
        const val PROTOCOLTO_SUCCESS = "protocolto_success"
        const val AUTHEN_SUCCESS = "authen_success"
        const val MINE_HEADER_SUCCESS = "mine_header_success"
    }
}