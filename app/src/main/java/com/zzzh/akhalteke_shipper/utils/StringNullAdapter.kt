package com.zzzh.akhalteke_shipper.utils

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class StringNullAdapter: TypeAdapter<String>(){
    override fun write(out: JsonWriter?, value: String?) {
        if (value == null) {
            out!!.value("")
            return
        }
        out!!.value(value)
    }

    override fun read(jsonReader: JsonReader?): String {
        if(jsonReader == null){
            return ""
        }else{
            if (jsonReader.peek() == JsonToken.NULL) {//反序列化使用的是read方法
                jsonReader.nextNull()
                return ""
            }
            return jsonReader.nextString()
        }
    }
}