package me.dasshark.advancedInvisibleItemframes.utils

import me.dasshark.advancedInvisibleItemframes.Main
import org.bukkit.*
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.*
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*


class ItemBuilder {
    val itemStack: ItemStack

    constructor(`is`: ItemStack) {
        itemStack = `is`
    }

    @JvmOverloads
    constructor(m: Material?, amount: Int = 1) {
        itemStack = ItemStack(m!!, amount)
    }

    fun clone(): ItemBuilder {
        return ItemBuilder(itemStack)
    }

    fun setDurability(dur: Short): ItemBuilder {
        itemStack.durability = dur
        return this
    }

    fun setInvisible(): ItemBuilder {
        val im = itemStack.itemMeta
        im!!.persistentDataContainer.set(Main.invisibleKey, PersistentDataType.BYTE, 1.toByte())
        itemStack.itemMeta = im
        return this
    }

    fun setName(name: String?): ItemBuilder {
        val im = itemStack.itemMeta
        im!!.setDisplayName(name)
        itemStack.itemMeta = im
        return this
    }

    fun setLocalizedName(localizedName: String?): ItemBuilder {
        val im = itemStack.itemMeta
        im!!.setLocalizedName(localizedName)
        itemStack.itemMeta = im
        return this
    }

    fun removeEnchantment(enchant: Enchantment?): ItemBuilder {
        itemStack.removeEnchantment(enchant!!)
        return this
    }

    fun addEnchant(enchant: Enchantment?, level: Int?): ItemBuilder {
        val im = itemStack.itemMeta
        im!!.addEnchant(enchant!!, level!!, true)
        itemStack.itemMeta = im
        return this
    }

    fun addEnchantments(enchantments: Map<Enchantment?, Int?>?): ItemBuilder {
        itemStack.addEnchantments(enchantments!!)
        return this
    }

    fun setSkullOwner(owner: Player?): ItemBuilder {
        val im = itemStack.itemMeta as SkullMeta?
        im!!.owningPlayer = owner
        itemStack.itemMeta = im
        return this
    }

    fun setRocketPower(power: Int?): ItemBuilder {
        val fm = itemStack.itemMeta as FireworkMeta?
        fm!!.power = power!!
        itemStack.itemMeta = fm
        return this
    }

    fun setPotionEffect(effect: PotionEffectType?): ItemBuilder {
        val potionMeta = itemStack.itemMeta as PotionMeta?
        potionMeta!!.setMainEffect(effect!!)
        itemStack.itemMeta = potionMeta
        return this
    }

    fun setUnbreakable(): ItemBuilder {
        val im = itemStack.itemMeta
        im!!.isUnbreakable = true
        itemStack.itemMeta = im
        return this
    }

    fun setLore(vararg lore: String?): ItemBuilder {
        val im = itemStack.itemMeta
        im!!.lore = Arrays.asList(*lore)
        itemStack.itemMeta = im
        return this
    }

    fun setLore(lore: List<String?>?): ItemBuilder {
        val im = itemStack.itemMeta
        im!!.lore = lore
        itemStack.itemMeta = im
        return this
    }

    fun removeLoreLine(line: String): ItemBuilder {
        val im = itemStack.itemMeta
        val lore: MutableList<String> = ArrayList(im!!.lore)
        if (!lore.contains(line)) return this
        lore.remove(line)
        im.lore = lore
        itemStack.itemMeta = im
        return this
    }

    fun addLoreLine(line: String?): ItemBuilder {
        val im = itemStack.itemMeta
        var lore: MutableList<String?> = ArrayList()
        if (im!!.hasLore()) lore = ArrayList(im.lore)
        lore.add(line)
        im.lore = lore
        itemStack.itemMeta = im
        return this
    }

    fun addLoreLine(line: String, pos: Int): ItemBuilder {
        val im = itemStack.itemMeta
        val lore: MutableList<String> = ArrayList(im!!.lore)
        lore[pos] = line
        im.lore = lore
        itemStack.itemMeta = im
        return this
    }

    fun setLeatherColor(color: Color?): ItemBuilder {
        try {
            val im = itemStack.itemMeta as LeatherArmorMeta?
            im!!.setColor(color)
            itemStack.itemMeta = im
        } catch (classCastException: ClassCastException) {
        }
        return this
    }

    fun addItemFlag(flag: ItemFlag): ItemBuilder {
        val im = itemStack.itemMeta
        im!!.addItemFlags(*arrayOf(flag))
        itemStack.itemMeta = im
        return this
    }

    fun setCustomModelData(modelID: Int?): ItemBuilder {
        val im = itemStack.itemMeta
        im!!.setCustomModelData(modelID)
        itemStack.itemMeta = im
        return this
    }

    fun setCompassLocation(pos: Location?): ItemBuilder {
        val im = itemStack.itemMeta as CompassMeta?
        im!!.isLodestoneTracked = true
        im.lodestone = pos
        itemStack.itemMeta = im
        return this
    }

    fun setCompassLocation(world: World?, x: Int, y: Int, z: Int): ItemBuilder {
        val im = itemStack.itemMeta as CompassMeta?
        val loc = Location(world, x.toDouble(), y.toDouble(), z.toDouble())
        im!!.isLodestoneTracked = true
        im.lodestone = loc
        itemStack.itemMeta = im
        return this
    }

    fun addPotionEffect(effect: PotionEffect?): ItemBuilder {
        val im = itemStack.itemMeta as PotionMeta?
        im!!.addCustomEffect(effect!!, true)
        itemStack.itemMeta = im
        return this
    }

    fun setPotionColor(color: Color?): ItemBuilder {
        val im = itemStack.itemMeta as PotionMeta?
        im!!.color = color
        itemStack.itemMeta = im
        return this
    }
}