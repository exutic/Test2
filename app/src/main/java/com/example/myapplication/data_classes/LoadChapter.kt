package com.example.myapplication.data_classes

import android.content.Context
import org.json.JSONObject

fun loadStoryFromJson(context: Context, fileName: String): List<ChapterModel> {
    val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    val jsonObject = JSONObject(jsonString)
    val jsonArray = jsonObject.getJSONArray("chapters")
    val chapterModels = mutableListOf<ChapterModel>()

    for (i in 0 until jsonArray.length()) {
        val chapterObject = jsonArray.getJSONObject(i)
        val chapterModel = ChapterModel(
            chapterNumber = chapterObject.getInt("chapterNumber"),
            chapterTitle = chapterObject.getString("chapterTitle"),
            chapterText = chapterObject.getString("chapterText"),
            task = chapterObject.getString("task"),
            choice1 = chapterObject.getString("choice1"),
            choice2 = chapterObject.getString("choice2"),
            choice3 = chapterObject.getString("choice3")
        )
        chapterModels.add(chapterModel)
    }

    return chapterModels
}

fun saveGameDataToJson(context: Context, playerName: String, playerHealth: Int, playerCoins: Int) {
    val jsonObject = JSONObject().apply {
        put("playerName", playerName)
        put("playerHealth", playerHealth)
        put("playerCoins", playerCoins)
    }

    context.openFileOutput("game_data.json", Context.MODE_PRIVATE).use {
        it.write(jsonObject.toString().toByteArray())
    }
}
