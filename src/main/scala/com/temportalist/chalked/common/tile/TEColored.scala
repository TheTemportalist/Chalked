package com.temportalist.chalked.common.tile

import java.util

import com.temportalist.origin.library.common.lib.ItemMeta
import com.temportalist.origin.wrapper.common.tile.TEWrapper
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

/**
 *
 *
 * @author TheTemportalist
 */
class TEColored() extends TEWrapper("Smeared Block") with ICamouflage {

	private var colorTag: NBTTagCompound = new NBTTagCompound

	def setBlockAndColor(block: Block, meta: Int, tagCom: NBTTagCompound): Unit = {
		this.setCamouflage(new ItemMeta(block, meta))
		this.colorTag = tagCom
	}

	def getColor(): Int = {
		this.colorTag.getInteger("hex")
	}

	override def getDrops(drops: util.ArrayList[ItemStack], block: Block, metadata: Int): Unit = {
		drops.clear()
		val thisStack: ItemStack = new ItemStack(block, 1, metadata)
		val tagCom: NBTTagCompound = new NBTTagCompound
		this.saveCamouflageNBT(tagCom)
		tagCom.setTag("colorTag", this.colorTag)
		thisStack.setTagCompound(tagCom)
		drops.add(thisStack)
	}

	override def writeToNBT(tagCom: NBTTagCompound): Unit = {
		super.writeToNBT(tagCom)

		val blockMetaTag: NBTTagCompound = new NBTTagCompound
		this.saveCamouflageNBT(blockMetaTag)
		tagCom.setTag("camouflage", blockMetaTag)

		tagCom.setTag("colorTag", this.colorTag)

	}

	override def readFromNBT(tagCom: NBTTagCompound): Unit = {
		super.readFromNBT(tagCom)

		this.readCamouflageNBT(tagCom.getCompoundTag("camouflage"))
		this.colorTag = tagCom.getCompoundTag("colorTag")

	}

}
