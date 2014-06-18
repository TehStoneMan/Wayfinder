package io.github.tehstoneman.wayfinder.utils;

import io.github.tehstoneman.wayfinder.gui.GuiCompass;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.init.Items;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class WF_EventHandler
{
	private final Minecraft	mc;

	public WF_EventHandler()
	{
		mc = Minecraft.getMinecraft();
	}

	@SubscribeEvent
	public void onRenderOverlay( RenderGameOverlayEvent event )
	{
		// Render after experience bar
		if (event.isCancelable() || event.type != ElementType.EXPERIENCE)
			return;

		// Draw the compass
		final EntityClientPlayerMP player = mc.thePlayer;
		if (player.getHeldItem() != null
				&& player.getHeldItem().getItem() == Items.compass)
		{
			final GuiCompass compass = new GuiCompass( mc );
			compass.drawCompassBar();
		}
	}
}
