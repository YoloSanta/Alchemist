package ltd.matrixstudios.alchemist.vault

import ltd.matrixstudios.alchemist.AlchemistSpigotPlugin
import ltd.matrixstudios.alchemist.vault.permission.VaultPermissionExtension

object VaultHookManager {
    private var using = false

    fun loadVault() {
        if (AlchemistSpigotPlugin.instance.server.pluginManager.getPlugin("Vault") != null) {
            using = true

            val prov = VaultPermissionExtension()
            prov.init()
            ltd.matrixstudios.alchemist.util.Chat.sendConsoleMessage("&6[Vault] &fPermission Hook: &aTrue")
            ltd.matrixstudios.alchemist.util.Chat.sendConsoleMessage("&6[Vault] &fChat Hook: &aTrue")
        }

    }
}