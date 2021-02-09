package com.example.lostandfound.presentation.view.ui.binding

import org.json.JSONObject

class CityParser(json: String) : JSONObject(json) {

    fun parse(): MutableList<String> {
        val city = this.getJSONArray("")
        val list: MutableList<String> = ArrayList()
        for (i in 0..city.length()) {
            list.add(city.getJSONObject(i).getString("city"))
        }

        return list
    }

}