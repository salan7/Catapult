package com.warriorsguildcatapult;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class WarriorsGuildCatapultTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(WarriorsGuildCatapult.class);
		RuneLite.main(args);
	}
}