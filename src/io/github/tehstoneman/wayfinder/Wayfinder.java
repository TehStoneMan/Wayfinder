/*
 * Wayfinder mod for Minecraft
 */

package io.github.tehstoneman.wayfinder;

import net.minecraftforge.common.MinecraftForge;

import io.github.tehstoneman.wayfinder.data.WaypointManager;
import io.github.tehstoneman.wayfinder.proxies.CommonProxy;
import io.github.tehstoneman.wayfinder.utils.WF_ConfigHandler;
import io.github.tehstoneman.wayfinder.utils.WF_EventHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * Base class for the Minecraft mod 'Wayfinder'
 * 
 * @author TehStoneMan
 * 
 */

@Mod( modid = Wayfinder.MOD_ID, name = Wayfinder.NAME,
		version = Wayfinder.VERSION )
public class Wayfinder
{
	// Wayfinder mod info
	public static final String		MOD_ID			= "wayfinder";
	public static final String		NAME			= "Wayfinder";
	public static final String		VERSION			= "0.2";
	public static final String		CHANNEL			= MOD_ID;
	public static final String		PROXY_LOCATION	= "io.github.tehstoneman.wayfinder.proxies.";

	// Register an instance of this mod
	@Instance( value = Wayfinder.MOD_ID )
	public static Wayfinder			instance;

	// Define proxies
	@SidedProxy( clientSide = Wayfinder.PROXY_LOCATION + "ClientProxy",
			serverSide = Wayfinder.PROXY_LOCATION + "CommonProxy" )
	public static CommonProxy		proxy;

	// Define custom GUI indices
	private static int				modGuiIndex		= 0;
	public static final int			GUI_WAYPOINTS	= modGuiIndex++;

	// Global variables
	public static WaypointManager	wpManager = null;

	// Perform pre initialisation operations
	@EventHandler
	public void PreInit( FMLPreInitializationEvent event )
	{
		// Initialise proxies.
		proxy.initRenderers();
		proxy.initSounds();

		// Load configuration
		WF_ConfigHandler.init( event.getSuggestedConfigurationFile() );
	}

	// Perform initialisation operations
	@EventHandler
	public void Init( FMLInitializationEvent event )
	{
		wpManager = new WaypointManager();
	}

	// Perform post initialisation operations
	@EventHandler
	public void PostInit( FMLPostInitializationEvent event )
	{
		// Register event handler
		MinecraftForge.EVENT_BUS.register( new WF_EventHandler() );
	}
}
