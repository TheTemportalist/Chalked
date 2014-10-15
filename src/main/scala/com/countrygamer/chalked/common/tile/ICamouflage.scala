package com.countrygamer.chalked.common.tile

import com.countrygamer.cgo.library.common.lib.ItemMeta
import net.minecraft.block.Block
import net.minecraft.nbt.NBTTagCompound

/**
 *
 *
 * @author CountryGamer
 */
trait ICamouflage {

	var camouflageBlock: Block = null
	var camouflageMetadata: Int = 0

	def isCamouflaged: Boolean = {
		this.hasCamouflage
	}

	def hasCamouflage: Boolean = {
		this.camouflageBlock != null
	}

	def getCamouflage: ItemMeta = {
		new ItemMeta(this.camouflageBlock, this.camouflageMetadata)
	}

	def setCamouflage(itemMeta: ItemMeta): Unit = {
		this.camouflageBlock = itemMeta.getBlock
		if (this.camouflageBlock != null)
			this.camouflageMetadata = itemMeta.getMetadata

	}

	def saveCamouflageNBT(tagCom: NBTTagCompound): Unit = {

		tagCom.setBoolean("ICamouflage_hasCamouflage", this.hasCamouflage)
		if (this.hasCamouflage) {
			tagCom.setInteger("ICamouflage_camouflageBlockID",
				Block.getIdFromBlock(this.camouflageBlock))
			tagCom.setInteger("ICamouflage_camouflageMetadata", this.camouflageMetadata)
		}

	}

	def readCamouflageNBT(tagCom: NBTTagCompound): Unit = {

		if (tagCom.getBoolean("ICamouflage_hasCamouflage")) {
			this.camouflageBlock = Block
					.getBlockById(tagCom.getInteger("ICamouflage_camouflageBlockID"))
			this.camouflageMetadata = tagCom.getInteger("ICamouflage_camouflageMetadata")
		}

	}

}
