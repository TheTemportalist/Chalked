package com.countrygamer.chalked.common

import com.countrygamer.cgo.library.common.helpers.RegisterHelper
import com.countrygamer.cgo.wrapper.common.PluginWrapper
import com.countrygamer.chalked.common.init.{CBlocks, CItems}
import com.countrygamer.chalked.common.network.PacketSaveColors
import cpw.mods.fml.common.event._
import cpw.mods.fml.common.{Mod, SidedProxy}

/**
 *
 *
 * @author CountryGamer
 */
@Mod(
	modid = Chalked.pluginID, name = Chalked.pluginName, version = "@PLUGIN_VERSION@",
	guiFactory = Chalked.clientProxy,
	modLanguage = "scala",
	dependencies = "required-after:Forge@[10.13,);required-after:cgo@[3.1,);after:EnderStorage@[1.4.5.22,);"
)
object Chalked extends PluginWrapper {

	final val pluginID = "chalked"
	final val pluginName = "Chalked"
	final val clientProxy = "com.countrygamer.chalked.client.ClientProxy"
	final val serverProxy = "com.countrygamer.chalked.server.ServerProxy"

	@SidedProxy(
		clientSide = this.clientProxy,
		serverSide = this.serverProxy
	)
	var proxy: CommonProxy = null

	final val guiChalkDust: Int = 0

	@Mod.EventHandler
	def preInit(event: FMLPreInitializationEvent): Unit = {
		super.preInitialize(this.pluginID, this.pluginName, event, this.proxy,
			CItems, CBlocks)

		RegisterHelper.registerPacketHandler(this.pluginID, classOf[PacketSaveColors])

	}

	@Mod.EventHandler
	def init(event: FMLInitializationEvent): Unit = {
		super.initialize(event)

	}

	@Mod.EventHandler
	def postInit(event: FMLPostInitializationEvent): Unit = {
		super.postInitialize(event)

	}

}
