package com.zzzh.akhalteke_shipper.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;


import java.util.List;

public class WeatherVo implements Parcelable {

    /**
     * cityInfo : {"c1":"101180101","c10":"1","c11":"0371","c12":"450000","c15":"111","c16":"AZ9371","c17":"+8","c2":"zhengzhou","c3":"郑州","c4":"zhengzhou","c5":"郑州","c6":"henan","c7":"河南","c8":"china","c9":"中国","latitude":34.758,"longitude":113.641}
     * f1 : {"3hourForcast":[{"hour":"8时-11时","temperature":"20","temperature_max":"25","temperature_min":"20","weather":"阴","weather_pic":"http://app1.showapi.com/weather/icon/day/02.png","wind_direction":"南风","wind_power":"<3级,3"},{"hour":"11时-14时","temperature":"25","temperature_max":"26","temperature_min":"20","weather":"小雨","weather_pic":"http://app1.showapi.com/weather/icon/day/07.png","wind_direction":"南风","wind_power":"<3级,3"},{"hour":"14时-17时","temperature":"26","temperature_max":"26","temperature_min":"24","weather":"小雨","weather_pic":"http://app1.showapi.com/weather/icon/day/07.png","wind_direction":"南风","wind_power":"3-4级,3"},{"hour":"17时-20时","temperature":"24","temperature_max":"26","temperature_min":"21","weather":"小雨","weather_pic":"http://app1.showapi.com/weather/icon/day/07.png","wind_direction":"南风","wind_power":"3-4级,3"},{"hour":"20时-23时","temperature":"21","temperature_max":"24","temperature_min":"21","weather":"小雨","weather_pic":"http://app1.showapi.com/weather/icon/night/07.png","wind_direction":"南风","wind_power":"<3级,0"},{"hour":"23时-2时","temperature":"21","temperature_max":"21","temperature_min":"18","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"南风","wind_power":"<3级,0"},{"hour":"2时-5时","temperature":"18","temperature_max":"21","temperature_min":"18","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"南风","wind_power":"<3级,0"},{"hour":"5时-8时","temperature":"18","temperature_max":"21","temperature_min":"18","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"南风","wind_power":"<3级,0"}],"air_press":"993 hPa","day":"20190514","day_air_temperature":"27","day_weather":"小雨","day_weather_code":"07","day_weather_pic":"http://app1.showapi.com/weather/icon/day/07.png","day_wind_direction":"南风","day_wind_power":"3-4级 5.5~7.9m/s","index":{"ac":{"desc":"您将感到很舒适，一般不需要开启空调。","title":"较少开启"},"ag":{"desc":"天气条件不易诱发过敏，可放心外出，除特殊体质外，无需担心过敏问题。","title":"不易发"},"aqi":{"desc":"敏感人群症状易加剧，应避免高强度户外锻炼","title":"轻度污染"},"beauty":{"desc":"请选用保湿型霜类化妆品。","title":"保湿"},"cl":{"desc":"有降水,推荐您在室内进行休闲运动。","title":"较不宜"},"clothes":{"desc":"建议穿长袖衬衫单裤等服装","title":"舒适"},"cold":{"desc":"感冒机率较低，避免长期处于空调屋中。","title":"少发"},"comfort":{"desc":"热，感觉不舒适","title":"较差"},"dy":{"desc":"天气不好，不适合垂钓","title":"不适宜钓鱼"},"gj":{"desc":"有降水，出门带雨具并注意防雷。","title":"不适宜"},"glass":{"desc":"不需要佩戴","title":"不需要"},"hc":{"desc":"天气不好，建议选择别的娱乐方式。","title":"不适宜"},"ls":{"desc":"有降水会淋湿衣物，不适宜晾晒。","title":"不适宜"},"mf":{"desc":"空气干燥，且风力较大，这种天气往往将头发吹得杂乱无形，并且头发的水份和油份丢失也加快，请保持头发的清洁，并选用滋润型洗发露和护发素。　","title":"适宜"},"nl":{"desc":"有风，且有降水，会给您的出行带来很大的不便，建议就近或最好在室内进行夜生活。","title":"较不适宜"},"pj":{"desc":"天气炎热，可适量饮用啤酒，不要过量。","title":"适宜"},"pk":{"desc":"有雨，天气不好，不适宜放风筝。","title":"不适宜"},"sports":{"desc":"有降水,推荐您在室内进行休闲运动。","title":"较不宜"},"travel":{"desc":"有降水气温高，注意防暑降温携带雨具。","title":"较不宜"},"uv":{"desc":"辐射较弱，涂擦SPF12-15、PA+护肤品","title":"弱"},"wash_car":{"desc":"有雨，雨水和泥水会弄脏爱车","title":"不宜"},"xq":{"desc":"雨水可能会使心绪无端地挂上轻愁。","title":"较差"},"yh":{"desc":"建议尽量不要去室外约会。","title":"较不适宜"},"zs":{"desc":"气温不高，中暑几率极低。","title":"不容易中暑"}},"jiangshui":"71%","night_air_temperature":"18","night_weather":"多云","night_weather_code":"01","night_weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","night_wind_direction":"南风","night_wind_power":"0-3级 <5.4m/s","sun_begin_end":"05:24|19:20","weekday":2,"ziwaixian":"弱"}
     * f2 : {"3hourForcast":[{"hour":"8时-11时","temperature":"21","temperature_max":"24","temperature_min":"18","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","wind_direction":"南风","wind_power":"<3级,2"},{"hour":"11时-14时","temperature":"24","temperature_max":"30","temperature_min":"21","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","wind_direction":"南风","wind_power":"<3级,2"},{"hour":"14时-17时","temperature":"30","temperature_max":"30","temperature_min":"24","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","wind_direction":"南风","wind_power":"<3级,2"},{"hour":"17时-20时","temperature":"30","temperature_max":"30","temperature_min":"26","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","wind_direction":"南风","wind_power":"<3级,2"},{"hour":"20时-23时","temperature":"26","temperature_max":"30","temperature_min":"23","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"南风","wind_power":"<3级,0"},{"hour":"23时-2时","temperature":"23","temperature_max":"26","temperature_min":"19","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"南风","wind_power":"3-4级,0"},{"hour":"2时-5时","temperature":"19","temperature_max":"23","temperature_min":"19","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"南风","wind_power":"3-4级,0"},{"hour":"5时-8时","temperature":"20","temperature_max":"23","temperature_min":"19","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"南风","wind_power":"<3级,0"}],"air_press":"993 hPa","day":"20190515","day_air_temperature":"31","day_weather":"多云","day_weather_code":"01","day_weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","day_wind_direction":"南风","day_wind_power":"0-3级 <5.4m/s","index":{"ac":{"desc":"天气热，到中午的时候您将会感到有点热，因此建议在午后较热时开启制冷空调。","title":"部分时间开启"},"ag":{"desc":"天气条件不易诱发过敏，有降水，特殊体质人群应预防感冒可能引发的过敏。","title":"不易发"},"aqi":{"desc":"敏感人群症状易加剧，应避免高强度户外锻炼","title":"轻度污染"},"beauty":{"desc":"请选用露质面霜打底，水质无油粉底霜。","title":"去油"},"cl":{"desc":"运动请注意防晒。","title":"较适宜"},"clothes":{"desc":"适合穿T恤、短薄外套等夏季服装","title":"热"},"cold":{"desc":"感冒机率较低，避免长期处于空调屋中。","title":"少发"},"comfort":{"desc":"热，感觉不舒适","title":"较差"},"dy":{"desc":"天气稍热会对垂钓产生一定影响。","title":"较适宜"},"gj":{"desc":"这种好天气去逛街可使身心畅快放松。","title":"适宜"},"glass":{"desc":"不需要佩戴","title":"不需要"},"hc":{"desc":"天气较好，适宜划船及嬉玩水上运动。","title":"适宜"},"ls":{"desc":"天气阴沉，不太适宜晾晒。","title":"不适宜"},"mf":{"desc":"天热，头皮皮脂分泌会增多，这既会助长发丝间细菌滋生，也会使空气中悬浮物更易黏附在头发上，极易弄脏头发，注意保持头发的清洁。","title":"一般"},"nl":{"desc":"天气较好，虽然有点风，但仍比较适宜夜生活，您可以放心外出。","title":"较适宜"},"pj":{"desc":"炎热干燥，适宜饮用冰镇啤酒，不要贪杯。","title":"适宜"},"pk":{"desc":"天气酷热，不适宜放风筝。","title":"不宜"},"sports":{"desc":"运动请注意防晒。","title":"较适宜"},"travel":{"desc":"天热注意防晒，可选择水上娱乐项目。","title":"较适宜"},"uv":{"desc":"辐射较弱，涂擦SPF12-15、PA+护肤品","title":"弱"},"wash_car":{"desc":"有雨，雨水和泥水会弄脏爱车","title":"不宜"},"xq":{"desc":"天气热，容易烦躁","title":"较差"},"yh":{"desc":"不用担心天气来调皮捣乱而影响了兴致。 ","title":"较适宜"},"zs":{"desc":"气温较高，易中暑人群注意阴凉休息。","title":"可能中暑"}},"jiangshui":"8%","night_air_temperature":"19","night_weather":"多云","night_weather_code":"01","night_weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","night_wind_direction":"南风","night_wind_power":"3-4级 5.5~7.9m/s","sun_begin_end":"05:23|19:21","weekday":3,"ziwaixian":"弱"}
     * f3 : {"3hourForcast":[{"hour":"8时-11时","temperature":"23","temperature_max":"27","temperature_min":"20","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","wind_direction":"南风","wind_power":"<3级,1"},{"hour":"11时-14时","temperature":"27","temperature_max":"31","temperature_min":"23","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","wind_direction":"东南风","wind_power":"<3级,1"},{"hour":"14时-17时","temperature":"31","temperature_max":"31","temperature_min":"27","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","wind_direction":"东南风","wind_power":"3-4级,3"},{"hour":"17时-20时","temperature":"31","temperature_max":"31","temperature_min":"28","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","wind_direction":"东南风","wind_power":"3-4级,3"},{"hour":"20时-23时","temperature":"28","temperature_max":"31","temperature_min":"24","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"东南风","wind_power":"<3级,0"},{"hour":"23时-2时","temperature":"24","temperature_max":"28","temperature_min":"22","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"东南风","wind_power":"<3级,0"},{"hour":"2时-5时","temperature":"22","temperature_max":"24","temperature_min":"20","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"东南风","wind_power":"<3级,0"},{"hour":"5时-8时","temperature":"20","temperature_max":"22","temperature_min":"20","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"东南风","wind_power":"<3级,0"}],"air_press":"993 hPa","day":"20190516","day_air_temperature":"32","day_weather":"多云","day_weather_code":"01","day_weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","day_wind_direction":"东南风","day_wind_power":"3-4级 5.5~7.9m/s","index":{"ac":{"desc":"天气热，到中午的时候您将会感到有点热，因此建议在午后较热时开启制冷空调。","title":"部分时间开启"},"ag":{"desc":"天气条件不易诱发过敏，可放心外出，除特殊体质外，无需担心过敏问题。","title":"不易发"},"aqi":{"desc":"敏感人群症状易加剧，应避免高强度户外锻炼","title":"轻度污染"},"beauty":{"desc":"请选用露质面霜打底，水质无油粉底霜。","title":"去油"},"cl":{"desc":"适当减少运动时间并降低运动强度。","title":"适宜"},"clothes":{"desc":"适合穿T恤、短薄外套等夏季服装","title":"热"},"cold":{"desc":"感冒机率较低，避免长期处于空调屋中。","title":"少发"},"comfort":{"desc":"热，感觉不舒适","title":"较差"},"dy":{"desc":"天气太热,不适宜垂钓。","title":"不适宜钓鱼"},"gj":{"desc":"风稍大，出门逛街前记得给秀发定型。","title":"较适宜"},"glass":{"desc":"需要佩戴","title":"需要"},"hc":{"desc":"风大，需注意及时添衣。","title":"较适宜"},"ls":{"desc":"天气阴沉，不太适宜晾晒。","title":"不适宜"},"mf":{"desc":"头发需要保持清洁，同时要注意防晒，含防晒成分洗发护发品是很好的选择。","title":"一般"},"nl":{"desc":"天气较好，虽然有点风，但仍比较适宜夜生活，您可以放心外出。","title":"较适宜"},"pj":{"desc":"炎热干燥，适宜饮用冰镇啤酒，不要贪杯。","title":"适宜"},"pk":{"desc":"天气酷热，不适宜放风筝。","title":"不宜"},"sports":{"desc":"适当减少运动时间并降低运动强度。","title":"适宜"},"travel":{"desc":"天热注意防晒，可选择水上娱乐项目。","title":"较适宜"},"uv":{"desc":"涂擦SPF大于15、PA+防晒护肤品","title":"中等"},"wash_car":{"desc":"无雨且风力较小，易保持清洁度","title":"较适宜"},"xq":{"desc":"天气热，容易烦躁","title":"较差"},"yh":{"desc":"不用担心天气来调皮捣乱而影响了兴致。 ","title":"较适宜"},"zs":{"desc":"气温较高，易中暑人群注意阴凉休息。","title":"可能中暑"}},"jiangshui":"8%","night_air_temperature":"20","night_weather":"多云","night_weather_code":"01","night_weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","night_wind_direction":"东南风","night_wind_power":"0-3级 <5.4m/s","sun_begin_end":"05:22|19:22","weekday":4,"ziwaixian":"中等"}
     * f4 : {"3hourForcast":[{"hour":"8时-14时","temperature":"22","temperature_max":"31","temperature_min":"20","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","wind_direction":"东南风","wind_power":"<3级,1"},{"hour":"14时-20时","temperature":"31","temperature_max":"31","temperature_min":"22","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","wind_direction":"东风","wind_power":"3-4级,3"},{"hour":"20时-2时","temperature":"26","temperature_max":"31","temperature_min":"20","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"东风","wind_power":"3-4级,0"},{"hour":"2时-8时","temperature":"20","temperature_max":"26","temperature_min":"20","weather":"晴","weather_pic":"http://app1.showapi.com/weather/icon/night/00.png","wind_direction":"东南风","wind_power":"<3级,0"}],"air_press":"993 hPa","day":"20190517","day_air_temperature":"32","day_weather":"多云","day_weather_code":"01","day_weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","day_wind_direction":"东风","day_wind_power":"3-4级 5.5~7.9m/s","index":{"ac":{"desc":"天气热，到中午的时候您将会感到有点热，因此建议在午后较热时开启制冷空调。","title":"部分时间开启"},"ag":{"desc":"天气条件不易诱发过敏，可放心外出，除特殊体质外，无需担心过敏问题。","title":"不易发"},"aqi":{"desc":"敏感人群症状易加剧，应避免高强度户外锻炼","title":"轻度污染"},"beauty":{"desc":"请选用露质面霜打底，水质无油粉底霜。","title":"去油"},"cl":{"desc":"天气炎热，建议停止户外运动。","title":"较不宜"},"clothes":{"desc":"适合穿T恤、短薄外套等夏季服装","title":"热"},"cold":{"desc":"感冒机率较低，避免长期处于空调屋中。","title":"少发"},"comfort":{"desc":"偏热或较热，部分人感觉不舒适","title":"较差"},"dy":{"desc":"天气太热,不适宜垂钓。","title":"不适宜钓鱼"},"gj":{"desc":"风稍大，出门逛街前记得给秀发定型。","title":"较适宜"},"glass":{"desc":"需要佩戴","title":"需要"},"hc":{"desc":"风大，需注意及时添衣。","title":"较适宜"},"ls":{"desc":"天气阴沉，不太适宜晾晒。","title":"不适宜"},"mf":{"desc":"头发需要保持清洁，同时要注意防晒，含防晒成分洗发护发品是很好的选择。","title":"一般"},"nl":{"desc":"天气较好，虽然有点风，比较适宜夜生活，您可以放心外出。","title":"较适宜"},"pj":{"desc":"炎热干燥，适宜饮用冰镇啤酒，不要贪杯。","title":"适宜"},"pk":{"desc":"天气酷热，不适宜放风筝。","title":"不宜"},"sports":{"desc":"天气炎热，建议停止户外运动。","title":"较不宜"},"travel":{"desc":"天气很热，如外出可选择水上娱乐项目。","title":"较不宜"},"uv":{"desc":"涂擦SPF大于15、PA+防晒护肤品","title":"中等"},"wash_car":{"desc":"无雨且风力较小，易保持清洁度","title":"较适宜"},"xq":{"desc":"天气热，容易烦躁","title":"较差"},"yh":{"desc":"天气较好，适宜约会","title":"较适宜"},"zs":{"desc":"气温较高，易中暑人群注意阴凉休息。","title":"可能中暑"}},"jiangshui":"6%","night_air_temperature":"19","night_weather":"晴","night_weather_code":"00","night_weather_pic":"http://app1.showapi.com/weather/icon/night/00.png","night_wind_direction":"东南风","night_wind_power":"0-3级 <5.4m/s","sun_begin_end":"05:21|19:23","weekday":5,"ziwaixian":"中等"}
     * f5 : {"3hourForcast":[{"hour":"8时-14时","temperature":"23","temperature_max":"31","temperature_min":"20","weather":"晴","weather_pic":"http://app1.showapi.com/weather/icon/day/00.png","wind_direction":"东南风","wind_power":"<3级,2"},{"hour":"14时-20时","temperature":"31","temperature_max":"31","temperature_min":"23","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","wind_direction":"东风","wind_power":"3-4级,3"},{"hour":"20时-2时","temperature":"24","temperature_max":"31","temperature_min":"18","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"东风","wind_power":"3-4级,0"},{"hour":"2时-8时","temperature":"18","temperature_max":"24","temperature_min":"17","weather":"小雨","weather_pic":"http://app1.showapi.com/weather/icon/night/07.png","wind_direction":"西北风","wind_power":"3-4级,0"}],"air_press":"993 hPa","day":"20190518","day_air_temperature":"32","day_weather":"多云","day_weather_code":"01","day_weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","day_wind_direction":"东风","day_wind_power":"3-4级 5.5~7.9m/s","index":{"ac":{"desc":"天气热同时湿度大，您需要适当开启制冷空调，来降温除湿，免受闷热之苦。","title":"部分时间开启"},"ag":{"desc":"天气条件极不易诱发过敏，可放心外出，享受生活。","title":"极不易发"},"aqi":{"desc":"敏感人群症状易加剧，应避免高强度户外锻炼","title":"轻度污染"},"beauty":{"desc":"请选用露质面霜打底，水质无油粉底霜。","title":"去油"},"cl":{"desc":"运动请注意防晒。","title":"较适宜"},"clothes":{"desc":"适合穿T恤、短薄外套等夏季服装","title":"热"},"cold":{"desc":"感冒机率较低，避免长期处于空调屋中。","title":"少发"},"comfort":{"desc":"偏热或较热，部分人感觉不舒适","title":"较差"},"dy":{"desc":"天气太热,不适宜垂钓。","title":"不适宜钓鱼"},"gj":{"desc":"风稍大，出门逛街前记得给秀发定型。","title":"较适宜"},"glass":{"desc":"不需要佩戴","title":"不需要"},"hc":{"desc":"风大，需注意及时添衣。","title":"较适宜"},"ls":{"desc":"天气阴沉，不太适宜晾晒。","title":"不适宜"},"mf":{"desc":"天热，头皮皮脂分泌会增多，这既会助长发丝间细菌滋生，也会使空气中悬浮物更易黏附在头发上，极易弄脏头发，注意保持头发的清洁。","title":"一般"},"nl":{"desc":"有降水，且风力很大，会给出行带来很大的不便，建议就近或最好在室内进行夜生活。","title":"较不适宜"},"pj":{"desc":"炎热干燥，适宜饮用冰镇啤酒，不要贪杯。","title":"适宜"},"pk":{"desc":"天气酷热，不适宜放风筝。","title":"不宜"},"sports":{"desc":"运动请注意防晒。","title":"较适宜"},"travel":{"desc":"天热注意防晒，可选择水上娱乐项目。","title":"较适宜"},"uv":{"desc":"辐射较弱，涂擦SPF12-15、PA+护肤品","title":"弱"},"wash_car":{"desc":"有雨，雨水和泥水会弄脏爱车","title":"不宜"},"xq":{"desc":"天气热，容易烦躁","title":"较差"},"yh":{"desc":"天气较好，适宜约会","title":"较适宜"},"zs":{"desc":"气温较高，易中暑人群注意阴凉休息。","title":"可能中暑"}},"jiangshui":"2%","night_air_temperature":"17","night_weather":"小雨","night_weather_code":"07","night_weather_pic":"http://app1.showapi.com/weather/icon/night/07.png","night_wind_direction":"西北风","night_wind_power":"4-5级 8.0~10.7m/s","sun_begin_end":"05:21|19:23","weekday":6,"ziwaixian":"弱"}
     * f6 : {"3hourForcast":[{"hour":"8时-14时","temperature":"17","temperature_max":"27","temperature_min":"17","weather":"中雨","weather_pic":"http://app1.showapi.com/weather/icon/day/08.png","wind_direction":"西北风","wind_power":"4-5级,3"},{"hour":"14时-20时","temperature":"27","temperature_max":"27","temperature_min":"17","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","wind_direction":"西北风","wind_power":"4-5级,2"},{"hour":"20时-2时","temperature":"20","temperature_max":"27","temperature_min":"15","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"西北风","wind_power":"3-4级,0"},{"hour":"2时-8时","temperature":"15","temperature_max":"20","temperature_min":"15","weather":"晴","weather_pic":"http://app1.showapi.com/weather/icon/night/00.png","wind_direction":"西北风","wind_power":"<3级,0"}],"air_press":"993 hPa","day":"20190519","day_air_temperature":"28","day_weather":"多云","day_weather_code":"01","day_weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","day_wind_direction":"西北风","day_wind_power":"4-5级 8.0~10.7m/s","index":{"ac":{"desc":"您将感到很舒适，一般不需要开启空调。","title":"较少开启"},"ag":{"desc":"天气条件极不易诱发过敏，风力较大，外出注意防风。","title":"极不易发"},"aqi":{"desc":"敏感人群症状易加剧，应避免高强度户外锻炼","title":"轻度污染"},"beauty":{"desc":"请选用露质面霜打底，水质无油粉底霜。","title":"去油"},"cl":{"desc":"运动请注意防晒。","title":"较适宜"},"clothes":{"desc":"建议穿长袖衬衫单裤等服装","title":"舒适"},"cold":{"desc":"感冒机率较低，避免长期处于空调屋中。","title":"少发"},"comfort":{"desc":"偏热或较热，部分人感觉不舒适","title":"较差"},"dy":{"desc":"天气稍热会对垂钓产生一定影响。","title":"较适宜"},"gj":{"desc":"风稍大，出门逛街前记得给秀发定型。","title":"较适宜"},"glass":{"desc":"不需要佩戴","title":"不需要"},"hc":{"desc":"风稍大会对划船产生一定影响。","title":"较适宜"},"ls":{"desc":"天气阴沉，不太适宜晾晒。","title":"不适宜"},"mf":{"desc":"空气干燥，且风力较大，这种天气往往将头发吹得杂乱无形，并且头发的水份和油份丢失也加快，请保持头发的清洁，并选用滋润型洗发露和护发素。　","title":"适宜"},"nl":{"desc":"天气较好，虽然有点风，但仍比较适宜夜生活，您可以放心外出。","title":"较适宜"},"pj":{"desc":"天气炎热，可适量饮用啤酒，不要过量。","title":"适宜"},"pk":{"desc":"气温略高，放风筝时戴上太阳帽。","title":"较适宜"},"sports":{"desc":"运动请注意防晒。","title":"较适宜"},"travel":{"desc":"天热注意防晒，可选择水上娱乐项目。","title":"较适宜"},"uv":{"desc":"辐射较弱，涂擦SPF12-15、PA+护肤品","title":"弱"},"wash_car":{"desc":"风力较大，洗车后会蒙上灰尘","title":"较不宜"},"xq":{"desc":"温度适宜，心情会不错。","title":"好"},"yh":{"desc":"风力较大，请您挑选避风的地点。","title":"不适宜"},"zs":{"desc":"气温不高，中暑几率极低。","title":"不容易中暑"}},"jiangshui":"10%","night_air_temperature":"15","night_weather":"晴","night_weather_code":"00","night_weather_pic":"http://app1.showapi.com/weather/icon/night/00.png","night_wind_direction":"西北风","night_wind_power":"0-3级 <5.4m/s","sun_begin_end":"05:20|19:24","weekday":7,"ziwaixian":"弱"}
     * f7 : {"3hourForcast":[{"hour":"8时-14时","temperature":"20","temperature_max":"30","temperature_min":"15","weather":"晴","weather_pic":"http://app1.showapi.com/weather/icon/day/00.png","wind_direction":"西北风","wind_power":"<3级,2"},{"hour":"14时-20时","temperature":"30","temperature_max":"30","temperature_min":"20","weather":"晴","weather_pic":"http://app1.showapi.com/weather/icon/day/00.png","wind_direction":"西北风","wind_power":"<3级,2"},{"hour":"20时-2时","temperature":"25","temperature_max":"30","temperature_min":"18","weather":"晴","weather_pic":"http://app1.showapi.com/weather/icon/night/00.png","wind_direction":"西北风","wind_power":"<3级,0"},{"hour":"2时-8时","temperature":"18","temperature_max":"25","temperature_min":"18","weather":"晴","weather_pic":"http://app1.showapi.com/weather/icon/night/00.png","wind_direction":"西南风","wind_power":"3-4级,0"}],"air_press":"993 hPa","day":"20190520","day_air_temperature":"31","day_weather":"晴","day_weather_code":"00","day_weather_pic":"http://app1.showapi.com/weather/icon/day/00.png","day_wind_direction":"西北风","day_wind_power":"0-3级 <5.4m/s","index":{"ac":{"desc":"天气热，到中午的时候您将会感到有点热，因此建议在午后较热时开启制冷空调。","title":"部分时间开启"},"ag":{"desc":"天气条件不易诱发过敏，可放心外出，除特殊体质外，无需担心过敏问题。","title":"不易发"},"aqi":{"desc":"敏感人群症状易加剧，应避免高强度户外锻炼","title":"轻度污染"},"beauty":{"desc":"请选用露质面霜打底，水质无油粉底霜。","title":"去油"},"cl":{"desc":"适当减少运动时间并降低运动强度。","title":"适宜"},"clothes":{"desc":"建议穿短衫、短裤等清凉夏季服装","title":"炎热"},"cold":{"desc":"感冒机率较低，避免长期处于空调屋中。","title":"少发"},"comfort":{"desc":"偏热或较热，部分人感觉不舒适","title":"较差"},"dy":{"desc":"天气稍热会对垂钓产生一定影响。","title":"较适宜"},"gj":{"desc":"这种好天气去逛街可使身心畅快放松。","title":"适宜"},"glass":{"desc":"很必要佩戴","title":"很必要"},"hc":{"desc":"天气较好，适宜划船及嬉玩水上运动。","title":"适宜"},"ls":{"desc":"请在室外通风的地方晾晒。","title":"较适宜"},"mf":{"desc":"头发需要保持清洁，同时要注意防晒，含防晒成分洗发护发品以及遮阳帽、伞是很好的选择。","title":"一般"},"nl":{"desc":"天气较好，虽然有点风，比较适宜夜生活，您可以放心外出。","title":"较适宜"},"pj":{"desc":"炎热干燥，适宜饮用冰镇啤酒，不要贪杯。","title":"适宜"},"pk":{"desc":"天气酷热，不适宜放风筝。","title":"不宜"},"sports":{"desc":"适当减少运动时间并降低运动强度。","title":"适宜"},"travel":{"desc":"天气很热，如外出可选择水上娱乐项目。","title":"较不宜"},"uv":{"desc":"涂擦SPF20以上，PA++护肤品，避强光","title":"很强"},"wash_car":{"desc":"无雨且风力较小，易保持清洁度","title":"较适宜"},"xq":{"desc":"天气热，容易烦躁","title":"较差"},"yh":{"desc":"不用担心天气来调皮捣乱而影响了兴致。 ","title":"较适宜"},"zs":{"desc":"气温较高，易中暑人群注意阴凉休息。","title":"可能中暑"}},"jiangshui":"0%","night_air_temperature":"18","night_weather":"晴","night_weather_code":"00","night_weather_pic":"http://app1.showapi.com/weather/icon/night/00.png","night_wind_direction":"西南风","night_wind_power":"3-4级 5.5~7.9m/s","sun_begin_end":"05:19|19:25","weekday":1,"ziwaixian":"很强"}
     * hourDataList : [{"aqi":"98","aqiDetail":{"aqi":"98","area":"郑州","co":"0.533","no2":"45","num":"284","o3":"57","o3_8h":"79","pm10":"147","pm2_5":"48","primary_pollutant":"颗粒物(PM10)","quality":"良好","so2":"9"},"sd":"35%","temperature":"17","temperature_time":"00:00","weather":"多云","weather_code":"01","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"南风","wind_power":"2级"},{"aqi":"99","aqiDetail":{"aqi":"99","area":"郑州","co":"0.533","no2":"50","num":"293","o3":"47","o3_8h":"75","pm10":"149","pm2_5":"46","primary_pollutant":"颗粒物(PM10)","quality":"良好","so2":"12"},"sd":"36%","temperature":"17","temperature_time":"00:30","weather":"多云","weather_code":"01","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"东南风 ","wind_power":"1级"},{"aqi":"99","aqiDetail":{"aqi":"99","area":"郑州","co":"0.533","no2":"50","num":"293","o3":"47","o3_8h":"75","pm10":"149","pm2_5":"46","primary_pollutant":"颗粒物(PM10)","quality":"良好","so2":"12"},"sd":"38%","temperature":"16","temperature_time":"01:00","weather":"多云","weather_code":"01","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"静风","wind_power":"0级"},{"aqi":"99","aqiDetail":{"aqi":"99","area":"郑州","co":"0.544","no2":"61","num":"290","o3":"33","o3_8h":"68","pm10":"148","pm2_5":"46","primary_pollutant":"颗粒物(PM10)","quality":"良好","so2":"14"},"sd":"35%","temperature":"16","temperature_time":"01:30","weather":"多云","weather_code":"01","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"南风","wind_power":"1级"},{"aqi":"99","aqiDetail":{"aqi":"99","area":"郑州","co":"0.544","no2":"61","num":"290","o3":"33","o3_8h":"68","pm10":"148","pm2_5":"46","primary_pollutant":"颗粒物(PM10)","quality":"良好","so2":"14"},"sd":"41%","temperature":"15","temperature_time":"02:00","weather":"多云","weather_code":"01","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"南风","wind_power":"1级"},{"aqi":"102","aqiDetail":{"aqi":"102","area":"郑州","co":"0.556","no2":"65","num":"300","o3":"27","o3_8h":"27","pm10":"150","pm2_5":"49","primary_pollutant":"颗粒物(PM10)","quality":"轻度污染","so2":"16"},"sd":"39%","temperature":"15","temperature_time":"02:30","weather":"多云","weather_code":"01","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"南风","wind_power":"1级"},{"aqi":"102","aqiDetail":{"aqi":"102","area":"郑州","co":"0.556","no2":"65","num":"300","o3":"27","o3_8h":"27","pm10":"150","pm2_5":"49","primary_pollutant":"颗粒物(PM10)","quality":"轻度污染","so2":"16"},"sd":"44%","temperature":"14","temperature_time":"03:00","weather":"多云","weather_code":"01","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"南风","wind_power":"1级"},{"aqi":"107","aqiDetail":{"aqi":"107","area":"郑州","co":"0.556","no2":"68","num":"311","o3":"24","o3_8h":"26","pm10":"161","pm2_5":"50","primary_pollutant":"颗粒物(PM10)","quality":"轻度污染","so2":"17"},"sd":"43%","temperature":"14","temperature_time":"03:30","weather":"多云","weather_code":"01","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"南风","wind_power":"1级"},{"aqi":"107","aqiDetail":{"aqi":"107","area":"郑州","co":"0.556","no2":"68","num":"311","o3":"24","o3_8h":"26","pm10":"161","pm2_5":"50","primary_pollutant":"颗粒物(PM10)","quality":"轻度污染","so2":"17"},"sd":"37%","temperature":"16","temperature_time":"04:00","weather":"多云","weather_code":"01","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"南风","wind_power":"2级"},{"aqi":"115","aqiDetail":{"aqi":"115","area":"郑州","co":"0.556","no2":"68","num":"321","o3":"24","o3_8h":"26","pm10":"161","pm2_5":"53","primary_pollutant":"颗粒物(PM10)","quality":"轻度污染","so2":"17"},"sd":"45%","temperature":"15","temperature_time":"04:30","weather":"多云","weather_code":"01","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"静风","wind_power":"0级"},{"aqi":"115","aqiDetail":{"aqi":"115","area":"郑州","co":"0.556","no2":"68","num":"321","o3":"24","o3_8h":"26","pm10":"161","pm2_5":"53","primary_pollutant":"颗粒物(PM10)","quality":"轻度污染","so2":"17"},"sd":"47%","temperature":"14","temperature_time":"05:00","weather":"多云","weather_code":"01","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"东北风","wind_power":"1级"},{"aqi":"123","aqiDetail":{"aqi":"123","area":"郑州","co":"0.578","no2":"65","num":"331","o3":"24","o3_8h":"25","pm10":"189","pm2_5":"56","primary_pollutant":"颗粒物(PM10)","quality":"轻度污染","so2":"17"},"sd":"52%","temperature":"14","temperature_time":"05:30","weather":"多云","weather_code":"01","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"东南风 ","wind_power":"2级"},{"aqi":"123","aqiDetail":{"aqi":"123","area":"郑州","co":"0.578","no2":"65","num":"331","o3":"24","o3_8h":"25","pm10":"189","pm2_5":"56","primary_pollutant":"颗粒物(PM10)","quality":"轻度污染","so2":"17"},"sd":"51%","temperature":"13","temperature_time":"06:00","weather":"多云","weather_code":"01","weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","wind_direction":"静风","wind_power":"0级"},{"aqi":"128","aqiDetail":{"aqi":"128","area":"郑州","co":"0.611","no2":"69","num":"338","o3":"21","o3_8h":"24","pm10":"199","pm2_5":"58","primary_pollutant":"颗粒物(PM10)","quality":"轻度污染","so2":"15"},"sd":"49%","temperature":"14","temperature_time":"06:30","weather":"阴","weather_code":"02","weather_pic":"http://app1.showapi.com/weather/icon/day/02.png","wind_direction":"南风","wind_power":"1级"},{"aqi":"128","aqiDetail":{"aqi":"128","area":"郑州","co":"0.611","no2":"69","num":"338","o3":"21","o3_8h":"24","pm10":"199","pm2_5":"58","primary_pollutant":"颗粒物(PM10)","quality":"轻度污染","so2":"15"},"sd":"48%","temperature":"15","temperature_time":"07:00","weather":"阴","weather_code":"02","weather_pic":"http://app1.showapi.com/weather/icon/day/02.png","wind_direction":"东南风 ","wind_power":"1级"},{"aqi":"133","aqiDetail":{"aqi":"133","area":"郑州","co":"0.633","no2":"77","num":"346","o3":"12","o3_8h":"22","pm10":"205","pm2_5":"64","primary_pollutant":"颗粒物(PM10)","quality":"轻度污染","so2":"15"},"sd":"40%","temperature":"17","temperature_time":"07:34","weather":"阴","weather_code":"02","weather_pic":"http://app1.showapi.com/weather/icon/day/02.png","wind_direction":"东南风 ","wind_power":"1级"},{"aqi":"133","aqiDetail":{"aqi":"133","area":"郑州","co":"0.633","no2":"77","num":"346","o3":"12","o3_8h":"22","pm10":"205","pm2_5":"64","primary_pollutant":"颗粒物(PM10)","quality":"轻度污染","so2":"15"},"sd":"39%","temperature":"18","temperature_time":"08:02","weather":"阴","weather_code":"02","weather_pic":"http://app1.showapi.com/weather/icon/day/02.png","wind_direction":"东南风 ","wind_power":"1级"},{"aqi":"135","aqiDetail":{"aqi":"135","area":"郑州","co":"0.722","no2":"80","num":"350","o3":"9","o3_8h":"20","pm10":"209","pm2_5":"69","primary_pollutant":"颗粒物(PM10)","quality":"轻度污染","so2":"15"},"sd":"35%","temperature":"19","temperature_time":"08:30","weather":"阴","weather_code":"02","weather_pic":"http://app1.showapi.com/weather/icon/day/02.png","wind_direction":"东北风","wind_power":"1级"},{"aqi":"135","aqiDetail":{"aqi":"135","area":"郑州","co":"0.722","no2":"80","num":"350","o3":"9","o3_8h":"20","pm10":"209","pm2_5":"69","primary_pollutant":"颗粒物(PM10)","quality":"轻度污染","so2":"15"},"sd":"32%","temperature":"21","temperature_time":"09:00","weather":"阴","weather_code":"02","weather_pic":"http://app1.showapi.com/weather/icon/day/02.png","wind_direction":"北风","wind_power":"1级"},{"aqi":"140","aqiDetail":{"aqi":"140","area":"郑州","co":"0.822","no2":"80","num":"353","o3":"13","o3_8h":"19","pm10":"231","pm2_5":"74","primary_pollutant":"颗粒物(PM10)","quality":"轻度污染","so2":"18"},"sd":"32%","temperature":"21","temperature_time":"09:30","weather":"阴","weather_code":"02","weather_pic":"http://app1.showapi.com/weather/icon/day/02.png","wind_direction":"南风","wind_power":"1级"},{"aqi":"140","aqiDetail":{"aqi":"140","area":"郑州","co":"0.822","no2":"80","num":"353","o3":"13","o3_8h":"19","pm10":"231","pm2_5":"74","primary_pollutant":"颗粒物(PM10)","quality":"轻度污染","so2":"18"},"sd":"30%","temperature":"23","temperature_time":"10:00","weather":"阴","weather_code":"02","weather_pic":"http://app1.showapi.com/weather/icon/day/02.png","wind_direction":"东南风 ","wind_power":"1级"},{"aqi":"148","aqiDetail":{"aqi":"148","area":"郑州","co":"0.822","no2":"74","num":"351","o3":"26","o3_8h":"18","pm10":"245","pm2_5":"82","primary_pollutant":"颗粒物(PM10)","quality":"轻度污染","so2":"21"},"sd":"32%","temperature":"24","temperature_time":"10:30","weather":"阴","weather_code":"02","weather_pic":"http://app1.showapi.com/weather/icon/day/02.png","wind_direction":"南风","wind_power":"2级"}]
     * now : {"aqi":"148","aqiDetail":{"aqi":"148","area":"郑州","co":"0.822","no2":"74","num":"351","o3":"26","o3_8h":"18","pm10":"245","pm2_5":"82","primary_pollutant":"颗粒物(PM10)","quality":"轻度污染","so2":"21"},"sd":"32%","temperature":"24","temperature_time":"10:30","weather":"阴","weather_code":"02","weather_pic":"http://app1.showapi.com/weather/icon/day/02.png","wind_direction":"南风","wind_power":"2级"}
     * ret_code : 0
     * time : 20190514073000
     */

