package coroutines.channel.Channel01

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Channel01
 *
 * @author zhuzhenjie
 * @since 2023/7/6
 */
fun main() = runBlocking {

    val channel: Channel<Int> = Channel()


    launch {
        for (i in 1..5) {
            channel.send(i * i)
        }
    }

    repeat(5) {
        val receive = channel.receive()
        println("receive $receive")
    }

}