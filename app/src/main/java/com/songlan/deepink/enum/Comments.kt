package com.songlan.deepink.enum

// 书源的评分类，数据固定，就从这里进行匹配结果
object Comments {
    const val ONE = 1
    const val TWO = 2
    const val THREE = 3
    const val FOUR = 4
    const val FIVE = 5
    const val SIX = 6
    const val SEVEN = 7
    const val EIGHT = 8
    const val NINE = 9
    const val TEN = 10

    const val EMPTY_STAR = 0
    const val HALF_STAR = 1
    const val SOLID_STAR = 2
    // 获取评分的星星展示
    fun commentsDes(score: Int): MutableList<Int> {
        val starList = mutableListOf<Int>()
        for (i in 0..score / 2)
            starList.add(SOLID_STAR)
        if (score % 2 == 1) {
            starList.add(HALF_STAR)
        }
        for (i in TEN - score..TEN) {
            starList.add(EMPTY_STAR)
        }
        return starList
    }

}