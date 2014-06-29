package io.github.tehstoneman.wayfinder.data;

import io.github.tehstoneman.wayfinder.Wayfinder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Define a waypoint for the Wayfinder Minecraft mod
 * 
 * @author TehStoneMan
 */
public class Waypoint
{
	private static final ResourceLocation	compassBar	= new ResourceLocation(
																Wayfinder.MOD_ID,
																"textures/gui/compass.png" );

	// The type of waypoint this represents
	public enum wpType
	{
		PATH, TEMP, AUTO, PUBLIC, PRIVATE
	};

	public int				posX, posY, posZ;	// Waypoint position
	public wpType			wp_type;			// Waypoint type

	public String			name;

	public int				world;				// The world (or dimension) that
												// this
												// waypoint is in

	protected RenderManager	renderManager;
	protected FontRenderer	fontrenderer;

	public Waypoint( int x, int y, int z )
	{
		if (renderManager == null) renderManager = RenderManager.instance;
		if (fontrenderer == null)
			fontrenderer = renderManager.getFontRenderer();

		posX = x;
		posY = y;
		posZ = z;

		wp_type = wpType.PATH;

		world = 0;
	}

	public Waypoint( int x, int y, int z, wpType type, int dimension )
	{
		if (renderManager == null) renderManager = RenderManager.instance;
		if (fontrenderer == null)
			fontrenderer = renderManager.getFontRenderer();

		posX = x;
		posY = y;
		posZ = z;

		wp_type = type;

		world = dimension;
	}

	public String getName()
	{
		if (name == null) return String.format( "<%d,%d,%d>", posX, posY, posZ );
		else return name;
	}

	public boolean isEqual( Waypoint wp )
	{
		return posX == wp.posX && posY == wp.posY && posZ == wp.posZ;
	}

	public boolean isMore( Waypoint wp )
	{
		if (posX > wp.posX) return true;
		if (posX == wp.posX && posY > wp.posY) return true;
		if (posX == wp.posX && posY == wp.posY && posZ > wp.posZ) return true;
		return false;
	}

	public boolean isLess( Waypoint wp )
	{
		if (posX < wp.posX) return true;
		if (posX == wp.posX && posY < wp.posY) return true;
		if (posX == wp.posX && posY == wp.posY && posZ < wp.posZ) return true;
		return false;
	}

	public void drawFlag( Minecraft mc )
	{
		// Get the distance from the player
		final EntityPlayer player = mc.thePlayer;
		player.getDistance( posX, posY, posZ );

		// if (renderManager == null) renderManager = RenderManager.instance;

		renderLabel( getName(), (float) (posX - player.posX),
				(float) (posY - player.posY), (float) (posZ - player.posZ), 64 );
		// RenderFloatingText.renderFloatingText( getName(), (float)(posX -
		// player.posX + 0.5), (float)(posY - player.posY), (float)(posZ -
		// player.posZ + 0.5) );

		// Set texture and get font renderer
		// mc.renderEngine.bindTexture( compassBar );

		// Calculate screen position of flag
		// final ScaledResolution screen = new ScaledResolution(
		// mc.gameSettings,
		// mc.displayWidth, mc.displayHeight );

		// final int xPos = screen.getScaledWidth() / 2 - 8;
		// final int yPos = screen.getScaledHeight() / 2 - 8;

		// final int uPos = 176;
		// final int vPos = 16;

		// Draw flag
		// drawTexturedModalRect( xPos, yPos, uPos, vPos, 16, 16 );
	}

	public void drawTexturedModalRect( int par1, int par2, int par3, int par4,
			int par5, int par6 )
	{
		final float f = 0.00390625F;
		final float f1 = 0.00390625F;
		final Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV( par1 + 0, par2 + par6, 0, (par3 + 0) * f,
				(par4 + par6) * f1 );
		tessellator.addVertexWithUV( par1 + par5, par2 + par6, 0, (par3 + par5)
				* f, (par4 + par6) * f1 );
		tessellator.addVertexWithUV( par1 + par5, par2 + 0, 0, (par3 + par5)
				* f, (par4 + 0) * f1 );
		tessellator.addVertexWithUV( par1 + 0, par2 + 0, 0, (par3 + 0) * f,
				(par4 + 0) * f1 );
		tessellator.draw();
	}

	protected void renderLabel( String label, double x, double y, double z,
			int colour )
	{
		// Calculate scale factor
		final float scale = 0.016666668F * 1.6F;

		// Save matrix
		GL11.glPushMatrix();

		// Translate point in 3D view
		GL11.glTranslatef( (float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F );

		// Set normal vector
		GL11.glNormal3f( 0.0F, 1.0F, 0.0F );

		// Rotate in 3D view
		GL11.glRotatef( -renderManager.playerViewY, 0.0F, 1.0F, 0.0F );
		GL11.glRotatef( renderManager.playerViewX, 1.0F, 0.0F, 0.0F );

		// Perform scale
		GL11.glScalef( -scale, -scale, scale );

		// Disable lighting
		GL11.glDisable( GL11.GL_LIGHTING );

		// Disable depth mask
		GL11.glDepthMask( false );
		GL11.glDisable( GL11.GL_DEPTH_TEST );

		// Set blend mode
		GL11.glEnable( GL11.GL_BLEND );
		OpenGlHelper.glBlendFunc( GL11.GL_SRC_ALPHA,
				GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0 );

		// Draw background
		final Tessellator tessellator = Tessellator.instance;

		GL11.glDisable( GL11.GL_TEXTURE_2D );
		tessellator.startDrawingQuads();
		final int halfTextWidth = fontrenderer.getStringWidth( label ) / 2;
		tessellator.setColorRGBA_F( 0.0F, 0.0F, 0.0F, 0.25F );
		tessellator.addVertex( -halfTextWidth - 1, -1, 0.0D );
		tessellator.addVertex( -halfTextWidth - 1, 8, 0.0D );
		tessellator.addVertex( halfTextWidth + 1, 8, 0.0D );
		tessellator.addVertex( halfTextWidth + 1, -1, 0.0D );
		tessellator.draw();

		// Draw text with shadow
		GL11.glEnable( GL11.GL_TEXTURE_2D );
		fontrenderer.drawString( label, -halfTextWidth, 0, 0xFFFFFF );
		GL11.glEnable( GL11.GL_DEPTH_TEST );
		GL11.glDepthMask( true );
		fontrenderer.drawString( label, -halfTextWidth, 0, -1 );

		// Restore default GL settings
		GL11.glEnable( GL11.GL_LIGHTING );
		GL11.glDisable( GL11.GL_BLEND );
		GL11.glColor4f( 1.0F, 1.0F, 1.0F, 1.0F );
		GL11.glPopMatrix();
	}

}
