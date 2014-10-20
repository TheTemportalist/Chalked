package com.temportalist.chalked.common.item

import java.util

import com.temportalist.chalked.common.lib.{Hex, HexHelper}
import com.temportalist.origin.library.client.utility.Keys
import net.minecraft.init.Items
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumChatFormatting

/**
 *
 *
 * @author TheTemportalist
 */
trait IColored {

	def getColorMult(itemStack: ItemStack): Int = {
		if (itemStack.hasTagCompound) {
			itemStack.getTagCompound.getInteger("hex")
		}
		else {
			0
		}
	}

	def getSubItems(item: Item, list: java.util.List[ItemStack]): Unit = {
		list.add(new ItemStack(item, 1, 0))
		var tagCom: NBTTagCompound = null
		for (i <- 0 until 16) {
			tagCom = new NBTTagCompound
			val itemStack: ItemStack = new ItemStack(item, 1, 0)

			val colors: Array[Int] = Hex.getRGBFromDye(new ItemStack(Items.dye, 1, i))
			HexHelper.setHex(tagCom, colors(0), colors(1), colors(2))

			itemStack.setTagCompound(tagCom)
			list.add(itemStack)
		}
	}

	def addInfo(itemStack: ItemStack, list: util.List[String]): Unit = {
		if (itemStack.hasTagCompound) {
			val tagCom: NBTTagCompound = itemStack.getTagCompound
			list.add(
				EnumChatFormatting.GOLD +
						Hex.toHexString(tagCom.getInteger("hex")) +
						EnumChatFormatting.RESET
			)
			if (tagCom.hasKey("colors")) {
				if (!Keys.keyPressed_Shift_Gui()) {
					list.add(
						"Hold " +
								EnumChatFormatting.AQUA +
								"SHIFT" +
								EnumChatFormatting.RESET +
								" down for more." +
								EnumChatFormatting.RESET
					)
				}
				else {
					val colors: Array[Int] = tagCom.getIntArray("colors")
					list.add(
						"Red: " + EnumChatFormatting.RED + colors(0) + EnumChatFormatting.RESET
					)
					list.add(
						"Green: " + EnumChatFormatting.GREEN + colors(1) + EnumChatFormatting.RESET
					)
					list.add(
						"Blue: " + EnumChatFormatting.BLUE + colors(2) + EnumChatFormatting.RESET
					)
				}
			}
		}
	}

}
