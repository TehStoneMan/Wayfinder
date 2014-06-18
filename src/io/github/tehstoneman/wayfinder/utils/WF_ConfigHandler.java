package io.github.tehstoneman.wayfinder.utils;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class WF_ConfigHandler
{
	public static void init( File configFile )
	{
		final Configuration config = new Configuration( configFile );

		config.load();

		// Block IDs

		// General settings
		// Settings.limitStackSize = config.get(config.CATEGORY_GENERAL,
		// "limitStackSize", 16).getInt();
		// Settings.debug = config.get(config.CATEGORY_GENERAL, "debug",
		// false).getBoolean(false);

		config.save();
	}
}
