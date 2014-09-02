package com.countrygamer.chalked.common.lib

import net.minecraft.nbt.NBTTagCompound

/**
 *
 *
 * @author CountryGamer
 */
object HexHelper {

	def setHex(tagCom: NBTTagCompound, r: Int, g: Int, b: Int): Unit = {
		tagCom.setIntArray("colors", Array[Int](r, g, b))
		val hex: Int = Hex.toHex(r, g, b)
		if (hex >= 0) {
			tagCom.setInteger("hex", hex)
		}
	}

}
