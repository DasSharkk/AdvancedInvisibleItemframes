package me.dasshark.advancedInvisibleItemframes.listeners

import me.dasshark.advancedInvisibleItemframes.Main
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.ItemFrame
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.hanging.HangingPlaceEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType


class IFPlaceListener: Listener {

    @EventHandler
    fun onHangingPlace(e: HangingPlaceEvent) {
        if (e.entity.type != EntityType.ITEM_FRAME) return
        val frame: ItemStack
        val p = e.player ?: return

        if (p.inventory.itemInMainHand.type === Material.ITEM_FRAME)
            frame = p.inventory.itemInMainHand
        else if (p.inventory.itemInOffHand.type === Material.ITEM_FRAME)
            frame = p.inventory.itemInOffHand
        else
            return

        if (frame.itemMeta!!.persistentDataContainer.has(Main.invisibleKey, PersistentDataType.BYTE)) {
            val itemFrame: ItemFrame = e.entity as ItemFrame
            itemFrame.isVisible = false
            e.entity.persistentDataContainer.set(Main.invisibleKey, PersistentDataType.BYTE, 1)
        }
    }

}