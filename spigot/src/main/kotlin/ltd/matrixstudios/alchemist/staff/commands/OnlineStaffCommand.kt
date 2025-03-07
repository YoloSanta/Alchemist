package ltd.matrixstudios.alchemist.staff.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.service.server.UniqueServerService
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player
import java.util.*

/**
 * Class created on 5/14/2023

 * @author 98ping, AB987
 * @project Alchemist
 * @website https://solo.to/redis
 */
class OnlineStaffCommand : BaseCommand() {

    @CommandAlias("onlinestaff|globalstaff|stafflist")
    @CommandPermission("alchemist.staff.list")
    fun onlineStaff(player: Player) {
        val allPlayers = mutableListOf<UUID>()
        val servers = UniqueServerService.getValues()
        val msgs = mutableListOf<String>()

        for (server in servers) {
            for (player1 in server.players) {
                if (!allPlayers.contains(player1)) {
                    allPlayers.add(player1)
                }
            }
        }

        for (player2 in allPlayers) {
            val profile = AlchemistAPI.syncFindProfile(player2) ?: continue
            val serverName = UniqueServerService.byId(profile.metadata.get("server").asString.lowercase())?.displayName
                ?: "&cUnknown"

            if (profile.getCurrentRank().staff) {
                msgs.add(Chat.format("&7- " + AlchemistAPI.getRankDisplay(profile.uuid) + " &eis currently &aonline &eat &f" + serverName))
            }

        }
        player.sendMessage(Chat.format("&e&lOnline Staff Members&7:"))
        for (msg in msgs) {
            player.sendMessage(Chat.format(msg))
        }
    }

}