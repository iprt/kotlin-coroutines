package org.iproute.kotlin.coroutines.count.Count01.kt

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Count01
 *
 * @author zhuzhenjie
 * @since 2023/7/4
 */
fun main() = runBlocking {

    var count = 0

    val job1 = CoroutineScope(Dispatchers.IO).launch {

        repeat(9999) {
            count++
        }
    }

    val job2 = CoroutineScope(Dispatchers.IO).launch {
        repeat(9999) {
            count--
        }
    }

    job1.join()
    job2.join()

    println("count: $count")
}

