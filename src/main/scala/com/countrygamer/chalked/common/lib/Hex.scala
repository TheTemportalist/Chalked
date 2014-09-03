package com.countrygamer.chalked.common.lib

import net.minecraft.item.ItemStack

/**
 *
 *
 * @author CountryGamer
 */
object Hex {

	def toHex(colors: Array[Int]): Int = {
		this.toHex(colors(0), colors(1), colors(2))
	}

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
		"%02X%02X%02X".format(
			r & 0xFF,
			g & 0xFF,
			b & 0xFF
		)
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
		val o: Int = 0
		dyeStack.getItemDamage match {
			case 0 =>
				Array[Int](0, 0, 0)
			case 1 =>
				Array[Int](127 + o, 0, 0)
			case 2 =>
				Array[Int](0, 127 + o, 0)
			case 3 =>
				Array[Int](100 + o + o, 50 + o + o, 0)
			case 4 =>
				Array[Int](0, 0, 127 + o)
			case 5 =>
				Array[Int](127 + o, 0, 255 + o)
			case 6 =>
				Array[Int](0, 191 + o, 191 + o)
			case 7 =>
				Array[Int](191 + o, 191 + o, 191 + o)
			case 8 =>
				Array[Int](64 + o, 64 + o, 64 + o)
			case 9 =>
				Array[Int](255 + o, 0, 255 + o)
			case 10 =>
				Array[Int](0, 255 + o, 0)
			case 11 =>
				Array[Int](255 + o, 191 + o, 0)
			case 12 =>
				Array[Int](0, 0, 255 + o)
			case 13 =>
				Array[Int](127 + o, 0, 127 + o)
			case 14 =>
				Array[Int](255 + o, 127 + o, 0)
			case 15 =>
				Array[Int](255 + o, 255 + o, 255 + o)
			case _ =>
				null
		}
	}

}
