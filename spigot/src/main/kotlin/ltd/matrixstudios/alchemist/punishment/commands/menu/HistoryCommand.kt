package ltd.matrixstudios.alchemist.punishment.commands.menu

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.alchemist.profiles.AsyncGameProfile
import ltd.matrixstudios.alchemist.util.Chat
import org.bukkit.entity.Player
import java.util.concurrent.CompletableFuture

class HistoryCommand : BaseCommand() {

    @CommandAlias("c|history|checkpunishments")
    @CommandPermission("alchemist.punishments.check")
    @CommandCompletion("@gameprofile")
    fun ban(sender: Player, @Name("target") gameProfile: AsyncGameProfile): CompletableFuture<Void> {
        return gameProfile.use(sender) {
            HistoryMenu(it, sender).openMenu()
            sender.sendMessage(Chat.format("&eViewing punishment history of " + it.getRankDisplay() + "&e..."))
        }
    }
}