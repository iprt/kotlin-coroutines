package coroutines.channel.Channel04_Pipeline.kt

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

/**
 * Channel04_Pipeline
 *
 * 管道也是一种设计模式, 比如某个协程可能会产生出无限多个值
 *
 * @author zhuzhenjie
 * @since 2023/7/6
 */
fun main() = runBlocking {
    // 主代码会启动这些协程, 并将整个管道连接在一起:
    val numbers = produceNumbers() // 从 1 开始产生无限的整数
    val squares = square(numbers) // 对整数进行平方


    repeat(5) {
        println(squares.receive())
    }

    println("Done")

    // 取消所有子协程
    coroutineContext.cancelChildren()
}


fun CoroutineScope.produceNumbers() = produce<Int> {
    var x = 1
    while (true) {
        // 从 1 开始递增的无限整数流}
        send(x++)
    }
}

/**
 * Square
 *
 * @param numbers 从一个channel里面获取数据
 * @return 返回一个新的Channel
 */
fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
    for (x in numbers) {
        send(x * x)
    }
}