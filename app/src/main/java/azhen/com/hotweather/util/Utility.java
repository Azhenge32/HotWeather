package azhen.com.hotweather.util;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import azhen.com.hotweather.db.City;
import azhen.com.hotweather.db.County;
import azhen.com.hotweather.db.Province;
import azhen.com.hotweather.gson.Weather;

public class Utility {
    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 解析和处理省级数据
     * @param response
     * @return
     */
    public static boolean handleProvinceResponse(String response) {
        if (TextUtils.isEmpty(response)) {
            return false;
        }

        try {
            JSONArray allProvinces = new JSONArray(response);
            for (int i = 0; i < allProvinces.length(); i ++) {
                JSONObject provinceObject = allProvinces.getJSONObject(i);
                Province province = new Province();
                province.setProvinceName(provinceObject.getString("name"));
                province.setProvinceCode(provinceObject.getInt("id"));
                province.save();
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 解析和处理市级数据
     * @param response
     * @return
     */
    public static boolean handleCityResponse(String response, int provinceId) {
        if (TextUtils.isEmpty(response)) {
            return false;
        }

        try {
            JSONArray allProvinces = new JSONArray(response);
            for (int i = 0; i < allProvinces.length(); i ++) {
                JSONObject provinceObject = allProvinces.getJSONObject(i);
                City city = new City();
                city.setCityName(provinceObject.getString("name"));
                city.setCityCode(provinceObject.getInt("id"));
                city.setProvinceId(provinceId);
                city.save();
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 解析和处理县级数据
     * @param response
     * @return
     */
    public static boolean handleCountyResponse(String response, int cityId) {
        if (TextUtils.isEmpty(response)) {
            return false;
        }

        try {
            JSONArray allProvinces = new JSONArray(response);
            for (int i = 0; i < allProvinces.length(); i ++) {
                JSONObject provinceObject = allProvinces.getJSONObject(i);
                County county = new County();
                county.setCountyName(provinceObject.getString("name"));
                county.setWeatherId(provinceObject.getString("weather_id"));
                county.setCityId(cityId);
                county.save();
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
