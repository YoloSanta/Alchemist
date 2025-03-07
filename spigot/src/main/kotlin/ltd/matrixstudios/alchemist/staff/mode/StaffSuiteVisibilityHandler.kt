package ltd.matrixstudios.alchemist.staff.mode

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object StaffSuiteVisibilityHandler {

    fun onDisableVisbility(player: Player) {
        Bukkit.getOnlinePlayers().forEach {
            it.showPlayer(player)
        }
    }

    fun onEnableVisibility(player: Player) {
        Bukkit.getOnlinePlayers().filter { !it.hasPermission("alchemist.staff") }.forEach { it.hidePlayer(player) }

        val profile = AlchemistAPI.syncFindProfile(player.uniqueId)?.hasMetadata("seeOtherStaff") ?: return

        if (profile) {
            Bukkit.getOnlinePlayers().filter {
                it.hasPermission("alchemist.staff")
            }.forEach {
                player.showPlayer(it)
            }
        }
    }
}