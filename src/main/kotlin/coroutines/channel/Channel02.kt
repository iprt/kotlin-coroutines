package coroutines.channel.Channel02

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Channel02
 *
 * @author zhuzhenjie
 * @since 2023/7/6
 */
fun main() = runBlocking {

    // 把channel看成队列是最好的
    val channel: Channel<Int> = Channel()

    launch {
        for (i in 1..5) {
            delay(100)
            channel.send(i * i)
        }

        // 主动关闭
        channel.close()

    }

    for (y in channel) {
        println(y)
    }

    println("Done")
}