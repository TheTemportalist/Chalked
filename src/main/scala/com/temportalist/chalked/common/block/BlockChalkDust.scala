package com.temportalist.chalked.common.block

import java.util.Random

import com.temportalist.chalked.common.Chalked
import com.temportalist.chalked.common.tile.TEChalkDust
import com.temportalist.origin.wrapper.common.block.BlockWrapperTE
import net.minecraft.block.material.Material
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.util.AxisAlignedBB
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.fluids.{FluidContainerRegistry, FluidRegistry}

/**
 *
 *
 * @author TheTemportalist
 */
class BlockChalkDust(mat: Material, pluginID: String, name: String)
		extends BlockWrapperTE(mat, pluginID, name, classOf[TEChalkDust]) {

	// Default Constructor
	{
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F)
	}

	// End Constructor

	override def getCollisionBoundingBoxFromPool(world: World, x: Int, y: Int,
			z: Int): AxisAlignedBB = {
		null
	}

	override def isOpaqueCube: Boolean = {
		false
	}

	override def renderAsNormalBlock(): Boolean = {
		false
	}

	override def getRenderType: Int = {
		com.countrygamer.chalked.client.render.BlockChalkDustRenderer.getRenderId
	}

	override def colorMultiplier(world: IBlockAccess, x: Int, y: Int, z: Int): Int = {
		world.getTileEntity(x, y, z).asInstanceOf[TEChalkDust].getOutputColor()
	}

	override def canPlaceBlockAt(world: World, x: Int, y: Int, z: Int): Boolean = {
		World.doesBlockHaveSolidTopSurface(world, x, y - 1, z)
	}

	override def getItemDropped(par1: Int, rand: Random, par2: Int): Item = {
		null
	}

	override def hasTileEntityDrops(metadata: Int): Boolean = {
		true
	}

	override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer,
			side: Int, offsetX: Float, offsetY: Float, offsetZ: Float): Boolean = {
		var inserted: Boolean = false
		if (!player.isSneaking) {
			if (player.getHeldItem != null) {
				val tile: TEChalkDust = world.getTileEntity(x, y, z).asInstanceOf[TEChalkDust]
				inserted = tile.add(player.getHeldItem, !world.isRemote)
				if (FluidContainerRegistry.isFilledContainer(player.getHeldItem) &&
						FluidContainerRegistry.getFluidForFilledItem(player.getHeldItem).getFluid ==
								FluidRegistry.WATER) {
					tile.make()
					return true
				}
				if (inserted)
					return true
			}
		}
		if (!inserted) {
			player.openGui(Chalked, Chalked.guiChalkDust, world, x, y, z)
			true
		}
		else
			false
	}

}
