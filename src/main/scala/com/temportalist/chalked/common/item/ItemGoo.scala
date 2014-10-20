package com.temportalist.chalked.common.item

import java.util

import com.temportalist.chalked.common.Chalked
import com.temportalist.chalked.common.init.CBlocks
import com.temportalist.chalked.common.tile.TEColored
import com.temportalist.origin.wrapper.common.item.ItemWrapper
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.world.World

/**
 *
 *
 * @author TheTemportalist
 */
class ItemGoo(name: String) extends ItemWrapper(Chalked.pluginID, name) with IColored {

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

	override def onItemUse(itemStack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int,
			z: Int, side: Int, offsetX: Float, offsetY: Float, offsetZ: Float): Boolean = {
		val block: Block = world.getBlock(x, y, z)
		val meta: Int = world.getBlockMetadata(x, y, z)
		if (!block.hasTileEntity(meta)) {
			world.setBlock(x, y, z, CBlocks.coloredBlock)
			world.getTileEntity(x, y, z).asInstanceOf[TEColored]
					.setBlockAndColor(block, meta, itemStack.getTagCompound)
			true
		}
		false
	}

}
