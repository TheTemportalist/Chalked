package com.countrygamer.chalked.common.lib

import net.minecraft.item.ItemStack

/**
 *
 *
 * @author CountryGamer
 */
object Hex {

	def toHex(r: Int, g: Int, b: Int): Int = {
		try {
			return Integer.parseInt(this.toHexString(r, g, b), 16)
		}
		catch {
			case e: Exception =>
				e.printStackTrace()
		}
		-1
	}

	def toHexString(r: Integer, g: Integer, b: Integer): String = {
		String.format("%02X%02X%02X", r, g, b)
	}

	def toHexString(hex: Integer): String = {
		String.format("#%06X", hex)
	}

	def toRGB(hex: String): Array[Int] = {
		Array[Int](
			Integer.valueOf(hex.substring(0, 2), 16),
			Integer.valueOf(hex.substring(2, 4), 16),
			Integer.valueOf(hex.substring(4, 6), 16)
		)
	}

	def getRGBFromDye(dyeStack: ItemStack): Array[Int] = {
		dyeStack.getItemDamage match {
			case 0 =>
				Array[Int](0, 0, 0)
			case 1 =>
				Array[Int](127, 0, 0)
			case 2 =>
				Array[Int](0, 127, 0)
			case 3 =>
				Array[Int](100, 50, 0)
			case 4 =>
				Array[Int](0, 0, 127)
			case 5 =>
				Array[Int](127, 0, 255)
			case 6 =>
				Array[Int](0, 191, 191)
			case 7 =>
				Array[Int](191, 191, 191)
			case 8 =>
				Array[Int](64, 64, 64)
			case 9 =>
				Array[Int](255, 0, 255)
			case 10 =>
				Array[Int](0, 255, 0)
			case 11 =>
				Array[Int](255, 191, 0)
			case 12 =>
				Array[Int](0, 0, 255)
			case 13 =>
				Array[Int](127, 0, 127)
			case 14 =>
				Array[Int](255, 127, 0)
			case 15 =>
				Array[Int](255, 255, 255)
			case _ =>
				null
		}
	}

}
