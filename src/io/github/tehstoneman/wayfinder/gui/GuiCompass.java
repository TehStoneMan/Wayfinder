package io.github.tehstoneman.wayfinder.gui;

import io.github.tehstoneman.wayfinder.Wayfinder;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;

public class GuiCompass extends Gui
{
	private final Minecraft					mc;
	private static final ResourceLocation	compassBar	= new ResourceLocation(
																Wayfinder.MOD_ID,
																"textures/gui/compass.png" );
	private static final int				barWidth	= 81;

	public GuiCompass( Minecraft mc )
	{
		super();
		this.mc = mc;
	}

	public void drawCompassBar()
	{
		GL11.glColor4f( 1.0F, 1.0F, 1.0F, 1.0F );
		GL11.glDisable( GL11.GL_LIGHTING );

		mc.renderEngine.bindTexture( compassBar );
		final ScaledResolution screen = new ScaledResolution( mc.gameSettings,
				mc.displayWidth, mc.displayHeight );

		final EntityClientPlayerMP player = mc.thePlayer;
		final float facing = MathHelper
				.wrapAngleTo180_float( player.rotationYaw ) + 180f;

		final int xPos = screen.getScaledWidth() / 2 - barWidth / 2;
		final int yPos = screen.getScaledHeight()
				- Math.max( GuiIngameForge.left_height,
						GuiIngameForge.right_height );

		final int uPos = (int) (facing % 90f / 90f * (barWidth - 9));
		final int vPos = (int) (facing / 90f) * 9;

		drawTexturedModalRect( xPos, yPos, uPos, vPos, barWidth, 9 );
	}
}
