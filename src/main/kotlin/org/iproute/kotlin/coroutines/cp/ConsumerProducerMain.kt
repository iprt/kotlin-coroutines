package org.iproute.kotlin.coroutines.cp.ConsumerProducerMain.kt

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

/**
 * ConsumerProducerMain
 *
 * @author zhuzhenjie
 * @since 2023/7/4
 */

val channel: Channel<String> = Channel(capacity = 5)

suspend fun produce(data: String) {
    delay(2000) // mock produce
    println("produce $data")
    channel.send(data)
}

/**
 * 每次消费最好包装在一个 launch 中
 *
 */
suspend fun consume() {
    delay(2000)// mock consume
    // 阻塞的
    val receive = channel.receive()
    println(receive)
}

fun main() = runBlocking {

    // 10次生产
    repeat(10) {
        launch {
            produce(UUID.randomUUID().toString())
        }
    }

    // 10次消费
    repeat(10) {
        launch {
            consume()
        }
    }

    println("Done!")
}