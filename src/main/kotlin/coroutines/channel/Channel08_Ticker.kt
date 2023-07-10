package coroutines.channel

import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

/**
 * Channel08_Ticker
 *
 * @author zhuzhenjie
 * @since 2023/7/10
 */
fun main() = runBlocking<Unit> {

    // 创建定时器通道
    val tickerChannel = ticker(
        delayMillis = 100,
        initialDelayMillis = 0
    )

    var nextElement = withTimeoutOrNull(1) {
        tickerChannel.receive()
    }

    // 没有初始延迟
    println("Initial element is available immediately: $nextElement")


    // 之后产生的所有数据的延迟时间都是 100ms
    nextElement = withTimeoutOrNull(50) {
        tickerChannel.receive()
    }
    println("Next element is not ready in 50 ms: $nextElement")

    nextElement = withTimeoutOrNull(60) {
        tickerChannel.receive()
    }
    println("Next element is ready in 100 ms: $nextElement")


    // 模拟消费者端的长时间延迟
    println("Consumer pauses for 150ms")
    delay(150)

    // 下一个元素已经产生了
    nextElement = withTimeoutOrNull(1) {
        tickerChannel.receive()
    }
    println("Next element is available immediately after large consumer delay: $nextElement")


    // 注意, `receive` 调用之间的暂停也会被计算在内,因此下一个元素产生得更快
    nextElement = withTimeoutOrNull(60) {
        tickerChannel.receive()
    }
    println("Next element is ready in 50ms after consumer pause in 150ms: $nextElement")


    tickerChannel.cancel() // 告诉通道, 不需要再产生更多元素了
}
/*

Initial element is available immediately: kotlin.Unit
Next element is not ready in 50 ms: null
Next element is ready in 100 ms: kotlin.Unit
Consumer pauses for 150ms
Next element is available immediately after large consumer delay: kotlin.Unit
Next element is ready in 50ms after consumer pause in 150ms: kotlin.Unit
Disconnected from the target VM, address: '127.0.0.1:49572', transport: 'socket'

Process finished with exit code 0

 */

/*
注意, ticker 会感知到消费端的暂停, 默认的, 如果消费端发生了暂停, 它会调整下一个元素产生的延迟时间, 尽量保证产生元素时维持一个固定的间隔速度.

另外一种做法是, 将 mode 参数设置为 TickerMode.FIXED_DELAY, 可以指定产生元素时维持一个固定的间隔速度.
 */