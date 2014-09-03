package com.countrygamer.chalked.addon

import net.minecraft.tileentity.TileEntity

/**
 *
 *
 * @author CountryGamer
 */
object TileFrequencyOwner {

	def getFreq(tile: TileEntity): Int = {
		EnderStorage.TileRequencyOwner.getField("freq").getInt(tile)
	}

	def setFreq(tile: TileEntity, colors: Array[Int]): Unit = {
		try {
			EnderStorage.TileRequencyOwner.getMethod("setFreq", Integer.TYPE)
					.invoke(tile, EnderStorageManager.getFreqFromColours(colors))
		}
		catch {
			case e: Exception =>
		}
	}

}
