package com.countrygamer.chalked.addon

import com.countrygamer.cgo.common.lib.LogHelper
import com.countrygamer.chalked.common.Chalked

/**
 *
 *
 * @author CountryGamer
 */
object EnderStorage {

	var EnderStorage: Class[_] = null
	var BlockEnderStorage: Class[_] = null
	var RayTracer: Class[_] = null
	var TileRequencyOwner: Class[_] = null
	var EnderStorageManager: Class[_] = null

	def register(): Unit = {

		try {
			this.EnderStorage = Class.forName("codechicken.enderstorage.EnderStorage")
			this.log("EnderStorage", isFound = true)
		}
		catch {
			case e: Exception =>
				this.log("EnderStorage", isFound = false)
				return
		}

		try {
			this.BlockEnderStorage =
					Class.forName("codechicken.enderstorage.common.BlockEnderStorage")
			this.RayTracer =
					Class.forName("codechicken.lib.raytracer.RayTracer")
			this.TileRequencyOwner =
					Class.forName("codechicken.enderstorage.common.TileFrequencyOwner")
			this.EnderStorageManager =
					Class.forName("codechicken.enderstorage.api.EnderStorageManager")

		}
		catch {
			case e: Exception =>
		}

	}

	def isLoaded(): Boolean = {
		this.EnderStorage != null
	}

	private def log(mod: String, isFound: Boolean): Unit = {
		LogHelper.info(Chalked.pluginName, mod + (if (!isFound) " not") + " found.")
	}

}
