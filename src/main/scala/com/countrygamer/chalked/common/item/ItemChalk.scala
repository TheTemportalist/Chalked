package com.countrygamer.chalked.common.item

import java.util

import com.countrygamer.cgo.common.lib.util.{Cursor, UtilKeys}
import com.countrygamer.cgo.wrapper.common.item.ItemWrapper
import com.countrygamer.chalked.common.init.CBlocks
import com.countrygamer.chalked.common.lib.{Hex, HexHelper}
import com.countrygamer.chalked.common.tile.TileEntityChalkDust
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumChatFormatting
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
class ItemChalk(pluginID: String, name: String) extends ItemWrapper(pluginID, name) {

	override def onItemUse(itemStack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int,
			z: Int, side: Int, offsetX: Float, offsetY: Float, offsetZ: Float): Boolean = {
		if (world.getBlock(x, y, z) != CBlocks.chalkDust) {
			val newCoords: Array[Int] = Cursor
					.getNewCoordsFromSide(x, y, z, side)

			val canEdit: Boolean =
				player.canPlayerEdit(
					newCoords(0), newCoords(1), newCoords(2),
					side, player.getHeldItem
				)
			val canPlace: Boolean = CBlocks.chalkDust
					.canPlaceBlockAt(
			            world, newCoords(0), newCoords(1), newCoords(2)
					)
			if (canEdit && canPlace) {
				world.setBlock(newCoords(0), newCoords(1), newCoords(2),
					CBlocks.chalkDust)
				world.getTileEntity(newCoords(0), newCoords(1), newCoords(2))
						.asInstanceOf[TileEntityChalkDust].add(itemStack)
				return true
			}
		}
		false
	}

	@SideOnly(Side.CLIENT)
	override def getColorFromItemStack(itemStack: ItemStack, meta: Int): Int = {
		if (itemStack.hasTagCompound) {
			itemStack.getTagCompound.getInteger("hex")
		}
		else {
			0
		}
	}

	override def getSubItems(item: Item, tab: CreativeTabs, list: util.List[_]): Unit = {
		var tagCom: NBTTagCompound = null
		for (i <- 0 until 16) {
			tagCom = new NBTTagCompound
			val itemStack: ItemStack = new ItemStack(item, 1, 0)

			val colors: Array[Int] = Hex.getRGBFromDye(new ItemStack(Items.dye, 1, i))
			HexHelper.setHex(tagCom, colors(0), colors(1), colors(2))

			itemStack.setTagCompound(tagCom)
			list.asInstanceOf[util.List[Any]].add(itemStack)
		}
	}

	@SideOnly(Side.CLIENT)
	override def addInformation(itemStack: ItemStack, player: EntityPlayer, list: util.List[_],
			isAdvanced: Boolean): Unit = {
		if (itemStack.hasTagCompound) {
			val tagCom: NBTTagCompound = itemStack.getTagCompound
			list.asInstanceOf[util.ArrayList[String]].add(
				EnumChatFormatting.GOLD +
						Hex.toHexString(tagCom.getInteger("hex")) +
						EnumChatFormatting.RESET
			)
			if (tagCom.hasKey("colors")) {
				if (!UtilKeys.isShiftKeyDown) {
					list.asInstanceOf[util.ArrayList[String]].add(
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
					list.asInstanceOf[util.ArrayList[String]].add(
						"Red: " + EnumChatFormatting.RED + colors(0) + EnumChatFormatting.RESET
					)
					list.asInstanceOf[util.ArrayList[String]].add(
						"Green: " + EnumChatFormatting.GREEN + colors(1) + EnumChatFormatting.RESET
					)
					list.asInstanceOf[util.ArrayList[String]].add(
						"Blue: " + EnumChatFormatting.BLUE + colors(2) + EnumChatFormatting.RESET
					)
				}
			}
		}
	}

}
