package ltd.matrixstudios.alchemist.aikar.context

import co.aikar.commands.BukkitCommandExecutionContext
import co.aikar.commands.InvalidCommandArgument
import co.aikar.commands.contexts.ContextResolver
import ltd.matrixstudios.alchemist.punishments.PunishmentType

class PunishmentTypeResolver : ContextResolver<PunishmentType, BukkitCommandExecutionContext> {

    override fun getContext(c: BukkitCommandExecutionContext?): PunishmentType? {
        val firstArg = c!!.popFirstArg() ?: return null

        var type: PunishmentType? = null

        try {
            type = PunishmentType.valueOf(firstArg.uppercase())
        } catch (e: java.lang.IllegalArgumentException) {
            throw InvalidCommandArgument("No punishment type by this name found")
        }

        if (type == null) {
            throw InvalidCommandArgument("No punishment type by this name found")
        }

        return type
    }
}