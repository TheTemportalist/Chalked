package com.countrygamer.chalked.common.item

import java.util

import com.countrygamer.cgo.library.common.utility.Cursor
import com.countrygamer.cgo.wrapper.common.item.ItemWrapper
import com.countrygamer.chalked.common.Chalked
import com.countrygamer.chalked.common.init.CBlocks
import com.countrygamer.chalked.common.tile.TEChalkDust
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.util.Vec3
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
class ItemChalk(name: String) extends ItemWrapper(Chalked.pluginID, name) with IColored {

	override def onItemUse(itemStack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int,
			z: Int, side: Int, offsetX: Float, offsetY: Float, offsetZ: Float): Boolean = {
		if (world.getBlock(x, y, z) != CBlocks.chalkDust) {
			val newCoords: Vec3 = Cursor
					.getNewCoordsFromSide(x, y, z, side)

			val canEdit: Boolean =
				player.canPlayerEdit(
					newCoords.xCoord.toInt, newCoords.yCoord.toInt, newCoords.zCoord.toInt,
					side, player.getHeldItem
				)
			val canPlace: Boolean = CBlocks.chalkDust
					.canPlaceBlockAt(
			            world, newCoords.xCoord.toInt, newCoords.yCoord.toInt,
			            newCoords.zCoord.toInt
					)
			if (canEdit && canPlace) {
				world.setBlock(newCoords.xCoord.toInt, newCoords.yCoord.toInt,
					newCoords.zCoord.toInt,
					CBlocks.chalkDust)
				world.getTileEntity(newCoords.xCoord.toInt, newCoords.yCoord.toInt,
					newCoords.zCoord.toInt)
						.asInstanceOf[TEChalkDust].add(itemStack, !world.isRemote)
				return true
			}
		}
		false
	}

	@SideOnly(Side.CLIENT)
	override def getColorFromItemStack(itemStack: ItemStack, meta: Int): Int = {
		this.getColorMult(itemStack)
	}

	override def getSubItems(item: Item, tab: CreativeTabs, list: util.List[_]): Unit = {
		this.getSubItems(item, list.asInstanceOf[util.List[ItemStack]])
	}

	@SideOnly(Side.CLIENT)
	override def addInformation(itemStack: ItemStack, player: EntityPlayer, list: util.List[_],
			isAdvanced: Boolean): Unit = {
		this.addInfo(itemStack, list.asInstanceOf[util.List[String]])
	}

}
