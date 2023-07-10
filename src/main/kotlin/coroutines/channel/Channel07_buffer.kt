package coroutines.channel.Channel07_buffer.kt

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Channel07_buffer
 *
 * @author zhuzhenjie
 * @since 2023/7/10
 */

fun main() = runBlocking {

    val channel = Channel<Int>(4)

    val sender = launch {
        repeat(10) {
            println("Sending $it")
            channel.send(it)
        }
    }

    // 不接收任何数据，只是等待
    delay(1000)
    sender.cancel()
}

/*
前 4 个数据会被添加到缓冲区中, 然后在试图发送第 5 个数据时, 发送者协程会挂起.
 */