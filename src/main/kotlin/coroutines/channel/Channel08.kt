package coroutines.channel.Channel08.kt

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Channel08
 *
 * @author zhuzhenjie
 * @since 2023/7/10
 */
fun main() = runBlocking {
    val table = Channel<Ball>()

    launch {
        player("ping", table)
    }

    launch {
        player("pong", table)
    }

    table.send(Ball(0))

    delay(1000)
    coroutineContext.cancelChildren()
}


data class Ball(
    var hits: Int
)

suspend fun player(name: String, table: Channel<Ball>) {
    for (ball in table) {
        ball.hits++
        println("$name $ball")
        delay(300)
        table.send(ball)
    }
}