package ltd.matrixstudios.alchemist.servers.packets

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.redis.RedisPacket
import org.bukkit.Bukkit

class ServerWhitelistPacket(val target: String) : RedisPacket("whitelist-packet") {

    override fun action() {
        val server = Alchemist.globalServer

        if (server.id.equals(target, ignoreCase = true)) {
            Bukkit.setWhitelist(!Bukkit.hasWhitelist())
        }
    }
}