package com.warriorsguildcatapult;

import javax.inject.Inject;

import net.runelite.api.Client;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.Projectile;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ProjectileMoved;
import net.runelite.api.widgets.Widget;

import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;


@PluginDescriptor(
		name = "Warriors Guild Catapult Helper"
)
public class WarriorsGuildCatapult extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private CatapultWidgetOverlay overlay;


	private Widget highlightedWidget;


	// Item ID for the Defender's Shield required to start the minigame
	private static final int SHIELD_ID = 8856;

	// Interface group containing the catapult minigame defensive option buttons
	private static final int WIDGET_GROUP = 411;


	// Child IDs for each defensive option in the interface
	private static final int STAB = 6;
	private static final int CRUSH = 8;
	private static final int SLASH = 10;
	private static final int MAGIC = 12;

	// Projectile IDs fired by the catapult for each corresponding attack style
	private static final int PROJECTILE_STAB = 679;
	private static final int PROJECTILE_CRUSH = 680;
	private static final int PROJECTILE_SLASH = 681;
	private static final int PROJECTILE_MAGIC = 682;


	@Override
	protected void startUp()
	{
		overlayManager.add(overlay);
	}


	@Override
	protected void shutDown()
	{
		// Remove overlay and clear the previously highlighted selection if any
		overlayManager.remove(overlay);
		highlightedWidget = null;
	}


	@Subscribe
	public void onGameTick(GameTick event)
	{
		// Remove highlight if the player no longer has the required defender's shield equipped
		if (!hasShieldEquipped())
		{
			highlightedWidget = null;
		}
	}


	@Subscribe
	public void onProjectileMoved(ProjectileMoved event)
	{
		// If the required defender's shield is not equipped, do not continue
		if (!hasShieldEquipped())
		{
			return;
		}

		Projectile projectile = event.getProjectile();

		int childId = getWidgetChildId(projectile.getId());

		if (childId == -1)
		{
			return;
		}

		Widget widget = client.getWidget(WIDGET_GROUP, childId);

		if (widget == null)
		{
			return;
		}

		if (highlightedWidget != widget)
		{
			highlightedWidget = widget;
		}
	}

	// Read the incoming catapult projectile, return its corresponding defense widget ID
	private int getWidgetChildId(int projectileId)
	{
		switch (projectileId)
		{
			case PROJECTILE_CRUSH:
				return CRUSH;

			case PROJECTILE_SLASH:
				return SLASH;

			case PROJECTILE_STAB:
				return STAB;

			case PROJECTILE_MAGIC:
				return MAGIC;

			default:
				return -1;
		}
	}


	private boolean hasShieldEquipped()
	{
		// Access the player's equipped items
		ItemContainer equipment =
				client.getItemContainer(InventoryID.EQUIPMENT);


		if (equipment == null)
		{
			return false;
		}


		// Check if the shield is equipped
		Item shield =
				equipment.getItem(EquipmentInventorySlot.SHIELD.getSlotIdx());


		return shield != null && shield.getId() == SHIELD_ID;
	}

	public Widget getHighlightedWidget()
	{
		return highlightedWidget;
	}
}