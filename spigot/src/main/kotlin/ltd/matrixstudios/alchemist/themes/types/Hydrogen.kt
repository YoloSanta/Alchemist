package ltd.matrixstudios.alchemist.themes.types

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.models.grant.types.Punishment
import ltd.matrixstudios.alchemist.models.grant.types.RankGrant
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.models.ranks.Rank
import ltd.matrixstudios.alchemist.punishments.PunishmentType
import ltd.matrixstudios.alchemist.themes.Theme
import ltd.matrixstudios.alchemist.util.Chat
import ltd.matrixstudios.alchemist.util.TimeUtil
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.*

class Hydrogen : Theme(
    "hydrogen", "&bHydrogen", mutableListOf(
        " ",
        "&7Hydrogen was the main RestAPI-based rank core",
        "&7that was found on most &bFrozenOrb &7operated",
        "&7servers. &dVelt&7, &5Arcane&7, and &6Viper &7used this core",
        "&7for a long time and held many players while doing so.",
        "&7Hydrogen has a simplistic and minimal look which makes it",
        "&7great for all staff members",
        " ",
        "&eClick to select the &bHydrogen &etheme.",
        " "
    ), Material.WATER_BUCKET, 0
) {

    override fun getGrantsLore(player: Player, rankGrant: RankGrant): MutableList<String> {
        val desc = mutableListOf<String>()
        desc.add(Chat.format("&7&m-------------------------"))
        desc.add(Chat.format("&eBy: &f" + AlchemistAPI.getRankDisplay(rankGrant.executor)))
        desc.add(Chat.format("&eReason: &c" + rankGrant.reason))
        desc.add(
            Chat.format(
                "&eActor: &c" + Chat.enumToDisplay(rankGrant.internalActor.actorType.name) + " &e: &c" + Chat.enumToDisplay(
                    rankGrant.internalActor.executor.name
                )
            )
        )
        desc.add(Chat.format("&eRank: &f" + rankGrant.getGrantable().color + rankGrant.getGrantable().displayName))
        desc.add(Chat.format("&7&m-------------------------"))
        val expirable = rankGrant.expirable

        if (expirable.duration == Long.MAX_VALUE) {
            desc.add(Chat.format("&eThis is a permanent grant!"))
        } else {
            desc.add(Chat.format("&eDuration: " + TimeUtil.formatDuration(rankGrant.expirable.duration)))
        }
        desc.add(" ")

        desc.add(Chat.format("&c&lClick to remove grant"))
        desc.add(Chat.format("&7&m-------------------------"))

        return desc
    }

    override fun getGrantsDisplayName(player: Player, rankGrant: RankGrant): String {
        return Chat.format("&e" + Date(rankGrant.expirable.addedAt))
    }

    override fun getGrantsData(player: Player, rankGrant: RankGrant): Short {
        return if (rankGrant.expirable.isActive()) DyeColor.LIME.woolData.toShort() else DyeColor.RED.woolData.toShort()
    }

    override fun getGrantLore(player: Player, gameProfile: GameProfile, rank: Rank): MutableList<String> {
        val desc = mutableListOf<String>()
        desc.add(Chat.format("&7&m--------------------------------"))
        desc.add(
            Chat.format(
                "&9Click to grant &f" + rank.color + rank.displayName + " &9to " + AlchemistAPI.getRankDisplay(
                    gameProfile.uuid
                )
            )
        )
        desc.add(Chat.format("&7&m--------------------------------"))

        return desc
    }

    override fun getGrantDisplayName(player: Player, rank: Rank): String {
        return Chat.format(rank.color + rank.displayName)
    }

    override fun getGrantData(player: Player, rank: Rank): Short {
        if (rank.woolColor != null) {
            return AlchemistAPI.getWoolColor(rank.woolColor!!).woolData.toShort()
        }

        return AlchemistAPI.getWoolColor(rank.color).woolData.toShort()
    }

    override fun getHistoryLore(player: Player, punishment: Punishment): MutableList<String> {
        val desc = mutableListOf<String>()
        desc.add(Chat.format("&7&m-------------------------"))
        desc.add(Chat.format("&eBy: &f" + AlchemistAPI.getRankDisplay(punishment.executor)))
        desc.add(Chat.format("&eReason: &c" + punishment.reason))
        desc.add(
            Chat.format(
                "&eActor: &c" + Chat.enumToDisplay(punishment.actor.actorType.name) + " &e: &c" + Chat.enumToDisplay(
                    punishment.actor.executor.name
                )
            )
        )
        desc.add(Chat.format("&7&m-------------------------"))
        val expirable = punishment.expirable

        if (expirable.duration == Long.MAX_VALUE) {
            desc.add(Chat.format("&eThis is a permanent punishment!"))
        } else {
            desc.add(Chat.format("&eDuration: " + TimeUtil.formatDuration(punishment.expirable.duration)))
        }
        desc.add(" ")

        desc.add(Chat.format("&c&lClick to remove punishment"))
        desc.add(Chat.format("&7&m-------------------------"))

        return desc
    }

    override fun getHistoryDisplayName(player: Player, punishment: Punishment): String {
        return Chat.format("&e" + Date(punishment.expirable.addedAt))
    }

    override fun getHistoryData(player: Player, punishment: Punishment): Short {
        return if (punishment.expirable.isActive()) DyeColor.LIME.woolData.toShort() else DyeColor.RED.woolData.toShort()
    }

    override fun getHistoryPlaceholderLore(
        player: Player,
        profile: GameProfile,
        punishment: PunishmentType
    ): MutableList<String> {
        return mutableListOf()
    }

    override fun getHistoryPlaceholderName(player: Player, profile: GameProfile, punishment: PunishmentType): String {
        return Chat.format(punishment.color + punishment.niceName + "'s")
    }

    override fun getHistoryPlaceholderData(player: Player, profile: GameProfile, punishment: PunishmentType): Short {
        return Chat.getDyeColor(punishment.color).woolData.toShort()
    }
}