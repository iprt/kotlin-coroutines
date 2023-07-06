package coroutines.channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

/**
 * Channel03_Producer
 *
 * @author zhuzhenjie
 * @since 2023/7/6
 */
fun main() = runBlocking {

    val squares = produceSquares()
    squares.consumeEach {
        println(it)
    }

    println("Done")

}


fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
    for (x in 1..5) {
        send(x * x)
    }
}