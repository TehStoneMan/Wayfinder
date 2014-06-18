package io.github.tehstoneman.wayfinder.utils;

import io.github.tehstoneman.wayfinder.Wayfinder;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class WF_PacketHandler
{
	public static final SimpleNetworkWrapper	INSTANCE	= NetworkRegistry.INSTANCE
																	.newSimpleChannel( Wayfinder.MOD_ID
																			.toLowerCase() );

	public static void init()
	{
		/*
		 * INSTANCE.registerMessage( MessageTileEntityEE.class,
		 * MessageTileEntityEE.class, 0, Side.CLIENT );
		 * INSTANCE.registerMessage( MessageTileCalcinator.class,
		 * MessageTileCalcinator.class, 1, Side.CLIENT );
		 * INSTANCE.registerMessage( MessageTileEntityAludel.class,
		 * MessageTileEntityAludel.class, 2, Side.CLIENT );
		 * INSTANCE.registerMessage( MessageTileEntityGlassBell.class,
		 * MessageTileEntityGlassBell.class, 3, Side.CLIENT );
		 * INSTANCE.registerMessage( MessageKeyPressed.class,
		 * MessageKeyPressed.class, 4, Side.SERVER );
		 */
	}
}
