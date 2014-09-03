package com.countrygamer.chalked.addon

/**
 *
 *
 * @author CountryGamer
 */
object EnderStorageManager {

	def getColoursFromFreq(freq: Int): Array[Int] = {
		try {
			EnderStorage.EnderStorageManager.getMethod("getColoursFromFreq", Integer.TYPE)
					.invoke(null, Int.box(freq)).asInstanceOf[Array[Int]]
		}
		catch {
			case e: Exception =>
				e.printStackTrace()
				new Array[Int](3)
		}
	}

	def getFreqFromColours(colors: Array[Int]): Integer = {
		try {
			EnderStorage.EnderStorageManager
					.getMethod("getFreqFromColours", classOf[Array[Int]]).invoke("null", colors)
					.asInstanceOf[Integer]
		}
		catch {
			case e: Exception =>
				e.printStackTrace()
				-1
		}
	}

}
