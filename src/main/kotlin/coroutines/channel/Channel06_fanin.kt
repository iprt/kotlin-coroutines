package coroutines.channel.Channel06_fanin.kt

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Channel05_fanin
 *
 * @author zhuzhenjie
 * @since 2023/7/10
 */
fun main() = runBlocking {

    val channel = Channel<String>()

    launch {
        sendString(channel, "foo", 200L)
    }

    launch {
        sendString(channel, "BAR!", 500L)
    }

    repeat(6) {
        println(channel.receive())
    }

    coroutineContext.cancelChildren()
}


/**
 * 多个协程也可以向同一个通道发送数据. 比如, 我们有一个字符串的通道, 还有一个挂起函数, 不断向通道发送特定的字符串, 然后暂停一段时间:
 *
 * @param channel
 * @param s
 * @param time
 */
suspend fun sendString(channel: SendChannel<String>, s: String, time: Long) {
    while (true) {
        delay(time)
        channel.send(s)
    }
}