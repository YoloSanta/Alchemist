package ltd.matrixstudios.alchemist.profiles.permissions

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.models.profile.GameProfile
import ltd.matrixstudios.alchemist.profiles.getProfile
import ltd.matrixstudios.alchemist.util.Chat.format
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.permissions.PermissionAttachment
import java.lang.reflect.Field
import java.util.*


object AccessiblePermissionHandler {

    private val permissionAttachmentMap: MutableMap<UUID, PermissionAttachment> = HashMap()

    lateinit var permissionField: Field

    var pendingLoadPermissions = hashMapOf<UUID, Map<String?, Boolean?>>()

    fun load() {
        try {
            permissionField = PermissionAttachment::class.java.getDeclaredField("permissions")
            permissionField.isAccessible = true
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }

    }

    fun setupPlayer(uuid: UUID, perms: Map<String?, Boolean?>) {
        pendingLoadPermissions[uuid] = perms
    }

    fun remove(player: Player) {
        permissionAttachmentMap.remove(player.uniqueId)
    }

    fun findRankWeight(player: Player): Int {
        return if (player.hasMetadata("AlchemistRankWeight")) {
            player.getMetadata("AlchemistRankWeight").first().asInt()
        } else 0
    }

    fun update(player: Player, perms: Map<String, Boolean>) {
        permissionAttachmentMap.putIfAbsent(player.uniqueId, player.addAttachment(AlchemistSpigotPlugin.instance))
        try {
            val attachment = permissionAttachmentMap[player.uniqueId]
            permissionField.set(attachment, perms)
            player.recalculatePermissions()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        val profile: GameProfile = player.getProfile() ?: return

        Bukkit.getScheduler().runTask(AlchemistSpigotPlugin.instance) {
            //apply display name
            player.displayName = format(profile.getCurrentRank().color + player.name)

            //set metadata values
            player.removeMetadata("AlchemistRankWeight", AlchemistSpigotPlugin.instance)
            player.setMetadata(
                "AlchemistRankWeight",
                FixedMetadataValue(AlchemistSpigotPlugin.instance, (profile.getCurrentRank().weight))
            )
        }
    }
}
