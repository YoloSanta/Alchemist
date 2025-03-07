package ltd.matrixstudios.alchemist.grants.apply

/**
 * Class created on 6/13/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.models.grant.types.scope.GrantScope
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.packets.GrantMessageTargetPacket
import ltd.matrixstudios.alchemist.packets.StaffAuditPacket
import ltd.matrixstudios.alchemist.profiles.BukkitProfileAdaptation
import ltd.matrixstudios.alchemist.punishment.BukkitPunishmentFunctions
import ltd.matrixstudios.alchemist.punishments.actor.ActorType
import ltd.matrixstudios.alchemist.punishments.actor.DefaultActor
import ltd.matrixstudios.alchemist.punishments.actor.executor.Executor
import ltd.matrixstudios.alchemist.redis.AsynchronousRedisSender
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import ltd.matrixstudios.alchemist.webhook.types.grants.GrantsNotification
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class NonModelGrantCommand : BaseCommand() {

    @CommandAlias("nmgrant")
    @CommandPermission("alchemist.grants.admin")
    fun nmgrant(
        sender: CommandSender,
        @Name("uuid") uuid: UUID,
        @Name("rank") rank: Rank,
        @Name("duration") duration: String,
        @Name("scope") scope: GrantScope,
        @Name("reason") reason: String
    ) {
        val rankGrant = RankGrant(
            rank.id,
            uuid,
            BukkitPunishmentFunctions.getSenderUUID(sender),
            reason, (if (duration == "perm") Long.MAX_VALUE else TimeUtil.parseTime(duration) * 1000L),

            DefaultActor(
                if (sender !is Player) Executor.CONSOLE else Executor.PLAYER,
                ActorType.GAME
            ),
            scope
        )

        BukkitProfileAdaptation.initializeGrant(rankGrant, uuid)
        GrantsNotification(rankGrant).send()
        AsynchronousRedisSender.send(
            GrantMessageTargetPacket(
                uuid,
                rank,
                (if (duration == "perm") Long.MAX_VALUE else TimeUtil.parseTime(duration) * 1000L)
            )
        )

        AsynchronousRedisSender.send(StaffAuditPacket("&b[Audit] &f" + uuid.toString() + " &3was granted " + rank.color + rank.displayName + " &3for &b" + reason))
        sender.sendMessage(Chat.format("&aGranted " + uuid.toString() + " the rank " + rank.color + rank.displayName))
    }
}