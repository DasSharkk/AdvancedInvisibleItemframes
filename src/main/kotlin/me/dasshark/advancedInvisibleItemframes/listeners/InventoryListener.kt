package me.dasshark.advancedInvisibleItemframes.listeners

import me.dasshark.advancedInvisibleItemframes.Main
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent

class InventoryListener: Listener {

    @EventHandler
    fun onInventoryClose(e: InventoryCloseEvent) {
        if (e.view.title != "§7Change Recipe") return

        val ingredients = listOf(
            "0:${e.inventory.getItem(3)?.type?.name}",
            "1:${e.inventory.getItem(4)?.type?.name}",
            "2:${e.inventory.getItem(5)?.type?.name}",
            "3:${e.inventory.getItem(12)?.type?.name}",
            "4:${e.inventory.getItem(13)?.type?.name}",
            "5:${e.inventory.getItem(14)?.type?.name}",
            "6:${e.inventory.getItem(21)?.type?.name}",
            "7:${e.inventory.getItem(22)?.type?.name}",
            "8:${e.inventory.getItem(23)?.type?.name}"
            )

        Main.instance.config.set("crafting.shape", listOf("012", "345", "678"))
        Main.instance.config.set("crafting.ingredients", ingredients)
        Main.instance.config.save("plugins/AdvancedInvisibleItemframes/config.yml")

        e.player.sendMessage(Main.prefix + "§aRecipe changed!")
        e.player.sendMessage(Main.prefix + "§aPlease reload the plugin to apply the changes.")
    }

    @EventHandler
    fun onInventoryInteract(e: InventoryClickEvent) {
        if (e.view.title != "§7Change Recipe") return
        if (e.currentItem == null) return
        if (e.currentItem!!.type == Material.BEDROCK && e.currentItem!!.itemMeta!!.localizedName == "cancel") e.isCancelled = true
    }

    @EventHandler
    fun onItemMove(e: InventoryMoveItemEvent) {
        if (e.item.type == Material.BEDROCK && e.item.itemMeta!!.localizedName == "cancel") e.isCancelled = true
    }

}