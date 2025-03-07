package ltd.matrixstudios.alchemist.profiles.connection.postlog.tasks

import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.packets.StaffGeneralMessagePacket
import ltd.matrixstudios.alchemist.profiles.connection.postlog.BukkitPostLoginTask
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import org.bukkit.entity.Player

/**
 * Class created on 7/20/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
object CheckBanEvasion : BukkitPostLoginTask {
    override fun run(player: Player) {
        val profileId = player.uniqueId
        val profile = AlchemistAPI.syncFindProfile(profileId) ?: return
        profile.getAltAccounts().thenAcceptAsync { alts ->
            val isBanEvading = alts.size >= 1 && alts.any {
                it.hasActivePunishment(PunishmentType.BAN) || it.hasActivePunishment(
                    PunishmentType.BLACKLIST
                )
            }

            if (isBanEvading) {
                AsynchronousRedisSender.send(
                    StaffGeneralMessagePacket(
                        "&b[S] &3[${Alchemist.globalServer.displayName}] ${
                            AlchemistAPI.getRankWithPrefix(
                                profileId
                            )
                        } &3may be using an alt to evade a punishment!"
                    )
                )
            }
        }
    }
}