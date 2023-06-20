package com.example.myapplication

import android.util.Log

fun main() {
    var i = 0
    while (i < 10) {

        if (i == 4) {
            if (i==4){
                println("I is [$i]")
            }
            i++
            continue // continue != break
        }
        println(i)
        i++
    }
}