package com.temportalist.chalked.common

import com.temportalist.chalked.common.init.{CBlocks, CItems}
import com.temportalist.chalked.common.network.PacketSaveColors
import com.temportalist.origin.library.common.helpers.RegisterHelper
import com.temportalist.origin.wrapper.common.PluginWrapper
import cpw.mods.fml.common.event._
import cpw.mods.fml.common.{Mod, SidedProxy}

/**
 *
 *
 * @author TheTemportalist
 */
@Mod(
	modid = Chalked.pluginID, name = Chalked.pluginName, version = "@PLUGIN_VERSION@",
	guiFactory = Chalked.clientProxy,
	modLanguage = "scala",
	dependencies = "required-after:Forge@[10.13,);required-after:origin@[3.3,);after:EnderStorage@[1.4.5.22,);"
)
object Chalked extends PluginWrapper {

	final val pluginID = "chalked"
	final val pluginName = "Chalked"
	final val clientProxy = "com.temportalist.chalked.client.ClientProxy"
	final val serverProxy = "com.temportalist.chalked.server.ServerProxy"

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
