package com.zzzh.akhalteke_shipper.realm

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.realm.Realm

/**
 * 地址三级数据操作
 */
class AddressRealm {

    /**
     * 添加
     */
    fun addAddress(addressBean: AddressBean): Completable {
        return Completable.fromAction {
            val mRealm: Realm = Realm.getDefaultInstance()
            try {
                mRealm.beginTransaction()
                val addrBean = mRealm.createObject(AddressBean::class.java, addressBean.id)
                addrBean.area_name = addressBean.area_name
                addrBean.pid = addressBean.pid
                addrBean.position = addressBean.position
                mRealm.commitTransaction()
            } catch (e: Exception) {
            } finally {
                mRealm.close()
            }
        }.subscribeOn(Schedulers.io())
    }

    /**
     * 批量添加
     */
    fun addAdrList(infoList: MutableList<AddressBean>):Single<MutableList<AddressBean>> {
        return Single.fromCallable {
            val mRealm: Realm = Realm.getDefaultInstance()
            try {
                mRealm.beginTransaction()
                for (adrBean in infoList) {
                    val addrBean = mRealm.createObject(AddressBean::class.java, adrBean.id)
                    addrBean.area_name = adrBean.area_name
                    addrBean.pid = adrBean.pid
                    addrBean.position = adrBean.position
                }
                mRealm.commitTransaction()
                return@fromCallable infoList
            } catch (e: Exception) {
            } finally {
                mRealm.close()
            }
            return@fromCallable mutableListOf<AddressBean>()
        }.subscribeOn(Schedulers.io())
    }

    fun togetAddr(id: Int): Single<AddressBean?> {
        return Single.fromCallable {
            val mRealm: Realm = Realm.getDefaultInstance()
            try {
                val addressBean = mRealm.where(AddressBean::class.java)
                        .equalTo("id", id).findFirst()
                return@fromCallable addressBean
            } catch (e: Exception) {

            } finally {
                mRealm.close()
            }
            return@fromCallable null
        }.subscribeOn(Schedulers.io())

    }

    fun selectAddrList(pid: Int): Single<MutableList<AddressBean>> {
        return Single.fromCallable {
            val mRealm: Realm = Realm.getDefaultInstance()
            try {
                val addressBean = mRealm.where(AddressBean::class.java)
                        .equalTo("pid", pid).findAll()
                return@fromCallable mRealm.copyFromRealm(addressBean)
            } catch (e: Exception) {

            } finally {
                mRealm.close()
            }
            return@fromCallable mutableListOf<AddressBean>()
        }.subscribeOn(Schedulers.io())
    }

}