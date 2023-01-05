package nl.vroste.zio.amqp.model
import zio.{ durationInt, Duration }

import java.net.{ URI, URLEncoder }

case class AMQPConfig(
  user: String,
  password: String,
  vhost: String,
  heartbeatInterval: Duration,
  ssl: Boolean,
  host: String,
  port: Short,
  connectionTimeout: Duration
) {
  val encodedVhost   = URLEncoder.encode(vhost, "UTF-8")
  def toUri: AmqpUri =
    AmqpUri(
      new URI(
        s"amqp${if (ssl) "s" else ""}://$user:$password@$host:$port/$encodedVhost?heartbeat=${heartbeatInterval.getSeconds}&connection_timeout=${connectionTimeout.toMillis}"
      )
    )
}
object AMQPConfig {
  lazy val default = AMQPConfig(
    user = "guest",
    password = "guest",
    vhost = "/",
    heartbeatInterval = 10.seconds,
    ssl = false,
    host = "localhost",
    port = 5672,
    connectionTimeout = 10.seconds
  )

}
