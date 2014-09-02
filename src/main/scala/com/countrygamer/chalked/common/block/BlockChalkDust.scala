package com.countrygamer.chalked.common.block

import java.util.Random

import com.countrygamer.cgo.wrapper.common.block.BlockWrapperTE
import com.countrygamer.chalked.common.tile.TileEntityChalkDust
import net.minecraft.block.material.Material
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.util.AxisAlignedBB
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
class BlockChalkDust(mat: Material, pluginID: String, name: String)
		extends BlockWrapperTE(mat, pluginID, name, classOf[TileEntityChalkDust]) {

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
		5
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
		world.getTileEntity(x, y, z).asInstanceOf[TileEntityChalkDust].add(player.getHeldItem)
	}

}
