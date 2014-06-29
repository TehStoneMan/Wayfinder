package io.github.tehstoneman.wayfinder.utils;

import io.github.tehstoneman.wayfinder.Wayfinder;
import io.github.tehstoneman.wayfinder.data.Waypoint;
import io.github.tehstoneman.wayfinder.gui.GuiCompass;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class WF_EventHandler
{
	private final Minecraft	mc;

	// public WaypointManager wpManager;

	public WF_EventHandler()
	{
		mc = Minecraft.getMinecraft();
	}

	@SubscribeEvent
	public void onRenderWorldLast( RenderWorldLastEvent event )
	{
		final EntityPlayer player = mc.thePlayer;
		if (player.getHeldItem() != null
				&& player.getHeldItem().getItem() == Items.compass)
			Wayfinder.wpManager.drawWaypoints( mc );
	}

	@SubscribeEvent
	public void onRenderOverlay( RenderGameOverlayEvent event )
	{
		// Render after experience bar
		if (event.isCancelable() || event.type != ElementType.EXPERIENCE)
			return;

		// Draw the compass
		// final EntityClientPlayerMP player = mc.thePlayer;
		final EntityPlayer player = mc.thePlayer;
		if (player.getHeldItem() != null
				&& player.getHeldItem().getItem() == Items.compass)
		{
			final GuiCompass compass = new GuiCompass();
			compass.drawCompassBar( mc );

			// Wayfinder.wpManager.drawWaypoints( mc );
		}
	}

	@SubscribeEvent
	public void onMouseClick( PlayerInteractEvent event )
	{
		final EntityPlayer player = event.entityPlayer;
		final ItemStack heldItem = player.getHeldItem();

		if (heldItem == null || heldItem.getItem() != Items.compass) return;

		final int posX = event.x;
		final int posY = event.y;
		final int posZ = event.z;

		// int face = event.face;

		player.addChatMessage( new ChatComponentText( "Compass clicked" ) );
		if (event.action == Action.LEFT_CLICK_BLOCK)
		{
			final Waypoint wp = new Waypoint( posX, posY, posZ );
			Wayfinder.wpManager.add( wp );
		}
		else player.addChatMessage( new ChatComponentText( "Right click" ) );
		// TODO: Open waypoint GUI

		event.useBlock = Result.DENY;
		event.useItem = Result.DEFAULT;

		if (event.isCancelable()) event.setCanceled( true );
	}
}
