package coroutines.channel.Channel05_fanout.kt

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Channel05_fanout
 *
 * 多个协程可能会从同一个通道接收数据
 *
 * 并将计算工作分配给这多个协程
 *
 * @author zhuzhenjie
 * @since 2023/7/10
 */
fun main() = runBlocking {
    val producer = produceNumbers()

    repeat(5) {
        launchProcessor(it, producer)
    }

    delay(950)

    producer.cancel()
}

/**
 * 定时产生整数
 *
 */
fun CoroutineScope.produceNumbers(): ReceiveChannel<Int> = produce<Int> {
    var x = 1
    while (true) {
        // 产生下一个整数
        send(x++)
        // 等待0.1秒
        delay(100)
    }
}


fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
    for (msg in channel) {
        println("Processor #$id received $msg")
    }
}


