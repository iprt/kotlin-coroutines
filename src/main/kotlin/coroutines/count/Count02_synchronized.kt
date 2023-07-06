package org.iproute.kotlin.coroutines.count.Count02_synchronized

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Conunt02_synchronized
 *
 * @author zhuzhenjie
 * @since 2023/7/4
 */
fun main() = runBlocking {
    var count = 0
    val lock = "lock"

    val job1 = CoroutineScope(Dispatchers.IO).launch {
        repeat(9999) {
            synchronized(lock) {
                count++
            }
        }
    }

    val job2 = CoroutineScope(Dispatchers.IO).launch {
        repeat(9999) {
            synchronized(lock) {
                count--
            }
        }
    }


    job1.join()
    job2.join()

    println("count: $count")
}