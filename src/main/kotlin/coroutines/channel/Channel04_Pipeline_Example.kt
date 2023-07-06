package coroutines.channel.Channel04_Pipeline_Example.kt

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

/**
 * 协程求质数
 *
 * @author zhuzhenjie
 * @since 2023/7/6
 */
fun main() = runBlocking {
    var cur = numbersFrom(2);

    repeat(10) {
        val prime = cur.receive()
        println(prime)

        // 这一步怎么理解 ？？？
        cur = filter(cur, prime)
    }

    // 取消所有的子协程, 让 main 函数结束
    coroutineContext.cancelChildren()
}

/**
 * 这个协程 产生无限的整数序列.
 *
 * @param start
 * @return
 */
fun CoroutineScope.numbersFrom(start: Int): ReceiveChannel<Int> = produce {
    var x = start
    while (true) {
        send(x++)
    }
}

/**
 * Filter 下一部分会对输入的整数流进行过滤, 删除可以被某个质数整除的数字:
 *
 * @param numbers
 * @param prime
 * @return
 */
fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int): ReceiveChannel<Int> = produce {
    for (number in numbers) {
        if (number % prime != 0) {
            send(number)
        }
    }
}

