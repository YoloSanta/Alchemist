package ltd.matrixstudios.alchemist.essentials.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import co.aikar.commands.bukkit.contexts.OnlinePlayer
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.staff.alerts.StaffActionAlertPacket
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.Location
import org.bukkit.entity.Player

class TeleportationCommands : BaseCommand() {

    @CommandAlias("teleport|tp")
    @CommandPermission("alchemist.essentials.teleport.other")
    fun teleport(player: Player, @Name("target") target: OnlinePlayer) {
        player.teleport(target.player.location)
        player.sendMessage(Chat.format("&6You have been teleported to " + target.player.displayName))
        AsynchronousRedisSender.send(
            StaffActionAlertPacket(
                "has teleported to ${target.player.name}",
                player.name,
                Alchemist.globalServer.id
            )
        )
    }

    @CommandAlias("tphere|s")
    @CommandPermission("alchemist.essentials.teleport.here")
    fun teleportHere(player: Player, @Name("target") target: OnlinePlayer) {
        target.player.teleport(player.location)
        player.sendMessage(Chat.format("&6You have teleported " + target.player.displayName + " &6to yourself"))
        target.player.sendMessage(Chat.format("&6You have been teleported to " + player.displayName))
        AsynchronousRedisSender.send(
            StaffActionAlertPacket(
                "has teleported ${target.player.name} to themselves",
                player.name,
                Alchemist.globalServer.id
            )
        )
    }

    @CommandAlias("tppos")
    @CommandPermission("alchemist.essentials.teleport.position")
    fun teleportPos(player: Player, @Name("x") x: Int, @Name("y") y: Int, @Name("z") z: Int) {
        player.teleport(Location(player.location.world, x.toDouble(), y.toDouble(), z.toDouble()))
        player.sendMessage(Chat.format("&6You have teleported yourself to the location &f$x, $y, $z"))
        AsynchronousRedisSender.send(
            StaffActionAlertPacket(
                "has teleported to &f$x, $y, $z",
                player.name,
                Alchemist.globalServer.id
            )
        )
    }
}