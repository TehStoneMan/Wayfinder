package io.github.tehstoneman.wayfinder.data;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class WaypointManager
{
	public List< Waypoint >	pointsPrivate	= new ArrayList< Waypoint >();
	public List< Waypoint >	pointsPublic	= new ArrayList< Waypoint >();

	// public void add( int posX, int posY, int posZ )
	public void add( Waypoint wp )
	{
		if (!exists( wp )) pointsPrivate.add( wp );
	}

	public boolean exists( Waypoint wp )
	{
		if (pointsPrivate.isEmpty()) return false;
		for (int i = 0; i < pointsPrivate.size(); i++)
			if (pointsPrivate.get( i ).isEqual( wp )) return true;

		return false;
	}
	
	public void drawWaypoints( Minecraft mc )
	{
		if (pointsPrivate.isEmpty()) return;
		for (int i = 0; i < pointsPrivate.size(); i++)
			pointsPrivate.get( i ).drawFlag( mc );
	}
}
