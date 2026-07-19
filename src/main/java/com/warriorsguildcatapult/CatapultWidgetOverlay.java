package com.warriorsguildcatapult;

import javax.inject.Inject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import net.runelite.api.widgets.Widget;

import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;


public class CatapultWidgetOverlay extends Overlay
{
    private final WarriorsGuildCatapult plugin;
    private static final Color HIGHLIGHT_COLOR = new Color(0, 255, 0, 80);
    private static final Color OUTLINE_COLOR = Color.GREEN;


    @Inject
    public CatapultWidgetOverlay(WarriorsGuildCatapult plugin)
    {
        this.plugin = plugin;

        //Render above the game's widgets and ensure the highlight appears at a higher priority
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(100.0f);
    }

    //Render the outline and highlight of the correct widget
    @Override
    public Dimension render(Graphics2D graphics)
    {
        Widget widget = plugin.getHighlightedWidget();

        // Draw nothing if there is no defense options or the widget is not available
        if (widget == null || widget.isHidden())
        {
            return null;
        }


        Rectangle bounds = widget.getBounds();


        // Fill highlight the widget selection
        graphics.setColor(HIGHLIGHT_COLOR);
        graphics.fill(bounds);


        // Outline the widget selection
        graphics.setColor(OUTLINE_COLOR);
        graphics.setStroke(new BasicStroke(3));
        graphics.draw(bounds);


        return null;
    }
}