    private CityInfoBean cityInfo;
    private WeatherBean f1;
    private WeatherBean f2;
    private WeatherBean f3;
    private WeatherBean f4;
    private WeatherBean f5;
    private WeatherBean f6;
    private WeatherBean f7;
    private NowBean now;
    private int ret_code;
    private String time;

    public WeatherBean getF2() {
        return f2;
    }

    public void setF2(WeatherBean f2) {
        this.f2 = f2;
    }

    public WeatherBean getF3() {
        return f3;
    }

    public void setF3(WeatherBean f3) {
        this.f3 = f3;
    }

    public WeatherBean getF4() {
        return f4;
    }

    public void setF4(WeatherBean f4) {
        this.f4 = f4;
    }

    public WeatherBean getF5() {
        return f5;
    }

    public void setF5(WeatherBean f5) {
        this.f5 = f5;
    }

    public WeatherBean getF6() {
        return f6;
    }

    public void setF6(WeatherBean f6) {
        this.f6 = f6;
    }

    public WeatherBean getF7() {
        return f7;
    }

    public void setF7(WeatherBean f7) {
        this.f7 = f7;
    }

    private List<HourDataListBean> hourDataList;

    public CityInfoBean getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfoBean cityInfo) {
        this.cityInfo = cityInfo;
    }

    public WeatherBean getF1() {
        return f1;
    }

    public void setF1(WeatherBean f1) {
        this.f1 = f1;
    }

    public NowBean getNow() {
        return now;
    }

    public void setNow(NowBean now) {
        this.now = now;
    }

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<HourDataListBean> getHourDataList() {
        return hourDataList;
    }

    public void setHourDataList(List<HourDataListBean> hourDataList) {
        this.hourDataList = hourDataList;
    }

    public static class CityInfoBean implements Parcelable {
        /**
         * c1 : 101180101
         * c10 : 1
         * c11 : 0371
         * c12 : 450000
         * c15 : 111
         * c16 : AZ9371
         * c17 : +8
         * c2 : zhengzhou
         * c3 : 郑州
         * c4 : zhengzhou
         * c5 : 郑州
         * c6 : henan
         * c7 : 河南
         * c8 : china
         * c9 : 中国
         * latitude : 34.758
         * longitude : 113.641
         */

        private String c1;
        private String c10;
        private String c11;
        private String c12;
        private String c15;
        private String c16;
        private String c17;
        private String c2;
        private String c3;
        private String c4;
        private String c5;
        private String c6;
        private String c7;
        private String c8;
        private String c9;
        private double latitude;
        private double longitude;

        public String getC1() {
            return c1;
        }

        public void setC1(String c1) {
            this.c1 = c1;
        }

        public String getC10() {
            return c10;
        }

        public void setC10(String c10) {
            this.c10 = c10;
        }

        public String getC11() {
            return c11;
        }

        public void setC11(String c11) {
            this.c11 = c11;
        }

        public String getC12() {
            return c12;
        }

        public void setC12(String c12) {
            this.c12 = c12;
        }

        public String getC15() {
            return c15;
        }

        public void setC15(String c15) {
            this.c15 = c15;
        }

        public String getC16() {
            return c16;
        }

        public void setC16(String c16) {
            this.c16 = c16;
        }

        public String getC17() {
            return c17;
        }

        public void setC17(String c17) {
            this.c17 = c17;
        }

        public String getC2() {
            return c2;
        }

        public void setC2(String c2) {
            this.c2 = c2;
        }

        public String getC3() {
            return c3;
        }

        public void setC3(String c3) {
            this.c3 = c3;
        }

        public String getC4() {
            return c4;
        }

        public void setC4(String c4) {
            this.c4 = c4;
        }

        public String getC5() {
            return c5;
        }

        public void setC5(String c5) {
            this.c5 = c5;
        }

        public String getC6() {
            return c6;
        }

        public void setC6(String c6) {
            this.c6 = c6;
        }

        public String getC7() {
            return c7;
        }

        public void setC7(String c7) {
            this.c7 = c7;
        }

        public String getC8() {
            return c8;
        }

        public void setC8(String c8) {
            this.c8 = c8;
        }

        public String getC9() {
            return c9;
        }

        public void setC9(String c9) {
            this.c9 = c9;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.c1);
            dest.writeString(this.c10);
            dest.writeString(this.c11);
            dest.writeString(this.c12);
            dest.writeString(this.c15);
            dest.writeString(this.c16);
            dest.writeString(this.c17);
            dest.writeString(this.c2);
            dest.writeString(this.c3);
            dest.writeString(this.c4);
            dest.writeString(this.c5);
            dest.writeString(this.c6);
            dest.writeString(this.c7);
            dest.writeString(this.c8);
            dest.writeString(this.c9);
            dest.writeDouble(this.latitude);
            dest.writeDouble(this.longitude);
        }

        public CityInfoBean() {
        }

        protected CityInfoBean(Parcel in) {
            this.c1 = in.readString();
            this.c10 = in.readString();
            this.c11 = in.readString();
            this.c12 = in.readString();
            this.c15 = in.readString();
            this.c16 = in.readString();
            this.c17 = in.readString();
            this.c2 = in.readString();
            this.c3 = in.readString();
            this.c4 = in.readString();
            this.c5 = in.readString();
            this.c6 = in.readString();
            this.c7 = in.readString();
            this.c8 = in.readString();
            this.c9 = in.readString();
            this.latitude = in.readDouble();
            this.longitude = in.readDouble();
        }

        public static final Parcelable.Creator<CityInfoBean> CREATOR = new Parcelable.Creator<CityInfoBean>() {
            @Override
            public CityInfoBean createFromParcel(Parcel source) {
                return new CityInfoBean(source);
            }

            @Override
            public CityInfoBean[] newArray(int size) {
                return new CityInfoBean[size];
            }
        };
    }

    public static class WeatherBean implements Parcelable {
        /**
         * 3hourForcast : [{"hour":"8时-11时","temperature":"20","temperature_max":"25","temperature_min":"20","weather":"阴","weather_pic":"http://app1.showapi.com/weather/icon/day/02.png","wind_direction":"南风","wind_power":"<3级,3"},{"hour":"11时-14时","temperature":"25","temperature_max":"26","temperature_min":"20","weather":"小雨","weather_pic":"http://app1.showapi.com/weather/icon/day/07.png","wind_direction":"南风","wind_power":"<3级,3"},{"hour":"14时-17时","temperature":"26","temperature_max":"26","temperature_min":"24","weather":"小雨","weather_pic":"http://app1.showapi.com/weather/icon/day/07.png","wind_direction":"南风","wind_power":"3-4级,3"},{"hour":"17时-20时","temperature":"24","temperature_max":"26","temperature_min":"21","weather":"小雨","weather_pic":"http://app1.showapi.com/weather/icon/day/07.png","wind_direction":"南风","wind_power":"3-4级,3"},{"hour":"20时-23时","temperature":"21","temperature_max":"24","temperature_min":"21","weather":"小雨","weather_pic":"http://app1.showapi.com/weather/icon/night/07.png","wind_direction":"南风","wind_power":"<3级,0"},{"hour":"23时-2时","temperature":"21","temperature_max":"21","temperature_min":"18","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"南风","wind_power":"<3级,0"},{"hour":"2时-5时","temperature":"18","temperature_max":"21","temperature_min":"18","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"南风","wind_power":"<3级,0"},{"hour":"5时-8时","temperature":"18","temperature_max":"21","temperature_min":"18","weather":"多云","weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","wind_direction":"南风","wind_power":"<3级,0"}]
         * air_press : 993 hPa
         * day : 20190514
         * day_air_temperature : 27
         * day_weather : 小雨
         * day_weather_code : 07
         * day_weather_pic : http://app1.showapi.com/weather/icon/day/07.png
         * day_wind_direction : 南风
         * day_wind_power : 3-4级 5.5~7.9m/s
         * index : {"ac":{"desc":"您将感到很舒适，一般不需要开启空调。","title":"较少开启"},"ag":{"desc":"天气条件不易诱发过敏，可放心外出，除特殊体质外，无需担心过敏问题。","title":"不易发"},"aqi":{"desc":"敏感人群症状易加剧，应避免高强度户外锻炼","title":"轻度污染"},"beauty":{"desc":"请选用保湿型霜类化妆品。","title":"保湿"},"cl":{"desc":"有降水,推荐您在室内进行休闲运动。","title":"较不宜"},"clothes":{"desc":"建议穿长袖衬衫单裤等服装","title":"舒适"},"cold":{"desc":"感冒机率较低，避免长期处于空调屋中。","title":"少发"},"comfort":{"desc":"热，感觉不舒适","title":"较差"},"dy":{"desc":"天气不好，不适合垂钓","title":"不适宜钓鱼"},"gj":{"desc":"有降水，出门带雨具并注意防雷。","title":"不适宜"},"glass":{"desc":"不需要佩戴","title":"不需要"},"hc":{"desc":"天气不好，建议选择别的娱乐方式。","title":"不适宜"},"ls":{"desc":"有降水会淋湿衣物，不适宜晾晒。","title":"不适宜"},"mf":{"desc":"空气干燥，且风力较大，这种天气往往将头发吹得杂乱无形，并且头发的水份和油份丢失也加快，请保持头发的清洁，并选用滋润型洗发露和护发素。　","title":"适宜"},"nl":{"desc":"有风，且有降水，会给您的出行带来很大的不便，建议就近或最好在室内进行夜生活。","title":"较不适宜"},"pj":{"desc":"天气炎热，可适量饮用啤酒，不要过量。","title":"适宜"},"pk":{"desc":"有雨，天气不好，不适宜放风筝。","title":"不适宜"},"sports":{"desc":"有降水,推荐您在室内进行休闲运动。","title":"较不宜"},"travel":{"desc":"有降水气温高，注意防暑降温携带雨具。","title":"较不宜"},"uv":{"desc":"辐射较弱，涂擦SPF12-15、PA+护肤品","title":"弱"},"wash_car":{"desc":"有雨，雨水和泥水会弄脏爱车","title":"不宜"},"xq":{"desc":"雨水可能会使心绪无端地挂上轻愁。","title":"较差"},"yh":{"desc":"建议尽量不要去室外约会。","title":"较不适宜"},"zs":{"desc":"气温不高，中暑几率极低。","title":"不容易中暑"}}
         * jiangshui : 71%
         * night_air_temperature : 18
         * night_weather : 多云
         * night_weather_code : 01
         * night_weather_pic : http://app1.showapi.com/weather/icon/night/01.png
         * night_wind_direction : 南风
         * night_wind_power : 0-3级 <5.4m/s
         * sun_begin_end : 05:24|19:20
         * weekday : 2
         * ziwaixian : 弱
         */

        private String air_press; //大气压
        private String day;  //日期
        private String day_air_temperature;//白天天气温度(摄氏度)
        private String day_weather;//白天天气温度(摄氏度)
        private String day_weather_code; //天气码
        private String day_weather_pic; //白天天气图标
        private String day_wind_direction;//白天风向
        private String day_wind_power; //白天风力
        private IndexBean index;
        private String jiangshui; //降水概率
        private String night_air_temperature; //晚上气温(摄氏度)
        private String night_weather;//晚上天气标识
        private String night_weather_code;
        private String night_weather_pic;//晚上天气图标
        private String night_wind_direction;//晚上风向
        private String night_wind_power;//晚上风力
        private String sun_begin_end; //日出|日落时间
        private int weekday;//星期几
        private String ziwaixian;    //紫外线强度
        @SerializedName("3hourForcast")
        private List<_$3hourForcastBean> _$3hourForcast;//每隔三个小时天气

        public String getAir_press() {
            return air_press;
        }

        public void setAir_press(String air_press) {
            this.air_press = air_press;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getDay_air_temperature() {
            return day_air_temperature;
        }

        public void setDay_air_temperature(String day_air_temperature) {
            this.day_air_temperature = day_air_temperature;
        }

        public String getDay_weather() {
            return day_weather;
        }

        public void setDay_weather(String day_weather) {
            this.day_weather = day_weather;
        }

        public String getDay_weather_code() {
            return day_weather_code;
        }

        public void setDay_weather_code(String day_weather_code) {
            this.day_weather_code = day_weather_code;
        }

        public String getDay_weather_pic() {
            return day_weather_pic;
        }

        public void setDay_weather_pic(String day_weather_pic) {
            this.day_weather_pic = day_weather_pic;
        }

        public String getDay_wind_direction() {
            return day_wind_direction;
        }

        public void setDay_wind_direction(String day_wind_direction) {
            this.day_wind_direction = day_wind_direction;
        }

        public String getDay_wind_power() {
            return day_wind_power;
        }

        public void setDay_wind_power(String day_wind_power) {
            this.day_wind_power = day_wind_power;
        }

        public IndexBean getIndex() {
            return index;
        }

        public void setIndex(IndexBean index) {
            this.index = index;
        }

        public String getJiangshui() {
            return jiangshui;
        }

        public void setJiangshui(String jiangshui) {
            this.jiangshui = jiangshui;
        }

        public String getNight_air_temperature() {
            return night_air_temperature;
        }

        public void setNight_air_temperature(String night_air_temperature) {
            this.night_air_temperature = night_air_temperature;
        }

        public String getNight_weather() {
            return night_weather;
        }

        public void setNight_weather(String night_weather) {
            this.night_weather = night_weather;
        }

        public String getNight_weather_code() {
            return night_weather_code;
        }

        public void setNight_weather_code(String night_weather_code) {
            this.night_weather_code = night_weather_code;
        }

        public String getNight_weather_pic() {
            return night_weather_pic;
        }

        public void setNight_weather_pic(String night_weather_pic) {
            this.night_weather_pic = night_weather_pic;
        }

        public String getNight_wind_direction() {
            return night_wind_direction;
        }

        public void setNight_wind_direction(String night_wind_direction) {
            this.night_wind_direction = night_wind_direction;
        }

        public String getNight_wind_power() {
            return night_wind_power;
        }

        public void setNight_wind_power(String night_wind_power) {
            this.night_wind_power = night_wind_power;
        }

        public String getSun_begin_end() {
            return sun_begin_end;
        }

        public void setSun_begin_end(String sun_begin_end) {
            this.sun_begin_end = sun_begin_end;
        }

        public int getWeekday() {
            return weekday;
        }

        public void setWeekday(int weekday) {
            this.weekday = weekday;
        }

        public String getZiwaixian() {
            return ziwaixian;
        }

        public void setZiwaixian(String ziwaixian) {
            this.ziwaixian = ziwaixian;
        }

        public List<_$3hourForcastBean> get_$3hourForcast() {
            return _$3hourForcast;
        }

        public void set_$3hourForcast(List<_$3hourForcastBean> _$3hourForcast) {
            this._$3hourForcast = _$3hourForcast;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.air_press);
            dest.writeString(this.day);
            dest.writeString(this.day_air_temperature);
            dest.writeString(this.day_weather);
            dest.writeString(this.day_weather_code);
            dest.writeString(this.day_weather_pic);
            dest.writeString(this.day_wind_direction);
            dest.writeString(this.day_wind_power);
            dest.writeParcelable(this.index, flags);
            dest.writeString(this.jiangshui);
            dest.writeString(this.night_air_temperature);
            dest.writeString(this.night_weather);
            dest.writeString(this.night_weather_code);
            dest.writeString(this.night_weather_pic);
            dest.writeString(this.night_wind_direction);
            dest.writeString(this.night_wind_power);
            dest.writeString(this.sun_begin_end);
            dest.writeInt(this.weekday);
            dest.writeString(this.ziwaixian);
            dest.writeTypedList(this._$3hourForcast);
        }

        public WeatherBean() {
        }

        protected WeatherBean(Parcel in) {
            this.air_press = in.readString();
            this.day = in.readString();
            this.day_air_temperature = in.readString();
            this.day_weather = in.readString();
            this.day_weather_code = in.readString();
            this.day_weather_pic = in.readString();
            this.day_wind_direction = in.readString();
            this.day_wind_power = in.readString();
            this.index = in.readParcelable(IndexBean.class.getClassLoader());
            this.jiangshui = in.readString();
            this.night_air_temperature = in.readString();
            this.night_weather = in.readString();
            this.night_weather_code = in.readString();
            this.night_weather_pic = in.readString();
            this.night_wind_direction = in.readString();
            this.night_wind_power = in.readString();
            this.sun_begin_end = in.readString();
            this.weekday = in.readInt();
            this.ziwaixian = in.readString();
            this._$3hourForcast = in.createTypedArrayList(_$3hourForcastBean.CREATOR);
        }

        public static final Parcelable.Creator<WeatherBean> CREATOR = new Parcelable.Creator<WeatherBean>() {
            @Override
            public WeatherBean createFromParcel(Parcel source) {
                return new WeatherBean(source);
            }

            @Override
            public WeatherBean[] newArray(int size) {
                return new WeatherBean[size];
            }
        };
    }

    public static class IndexBean implements Parcelable {
        /**
         * ac : {"desc":"您将感到很舒适，一般不需要开启空调。","title":"较少开启"}
         * ag : {"desc":"天气条件不易诱发过敏，可放心外出，除特殊体质外，无需担心过敏问题。","title":"不易发"}
         * aqi : {"desc":"敏感人群症状易加剧，应避免高强度户外锻炼","title":"轻度污染"}
         * beauty : {"desc":"请选用保湿型霜类化妆品。","title":"保湿"}
         * cl : {"desc":"有降水,推荐您在室内进行休闲运动。","title":"较不宜"}
         * clothes : {"desc":"建议穿长袖衬衫单裤等服装","title":"舒适"}
         * cold : {"desc":"感冒机率较低，避免长期处于空调屋中。","title":"少发"}
         * comfort : {"desc":"热，感觉不舒适","title":"较差"}
         * dy : {"desc":"天气不好，不适合垂钓","title":"不适宜钓鱼"}
         * gj : {"desc":"有降水，出门带雨具并注意防雷。","title":"不适宜"}
         * glass : {"desc":"不需要佩戴","title":"不需要"}
         * hc : {"desc":"天气不好，建议选择别的娱乐方式。","title":"不适宜"}
         * ls : {"desc":"有降水会淋湿衣物，不适宜晾晒。","title":"不适宜"}
         * mf : {"desc":"空气干燥，且风力较大，这种天气往往将头发吹得杂乱无形，并且头发的水份和油份丢失也加快，请保持头发的清洁，并选用滋润型洗发露和护发素。　","title":"适宜"}
         * nl : {"desc":"有风，且有降水，会给您的出行带来很大的不便，建议就近或最好在室内进行夜生活。","title":"较不适宜"}
         * pj : {"desc":"天气炎热，可适量饮用啤酒，不要过量。","title":"适宜"}
         * pk : {"desc":"有雨，天气不好，不适宜放风筝。","title":"不适宜"}
         * sports : {"desc":"有降水,推荐您在室内进行休闲运动。","title":"较不宜"}
         * travel : {"desc":"有降水气温高，注意防暑降温携带雨具。","title":"较不宜"}
         * uv : {"desc":"辐射较弱，涂擦SPF12-15、PA+护肤品","title":"弱"}
         * wash_car : {"desc":"有雨，雨水和泥水会弄脏爱车","title":"不宜"}
         * xq : {"desc":"雨水可能会使心绪无端地挂上轻愁。","title":"较差"}
         * yh : {"desc":"建议尽量不要去室外约会。","title":"较不适宜"}
         * zs : {"desc":"气温不高，中暑几率极低。","title":"不容易中暑"}
         */

        private AcBean ac;
        private AgBean ag;
        private AqiBean aqi;
        private BeautyBean beauty;
        private ClBean cl;
        private ClothesBean clothes;
        private ColdBean cold;
        private ComfortBean comfort;
        private DyBean dy;
        private GjBean gj;
        private GlassBean glass;
        private HcBean hc;
        private LsBean ls;
        private MfBean mf;
        private NlBean nl;
        private PjBean pj;
        private PkBean pk;
        private SportsBean sports;
        private TravelBean travel;
        private UvBean uv;
        private WashCarBean wash_car;
        private XqBean xq;
        private YhBean yh;
        private ZsBean zs;

        public AcBean getAc() {
            return ac;
        }

        public void setAc(AcBean ac) {
            this.ac = ac;
        }

        public AgBean getAg() {
            return ag;
        }

        public void setAg(AgBean ag) {
            this.ag = ag;
        }

        public AqiBean getAqi() {
            return aqi;
        }

        public void setAqi(AqiBean aqi) {
            this.aqi = aqi;
        }

        public BeautyBean getBeauty() {
            return beauty;
        }

        public void setBeauty(BeautyBean beauty) {
            this.beauty = beauty;
        }

        public ClBean getCl() {
            return cl;
        }

        public void setCl(ClBean cl) {
            this.cl = cl;
        }

        public ClothesBean getClothes() {
            return clothes;
        }

        public void setClothes(ClothesBean clothes) {
            this.clothes = clothes;
        }

        public ColdBean getCold() {
            return cold;
        }

        public void setCold(ColdBean cold) {
            this.cold = cold;
        }

        public ComfortBean getComfort() {
            return comfort;
        }

        public void setComfort(ComfortBean comfort) {
            this.comfort = comfort;
        }

        public DyBean getDy() {
            return dy;
        }

        public void setDy(DyBean dy) {
            this.dy = dy;
        }

        public GjBean getGj() {
            return gj;
        }

        public void setGj(GjBean gj) {
            this.gj = gj;
        }

        public GlassBean getGlass() {
            return glass;
        }

        public void setGlass(GlassBean glass) {
            this.glass = glass;
        }

        public HcBean getHc() {
            return hc;
        }

        public void setHc(HcBean hc) {
            this.hc = hc;
        }

        public LsBean getLs() {
            return ls;
        }

        public void setLs(LsBean ls) {
            this.ls = ls;
        }

        public MfBean getMf() {
            return mf;
        }

        public void setMf(MfBean mf) {
            this.mf = mf;
        }

        public NlBean getNl() {
            return nl;
        }

        public void setNl(NlBean nl) {
            this.nl = nl;
        }

        public PjBean getPj() {
            return pj;
        }

        public void setPj(PjBean pj) {
            this.pj = pj;
        }

        public PkBean getPk() {
            return pk;
        }

        public void setPk(PkBean pk) {
            this.pk = pk;
        }

        public SportsBean getSports() {
            return sports;
        }

        public void setSports(SportsBean sports) {
            this.sports = sports;
        }

        public TravelBean getTravel() {
            return travel;
        }

        public void setTravel(TravelBean travel) {
            this.travel = travel;
        }

        public UvBean getUv() {
            return uv;
        }

        public void setUv(UvBean uv) {
            this.uv = uv;
        }

        public WashCarBean getWash_car() {
            return wash_car;
        }

        public void setWash_car(WashCarBean wash_car) {
            this.wash_car = wash_car;
        }

        public XqBean getXq() {
            return xq;
        }

        public void setXq(XqBean xq) {
            this.xq = xq;
        }

        public YhBean getYh() {
            return yh;
        }

        public void setYh(YhBean yh) {
            this.yh = yh;
        }

        public ZsBean getZs() {
            return zs;
        }

        public void setZs(ZsBean zs) {
            this.zs = zs;
        }

        public static class AcBean implements Parcelable {
            /**
             * desc : 您将感到很舒适，一般不需要开启空调。
             * title : 较少开启
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public AcBean() {
            }

            protected AcBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<AcBean> CREATOR = new Parcelable.Creator<AcBean>() {
                @Override
                public AcBean createFromParcel(Parcel source) {
                    return new AcBean(source);
                }

                @Override
                public AcBean[] newArray(int size) {
                    return new AcBean[size];
                }
            };
        }

        public static class AgBean implements Parcelable {
            /**
             * desc : 天气条件不易诱发过敏，可放心外出，除特殊体质外，无需担心过敏问题。
             * title : 不易发
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public AgBean() {
            }

            protected AgBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<AgBean> CREATOR = new Parcelable.Creator<AgBean>() {
                @Override
                public AgBean createFromParcel(Parcel source) {
                    return new AgBean(source);
                }

                @Override
                public AgBean[] newArray(int size) {
                    return new AgBean[size];
                }
            };
        }

        public static class AqiBean implements Parcelable {
            /**
             * desc : 敏感人群症状易加剧，应避免高强度户外锻炼
             * title : 轻度污染
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public AqiBean() {
            }

            protected AqiBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<AqiBean> CREATOR = new Parcelable.Creator<AqiBean>() {
                @Override
                public AqiBean createFromParcel(Parcel source) {
                    return new AqiBean(source);
                }

                @Override
                public AqiBean[] newArray(int size) {
                    return new AqiBean[size];
                }
            };
        }

        public static class BeautyBean implements Parcelable  {
            /**
             * desc : 请选用保湿型霜类化妆品。
             * title : 保湿
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public BeautyBean() {
            }

            protected BeautyBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<BeautyBean> CREATOR = new Parcelable.Creator<BeautyBean>() {
                @Override
                public BeautyBean createFromParcel(Parcel source) {
                    return new BeautyBean(source);
                }

                @Override
                public BeautyBean[] newArray(int size) {
                    return new BeautyBean[size];
                }
            };
        }

        public static class ClBean implements Parcelable {
            /**
             * desc : 有降水,推荐您在室内进行休闲运动。
             * title : 较不宜
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public ClBean() {
            }

            protected ClBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<ClBean> CREATOR = new Parcelable.Creator<ClBean>() {
                @Override
                public ClBean createFromParcel(Parcel source) {
                    return new ClBean(source);
                }

                @Override
                public ClBean[] newArray(int size) {
                    return new ClBean[size];
                }
            };
        }

        public static class ClothesBean implements Parcelable {
            /**
             * desc : 建议穿长袖衬衫单裤等服装
             * title : 舒适
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public ClothesBean() {
            }

            protected ClothesBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<ClothesBean> CREATOR = new Parcelable.Creator<ClothesBean>() {
                @Override
                public ClothesBean createFromParcel(Parcel source) {
                    return new ClothesBean(source);
                }

                @Override
                public ClothesBean[] newArray(int size) {
                    return new ClothesBean[size];
                }
            };
        }

        public static class ColdBean implements Parcelable {
            /**
             * desc : 感冒机率较低，避免长期处于空调屋中。
             * title : 少发
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public ColdBean() {
            }

            protected ColdBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<ColdBean> CREATOR = new Parcelable.Creator<ColdBean>() {
                @Override
                public ColdBean createFromParcel(Parcel source) {
                    return new ColdBean(source);
                }

                @Override
                public ColdBean[] newArray(int size) {
                    return new ColdBean[size];
                }
            };
        }

        public static class ComfortBean implements Parcelable {
            /**
             * desc : 热，感觉不舒适
             * title : 较差
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public ComfortBean() {
            }

            protected ComfortBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<ComfortBean> CREATOR = new Parcelable.Creator<ComfortBean>() {
                @Override
                public ComfortBean createFromParcel(Parcel source) {
                    return new ComfortBean(source);
                }

                @Override
                public ComfortBean[] newArray(int size) {
                    return new ComfortBean[size];
                }
            };
        }

        public static class DyBean implements Parcelable {
            /**
             * desc : 天气不好，不适合垂钓
             * title : 不适宜钓鱼
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public DyBean() {
            }

            protected DyBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<DyBean> CREATOR = new Parcelable.Creator<DyBean>() {
                @Override
                public DyBean createFromParcel(Parcel source) {
                    return new DyBean(source);
                }

                @Override
                public DyBean[] newArray(int size) {
                    return new DyBean[size];
                }
            };
        }

        public static class GjBean implements Parcelable {
            /**
             * desc : 有降水，出门带雨具并注意防雷。
             * title : 不适宜
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public GjBean() {
            }

            protected GjBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<GjBean> CREATOR = new Parcelable.Creator<GjBean>() {
                @Override
                public GjBean createFromParcel(Parcel source) {
                    return new GjBean(source);
                }

                @Override
                public GjBean[] newArray(int size) {
                    return new GjBean[size];
                }
            };
        }

        public static class GlassBean implements Parcelable {
            /**
             * desc : 不需要佩戴
             * title : 不需要
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public GlassBean() {
            }

            protected GlassBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<GlassBean> CREATOR = new Parcelable.Creator<GlassBean>() {
                @Override
                public GlassBean createFromParcel(Parcel source) {
                    return new GlassBean(source);
                }

                @Override
                public GlassBean[] newArray(int size) {
                    return new GlassBean[size];
                }
            };
        }

        public static class HcBean implements Parcelable {
            /**
             * desc : 天气不好，建议选择别的娱乐方式。
             * title : 不适宜
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public HcBean() {
            }

            protected HcBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<HcBean> CREATOR = new Parcelable.Creator<HcBean>() {
                @Override
                public HcBean createFromParcel(Parcel source) {
                    return new HcBean(source);
                }

                @Override
                public HcBean[] newArray(int size) {
                    return new HcBean[size];
                }
            };
        }

        public static class LsBean implements Parcelable {
            /**
             * desc : 有降水会淋湿衣物，不适宜晾晒。
             * title : 不适宜
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public LsBean() {
            }

            protected LsBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<LsBean> CREATOR = new Parcelable.Creator<LsBean>() {
                @Override
                public LsBean createFromParcel(Parcel source) {
                    return new LsBean(source);
                }

                @Override
                public LsBean[] newArray(int size) {
                    return new LsBean[size];
                }
            };
        }

        public static class MfBean implements Parcelable {
            /**
             * desc : 空气干燥，且风力较大，这种天气往往将头发吹得杂乱无形，并且头发的水份和油份丢失也加快，请保持头发的清洁，并选用滋润型洗发露和护发素。
             * title : 适宜
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public MfBean() {
            }

            protected MfBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<MfBean> CREATOR = new Parcelable.Creator<MfBean>() {
                @Override
                public MfBean createFromParcel(Parcel source) {
                    return new MfBean(source);
                }

                @Override
                public MfBean[] newArray(int size) {
                    return new MfBean[size];
                }
            };
        }

        public static class NlBean implements Parcelable {
            /**
             * desc : 有风，且有降水，会给您的出行带来很大的不便，建议就近或最好在室内进行夜生活。
             * title : 较不适宜
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public NlBean() {
            }

            protected NlBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<NlBean> CREATOR = new Parcelable.Creator<NlBean>() {
                @Override
                public NlBean createFromParcel(Parcel source) {
                    return new NlBean(source);
                }

                @Override
                public NlBean[] newArray(int size) {
                    return new NlBean[size];
                }
            };
        }

        public static class PjBean implements Parcelable {
            /**
             * desc : 天气炎热，可适量饮用啤酒，不要过量。
             * title : 适宜
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public PjBean() {
            }

            protected PjBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<PjBean> CREATOR = new Parcelable.Creator<PjBean>() {
                @Override
                public PjBean createFromParcel(Parcel source) {
                    return new PjBean(source);
                }

                @Override
                public PjBean[] newArray(int size) {
                    return new PjBean[size];
                }
            };
        }

        public static class PkBean implements Parcelable {
            /**
             * desc : 有雨，天气不好，不适宜放风筝。
             * title : 不适宜
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public PkBean() {
            }

            protected PkBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<PkBean> CREATOR = new Parcelable.Creator<PkBean>() {
                @Override
                public PkBean createFromParcel(Parcel source) {
                    return new PkBean(source);
                }

                @Override
                public PkBean[] newArray(int size) {
                    return new PkBean[size];
                }
            };
        }

        public static class SportsBean implements Parcelable {
            /**
             * desc : 有降水,推荐您在室内进行休闲运动。
             * title : 较不宜
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public SportsBean() {
            }

            protected SportsBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<SportsBean> CREATOR = new Parcelable.Creator<SportsBean>() {
                @Override
                public SportsBean createFromParcel(Parcel source) {
                    return new SportsBean(source);
                }

                @Override
                public SportsBean[] newArray(int size) {
                    return new SportsBean[size];
                }
            };
        }

        public static class TravelBean implements Parcelable {
            /**
             * desc : 有降水气温高，注意防暑降温携带雨具。
             * title : 较不宜
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public TravelBean() {
            }

            protected TravelBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<TravelBean> CREATOR = new Parcelable.Creator<TravelBean>() {
                @Override
                public TravelBean createFromParcel(Parcel source) {
                    return new TravelBean(source);
                }

                @Override
                public TravelBean[] newArray(int size) {
                    return new TravelBean[size];
                }
            };
        }

        public static class UvBean implements Parcelable {
            /**
             * desc : 辐射较弱，涂擦SPF12-15、PA+护肤品
             * title : 弱
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public UvBean() {
            }

            protected UvBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<UvBean> CREATOR = new Parcelable.Creator<UvBean>() {
                @Override
                public UvBean createFromParcel(Parcel source) {
                    return new UvBean(source);
                }

                @Override
                public UvBean[] newArray(int size) {
                    return new UvBean[size];
                }
            };
        }

        public static class WashCarBean implements Parcelable {
            /**
             * desc : 有雨，雨水和泥水会弄脏爱车
             * title : 不宜
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public WashCarBean() {
            }

            protected WashCarBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<WashCarBean> CREATOR = new Parcelable.Creator<WashCarBean>() {
                @Override
                public WashCarBean createFromParcel(Parcel source) {
                    return new WashCarBean(source);
                }

                @Override
                public WashCarBean[] newArray(int size) {
                    return new WashCarBean[size];
                }
            };
        }

        public static class XqBean implements Parcelable {
            /**
             * desc : 雨水可能会使心绪无端地挂上轻愁。
             * title : 较差
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public XqBean() {
            }

            protected XqBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<XqBean> CREATOR = new Parcelable.Creator<XqBean>() {
                @Override
                public XqBean createFromParcel(Parcel source) {
                    return new XqBean(source);
                }

                @Override
                public XqBean[] newArray(int size) {
                    return new XqBean[size];
                }
            };
        }

        public static class YhBean implements Parcelable {
            /**
             * desc : 建议尽量不要去室外约会。
             * title : 较不适宜
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public YhBean() {
            }

            protected YhBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<YhBean> CREATOR = new Parcelable.Creator<YhBean>() {
                @Override
                public YhBean createFromParcel(Parcel source) {
                    return new YhBean(source);
                }

                @Override
                public YhBean[] newArray(int size) {
                    return new YhBean[size];
                }
            };
        }

        public static class ZsBean implements Parcelable {
            /**
             * desc : 气温不高，中暑几率极低。
             * title : 不容易中暑
             */

            private String desc;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.desc);
                dest.writeString(this.title);
            }

            public ZsBean() {
            }

            protected ZsBean(Parcel in) {
                this.desc = in.readString();
                this.title = in.readString();
            }

            public static final Parcelable.Creator<ZsBean> CREATOR = new Parcelable.Creator<ZsBean>() {
                @Override
                public ZsBean createFromParcel(Parcel source) {
                    return new ZsBean(source);
                }

                @Override
                public ZsBean[] newArray(int size) {
                    return new ZsBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.ac, flags);
            dest.writeParcelable(this.ag, flags);
            dest.writeParcelable(this.aqi, flags);
            dest.writeParcelable(this.beauty, flags);
            dest.writeParcelable(this.cl, flags);
            dest.writeParcelable(this.clothes, flags);
            dest.writeParcelable(this.cold, flags);
            dest.writeParcelable(this.comfort, flags);
            dest.writeParcelable(this.dy, flags);
            dest.writeParcelable(this.gj, flags);
            dest.writeParcelable(this.glass, flags);
            dest.writeParcelable(this.hc, flags);
            dest.writeParcelable(this.ls, flags);
            dest.writeParcelable(this.mf, flags);
            dest.writeParcelable(this.nl, flags);
            dest.writeParcelable(this.pj, flags);
            dest.writeParcelable(this.pk, flags);
            dest.writeParcelable(this.sports, flags);
            dest.writeParcelable(this.travel, flags);
            dest.writeParcelable(this.uv, flags);
            dest.writeParcelable(this.wash_car, flags);
            dest.writeParcelable(this.xq, flags);
            dest.writeParcelable(this.yh, flags);
            dest.writeParcelable(this.zs, flags);
        }

        public IndexBean() {
        }

        protected IndexBean(Parcel in) {
            this.ac = in.readParcelable(AcBean.class.getClassLoader());
            this.ag = in.readParcelable(AgBean.class.getClassLoader());
            this.aqi = in.readParcelable(AqiBean.class.getClassLoader());
            this.beauty = in.readParcelable(BeautyBean.class.getClassLoader());
            this.cl = in.readParcelable(ClBean.class.getClassLoader());
            this.clothes = in.readParcelable(ClothesBean.class.getClassLoader());
            this.cold = in.readParcelable(ColdBean.class.getClassLoader());
            this.comfort = in.readParcelable(ComfortBean.class.getClassLoader());
            this.dy = in.readParcelable(DyBean.class.getClassLoader());
            this.gj = in.readParcelable(GjBean.class.getClassLoader());
            this.glass = in.readParcelable(GlassBean.class.getClassLoader());
            this.hc = in.readParcelable(HcBean.class.getClassLoader());
            this.ls = in.readParcelable(LsBean.class.getClassLoader());
            this.mf = in.readParcelable(MfBean.class.getClassLoader());
            this.nl = in.readParcelable(NlBean.class.getClassLoader());
            this.pj = in.readParcelable(PjBean.class.getClassLoader());
            this.pk = in.readParcelable(PkBean.class.getClassLoader());
            this.sports = in.readParcelable(SportsBean.class.getClassLoader());
            this.travel = in.readParcelable(TravelBean.class.getClassLoader());
            this.uv = in.readParcelable(UvBean.class.getClassLoader());
            this.wash_car = in.readParcelable(WashCarBean.class.getClassLoader());
            this.xq = in.readParcelable(XqBean.class.getClassLoader());
            this.yh = in.readParcelable(YhBean.class.getClassLoader());
            this.zs = in.readParcelable(ZsBean.class.getClassLoader());
        }

        public static final Parcelable.Creator<IndexBean> CREATOR = new Parcelable.Creator<IndexBean>() {
            @Override
            public IndexBean createFromParcel(Parcel source) {
                return new IndexBean(source);
            }

            @Override
            public IndexBean[] newArray(int size) {
                return new IndexBean[size];
            }
        };
    }

    public static class _$3hourForcastBean implements Parcelable {
        /**
         * hour : 8时-11时
         * temperature : 20
         * temperature_max : 25
         * temperature_min : 20
         * weather : 阴
         * weather_pic : http://app1.showapi.com/weather/icon/day/02.png
         * wind_direction : 南风
         * wind_power : <3级,3
         */

        private String hour;
        private String temperature;
        private String temperature_max;
        private String temperature_min;
        private String weather;
        private String weather_pic;
        private String wind_direction;
        private String wind_power;

        public String getHour() {
            return hour;
        }

        public void setHour(String hour) {
            this.hour = hour;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getTemperature_max() {
            return temperature_max;
        }

        public void setTemperature_max(String temperature_max) {
            this.temperature_max = temperature_max;
        }

        public String getTemperature_min() {
            return temperature_min;
        }

        public void setTemperature_min(String temperature_min) {
            this.temperature_min = temperature_min;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getWeather_pic() {
            return weather_pic;
        }

        public void setWeather_pic(String weather_pic) {
            this.weather_pic = weather_pic;
        }

        public String getWind_direction() {
            return wind_direction;
        }

        public void setWind_direction(String wind_direction) {
            this.wind_direction = wind_direction;
        }

        public String getWind_power() {
            return wind_power;
        }

        public void setWind_power(String wind_power) {
            this.wind_power = wind_power;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.hour);
            dest.writeString(this.temperature);
            dest.writeString(this.temperature_max);
            dest.writeString(this.temperature_min);
            dest.writeString(this.weather);
            dest.writeString(this.weather_pic);
            dest.writeString(this.wind_direction);
            dest.writeString(this.wind_power);
        }

        public _$3hourForcastBean() {
        }

        protected _$3hourForcastBean(Parcel in) {
            this.hour = in.readString();
            this.temperature = in.readString();
            this.temperature_max = in.readString();
            this.temperature_min = in.readString();
            this.weather = in.readString();
            this.weather_pic = in.readString();
            this.wind_direction = in.readString();
            this.wind_power = in.readString();
        }

        public static final Parcelable.Creator<_$3hourForcastBean> CREATOR = new Parcelable.Creator<_$3hourForcastBean>() {
            @Override
            public _$3hourForcastBean createFromParcel(Parcel source) {
                return new _$3hourForcastBean(source);
            }

            @Override
            public _$3hourForcastBean[] newArray(int size) {
                return new _$3hourForcastBean[size];
            }
        };
    }

    public static class NowBean implements Parcelable {
        /**
         * aqi : 148
         * aqiDetail : {"aqi":"148","area":"郑州","co":"0.822","no2":"74","num":"351","o3":"26","o3_8h":"18","pm10":"245","pm2_5":"82","primary_pollutant":"颗粒物(PM10)","quality":"轻度污染","so2":"21"}
         * sd : 32%
         * temperature : 24
         * temperature_time : 10:30
         * weather : 阴
         * weather_code : 02
         * weather_pic : http://app1.showapi.com/weather/icon/day/02.png
         * wind_direction : 南风
         * wind_power : 2级
         */

        private String aqi;
        private AqiDetailBean aqiDetail;
        private String sd;
        private String temperature;
        private String temperature_time;
        private String weather;
        private String weather_code;
        private String weather_pic;
        private String wind_direction;
        private String wind_power;

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public AqiDetailBean getAqiDetail() {
            return aqiDetail;
        }

        public void setAqiDetail(AqiDetailBean aqiDetail) {
            this.aqiDetail = aqiDetail;
        }

        public String getSd() {
            return sd;
        }

        public void setSd(String sd) {
            this.sd = sd;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getTemperature_time() {
            return temperature_time;
        }

        public void setTemperature_time(String temperature_time) {
            this.temperature_time = temperature_time;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getWeather_code() {
            return weather_code;
        }

        public void setWeather_code(String weather_code) {
            this.weather_code = weather_code;
        }

        public String getWeather_pic() {
            return weather_pic;
        }

        public void setWeather_pic(String weather_pic) {
            this.weather_pic = weather_pic;
        }

        public String getWind_direction() {
            return wind_direction;
        }

        public void setWind_direction(String wind_direction) {
            this.wind_direction = wind_direction;
        }

        public String getWind_power() {
            return wind_power;
        }

        public void setWind_power(String wind_power) {
            this.wind_power = wind_power;
        }

        public static class AqiDetailBean implements Parcelable {
            /**
             * aqi : 148
             * area : 郑州
             * co : 0.822
             * no2 : 74
             * num : 351
             * o3 : 26
             * o3_8h : 18
             * pm10 : 245
             * pm2_5 : 82
             * primary_pollutant : 颗粒物(PM10)
             * quality : 轻度污染
             * so2 : 21
             */

            private String aqi;
            private String area;
            private String co;
            private String no2;
            private String num;
            private String o3;
            private String o3_8h;
            private String pm10;
            private String pm2_5;
            private String primary_pollutant;
            private String quality;
            private String so2;

            public String getAqi() {
                return aqi;
            }

            public void setAqi(String aqi) {
                this.aqi = aqi;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getCo() {
                return co;
            }

            public void setCo(String co) {
                this.co = co;
            }

            public String getNo2() {
                return no2;
            }

            public void setNo2(String no2) {
                this.no2 = no2;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getO3() {
                return o3;
            }

            public void setO3(String o3) {
                this.o3 = o3;
            }

            public String getO3_8h() {
                return o3_8h;
            }

            public void setO3_8h(String o3_8h) {
                this.o3_8h = o3_8h;
            }

            public String getPm10() {
                return pm10;
            }

            public void setPm10(String pm10) {
                this.pm10 = pm10;
            }

            public String getPm2_5() {
                return pm2_5;
            }

            public void setPm2_5(String pm2_5) {
                this.pm2_5 = pm2_5;
            }

            public String getPrimary_pollutant() {
                return primary_pollutant;
            }

            public void setPrimary_pollutant(String primary_pollutant) {
                this.primary_pollutant = primary_pollutant;
            }

            public String getQuality() {
                return quality;
            }

            public void setQuality(String quality) {
                this.quality = quality;
            }

            public String getSo2() {
                return so2;
            }

            public void setSo2(String so2) {
                this.so2 = so2;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.aqi);
                dest.writeString(this.area);
                dest.writeString(this.co);
                dest.writeString(this.no2);
                dest.writeString(this.num);
                dest.writeString(this.o3);
                dest.writeString(this.o3_8h);
                dest.writeString(this.pm10);
                dest.writeString(this.pm2_5);
                dest.writeString(this.primary_pollutant);
                dest.writeString(this.quality);
                dest.writeString(this.so2);
            }

            public AqiDetailBean() {
            }

            protected AqiDetailBean(Parcel in) {
                this.aqi = in.readString();
                this.area = in.readString();
                this.co = in.readString();
                this.no2 = in.readString();
                this.num = in.readString();
                this.o3 = in.readString();
                this.o3_8h = in.readString();
                this.pm10 = in.readString();
                this.pm2_5 = in.readString();
                this.primary_pollutant = in.readString();
                this.quality = in.readString();
                this.so2 = in.readString();
            }

            public static final Parcelable.Creator<AqiDetailBean> CREATOR = new Parcelable.Creator<AqiDetailBean>() {
                @Override
                public AqiDetailBean createFromParcel(Parcel source) {
                    return new AqiDetailBean(source);
                }

                @Override
                public AqiDetailBean[] newArray(int size) {
                    return new AqiDetailBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.aqi);
            dest.writeParcelable(this.aqiDetail, flags);
            dest.writeString(this.sd);
            dest.writeString(this.temperature);
            dest.writeString(this.temperature_time);
            dest.writeString(this.weather);
            dest.writeString(this.weather_code);
            dest.writeString(this.weather_pic);
            dest.writeString(this.wind_direction);
            dest.writeString(this.wind_power);
        }

        public NowBean() {
        }

        protected NowBean(Parcel in) {
            this.aqi = in.readString();
            this.aqiDetail = in.readParcelable(AqiDetailBean.class.getClassLoader());
            this.sd = in.readString();
            this.temperature = in.readString();
            this.temperature_time = in.readString();
            this.weather = in.readString();
            this.weather_code = in.readString();
            this.weather_pic = in.readString();
            this.wind_direction = in.readString();
            this.wind_power = in.readString();
        }

        public static final Parcelable.Creator<NowBean> CREATOR = new Parcelable.Creator<NowBean>() {
            @Override
            public NowBean createFromParcel(Parcel source) {
                return new NowBean(source);
            }

            @Override
            public NowBean[] newArray(int size) {
                return new NowBean[size];
            }
        };
    }

    public static class HourDataListBean implements Parcelable {
        /**
         * aqi : 98
         * aqiDetail : {"aqi":"98","area":"郑州","co":"0.533","no2":"45","num":"284","o3":"57","o3_8h":"79","pm10":"147","pm2_5":"48","primary_pollutant":"颗粒物(PM10)","quality":"良好","so2":"9"}
         * sd : 35%
         * temperature : 17
         * temperature_time : 00:00
         * weather : 多云
         * weather_code : 01
         * weather_pic : http://app1.showapi.com/weather/icon/night/01.png
         * wind_direction : 南风
         * wind_power : 2级
         */

        private String aqi;
        private AqiDetailBeanX aqiDetail;
        private String sd;
        private String temperature;
        private String temperature_time;
        private String weather;
        private String weather_code;
        private String weather_pic;
        private String wind_direction;
        private String wind_power;

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public AqiDetailBeanX getAqiDetail() {
            return aqiDetail;
        }

        public void setAqiDetail(AqiDetailBeanX aqiDetail) {
            this.aqiDetail = aqiDetail;
        }

        public String getSd() {
            return sd;
        }

        public void setSd(String sd) {
            this.sd = sd;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getTemperature_time() {
            return temperature_time;
        }

        public void setTemperature_time(String temperature_time) {
            this.temperature_time = temperature_time;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getWeather_code() {
            return weather_code;
        }

        public void setWeather_code(String weather_code) {
            this.weather_code = weather_code;
        }

        public String getWeather_pic() {
            return weather_pic;
        }

        public void setWeather_pic(String weather_pic) {
            this.weather_pic = weather_pic;
        }

        public String getWind_direction() {
            return wind_direction;
        }

        public void setWind_direction(String wind_direction) {
            this.wind_direction = wind_direction;
        }

        public String getWind_power() {
            return wind_power;
        }

        public void setWind_power(String wind_power) {
            this.wind_power = wind_power;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.aqi);
            dest.writeParcelable(this.aqiDetail, flags);
            dest.writeString(this.sd);
            dest.writeString(this.temperature);
            dest.writeString(this.temperature_time);
            dest.writeString(this.weather);
            dest.writeString(this.weather_code);
            dest.writeString(this.weather_pic);
            dest.writeString(this.wind_direction);
            dest.writeString(this.wind_power);
        }

        public HourDataListBean() {
        }

        protected HourDataListBean(Parcel in) {
            this.aqi = in.readString();
            this.aqiDetail = in.readParcelable(AqiDetailBeanX.class.getClassLoader());
            this.sd = in.readString();
            this.temperature = in.readString();
            this.temperature_time = in.readString();
            this.weather = in.readString();
            this.weather_code = in.readString();
            this.weather_pic = in.readString();
            this.wind_direction = in.readString();
            this.wind_power = in.readString();
        }

        public static final Parcelable.Creator<HourDataListBean> CREATOR = new Parcelable.Creator<HourDataListBean>() {
            @Override
            public HourDataListBean createFromParcel(Parcel source) {
                return new HourDataListBean(source);
            }

            @Override
            public HourDataListBean[] newArray(int size) {
                return new HourDataListBean[size];
            }
        };
    }

    public static class AqiDetailBeanX implements Parcelable {
        /**
         * aqi : 98
         * area : 郑州
         * co : 0.533
         * no2 : 45
         * num : 284
         * o3 : 57
         * o3_8h : 79
         * pm10 : 147
         * pm2_5 : 48
         * primary_pollutant : 颗粒物(PM10)
         * quality : 良好
         * so2 : 9
         */

        private String aqi;
        private String area;
        private String co;
        private String no2;
        private String num;
        private String o3;
        private String o3_8h;
        private String pm10;
        private String pm2_5;
        private String primary_pollutant;
        private String quality;
        private String so2;

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCo() {
            return co;
        }

        public void setCo(String co) {
            this.co = co;
        }

        public String getNo2() {
            return no2;
        }

        public void setNo2(String no2) {
            this.no2 = no2;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getO3() {
            return o3;
        }

        public void setO3(String o3) {
            this.o3 = o3;
        }

        public String getO3_8h() {
            return o3_8h;
        }

        public void setO3_8h(String o3_8h) {
            this.o3_8h = o3_8h;
        }

        public String getPm10() {
            return pm10;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getPm2_5() {
            return pm2_5;
        }

        public void setPm2_5(String pm2_5) {
            this.pm2_5 = pm2_5;
        }

        public String getPrimary_pollutant() {
            return primary_pollutant;
        }

        public void setPrimary_pollutant(String primary_pollutant) {
            this.primary_pollutant = primary_pollutant;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getSo2() {
            return so2;
        }

        public void setSo2(String so2) {
            this.so2 = so2;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.aqi);
            dest.writeString(this.area);
            dest.writeString(this.co);
            dest.writeString(this.no2);
            dest.writeString(this.num);
            dest.writeString(this.o3);
            dest.writeString(this.o3_8h);
            dest.writeString(this.pm10);
            dest.writeString(this.pm2_5);
            dest.writeString(this.primary_pollutant);
            dest.writeString(this.quality);
            dest.writeString(this.so2);
        }

        public AqiDetailBeanX() {
        }

        protected AqiDetailBeanX(Parcel in) {
            this.aqi = in.readString();
            this.area = in.readString();
            this.co = in.readString();
            this.no2 = in.readString();
            this.num = in.readString();
            this.o3 = in.readString();
            this.o3_8h = in.readString();
            this.pm10 = in.readString();
            this.pm2_5 = in.readString();
            this.primary_pollutant = in.readString();
            this.quality = in.readString();
            this.so2 = in.readString();
        }

        public static final Parcelable.Creator<AqiDetailBeanX> CREATOR = new Parcelable.Creator<AqiDetailBeanX>() {
            @Override
            public AqiDetailBeanX createFromParcel(Parcel source) {
                return new AqiDetailBeanX(source);
            }

            @Override
            public AqiDetailBeanX[] newArray(int size) {
                return new AqiDetailBeanX[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.cityInfo, flags);
        dest.writeParcelable(this.f1, flags);
        dest.writeParcelable(this.f2, flags);
        dest.writeParcelable(this.f3, flags);
        dest.writeParcelable(this.f4, flags);
        dest.writeParcelable(this.f5, flags);
        dest.writeParcelable(this.f6, flags);
        dest.writeParcelable(this.f7, flags);
        dest.writeParcelable(this.now, flags);
        dest.writeInt(this.ret_code);
        dest.writeString(this.time);
        dest.writeTypedList(this.hourDataList);
    }

    public WeatherVo() {
    }

    protected WeatherVo(Parcel in) {
        this.cityInfo = in.readParcelable(CityInfoBean.class.getClassLoader());
        this.f1 = in.readParcelable(WeatherBean.class.getClassLoader());
        this.f2 = in.readParcelable(WeatherBean.class.getClassLoader());
        this.f3 = in.readParcelable(WeatherBean.class.getClassLoader());
        this.f4 = in.readParcelable(WeatherBean.class.getClassLoader());
        this.f5 = in.readParcelable(WeatherBean.class.getClassLoader());
        this.f6 = in.readParcelable(WeatherBean.class.getClassLoader());
        this.f7 = in.readParcelable(WeatherBean.class.getClassLoader());
        this.now = in.readParcelable(NowBean.class.getClassLoader());
        this.ret_code = in.readInt();
        this.time = in.readString();
        this.hourDataList = in.createTypedArrayList(HourDataListBean.CREATOR);
    }

    public static final Parcelable.Creator<WeatherVo> CREATOR = new Parcelable.Creator<WeatherVo>() {
        @Override
        public WeatherVo createFromParcel(Parcel source) {
            return new WeatherVo(source);
        }

        @Override
        public WeatherVo[] newArray(int size) {
            return new WeatherVo[size];
        }
    };
}




