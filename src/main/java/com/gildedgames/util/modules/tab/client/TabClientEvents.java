package com.gildedgames.util.modules.tab.client;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.modules.tab.TabModule;
import com.gildedgames.util.modules.tab.client.util.RenderTabGroup;
import com.gildedgames.util.modules.tab.common.networking.packet.PacketOpenTab;
import com.gildedgames.util.modules.tab.common.util.ITabClient;
import com.gildedgames.util.modules.tab.common.util.ITabGroup;
import com.gildedgames.util.modules.tab.common.util.ITabGroupHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class TabClientEvents
{
	@SideOnly(Side.CLIENT)
	private final static RenderTabGroup tabGroupRenderer = new RenderTabGroup();

	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event)
	{
		GuiScreen gui = event.getGui();

		ITabGroupHandler groupHandler = TabModule.api().getActiveGroup();

		if (groupHandler != null)
		{
			ITabClient selectedTab = groupHandler.getClientGroup().getSelectedTab();

			if (event.getGui() != null && selectedTab.isTabValid(gui))
			{
				return;
			}

			TabModule.api().setActiveGroup(null);
		}

		for (ITabGroupHandler tabGroupHandler : TabModule.api().getRegisteredTabGroups().values())
		{
			ITabGroup<ITabClient> tabGroup = tabGroupHandler.getClientGroup();

			for (ITabClient tab : tabGroup.getTabs())
			{
				if (event.getGui() != null && tab.isTabValid(gui))
				{
					ITabClient selectedTab = tabGroup.getSelectedTab();

					if (selectedTab != null && !selectedTab.isTabValid(gui))
					{
						if (tabGroup.getRememberSelectedTab())
						{
							event.setCanceled(true);

							if (tabGroup.getRememberedTab() != null)
							{
								tabGroup.setSelectedTab(tabGroup.getRememberedTab());
							}
							else
							{
								tabGroup.setSelectedTab(tabGroup.getEnabledTabs().get(0));
							}

							tabGroup.getSelectedTab().onOpen(Minecraft.getMinecraft().thePlayer);

							UtilModule.NETWORK.sendToServer(new PacketOpenTab(tabGroup.getSelectedTab()));
						}
						else
						{
							tabGroup.setSelectedTab(tabGroup.getEnabledTabs().get(0));
						}
					}
					else
					{
						tabGroup.setSelectedTab(tab);
					}

					TabModule.api().setActiveGroup(tabGroupHandler);

					return;
				}
			}
		}
	}

	@SubscribeEvent
	public void tickStart(TickEvent.ClientTickEvent event)
	{
		if (event.phase == TickEvent.Phase.START)
		{
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;

			if (player == null || player.inventory.getItemStack() != null)
			{
				return;
			}

			ITabGroupHandler groupHandler = TabModule.api().getActiveGroup();

			if (groupHandler != null)
			{
				ITabGroup<ITabClient> activeGroup = groupHandler.getClientGroup();

				if (activeGroup != null)
				{
					while (Mouse.next())
					{
						ITabClient hoveredTab;

						hoveredTab = tabGroupRenderer.getHoveredTab(activeGroup);

						if (Mouse.getEventButtonState() && hoveredTab != null)
						{
							if (hoveredTab != activeGroup.getSelectedTab())
							{
								activeGroup.getSelectedTab().onClose(Minecraft.getMinecraft().thePlayer);
								activeGroup.setSelectedTab(hoveredTab);

								if (hoveredTab != activeGroup.getRememberedTab() && hoveredTab.isRemembered())
								{
									if (activeGroup.getRememberedTab() != null)
									{
										activeGroup.getRememberedTab().onClose(Minecraft.getMinecraft().thePlayer);
									}

									activeGroup.setRememberedTab(hoveredTab);
								}

								hoveredTab.onOpen(Minecraft.getMinecraft().thePlayer);
								UtilModule.NETWORK.sendToServer(new PacketOpenTab(hoveredTab));
							}
						}
						else if (Minecraft.getMinecraft().currentScreen != null)
						{
							try
							{
								Minecraft.getMinecraft().currentScreen.handleMouseInput();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void tickEnd(TickEvent.RenderTickEvent event)
	{
		if (event.phase == TickEvent.Phase.END)
		{
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;

			if (player == null || player.inventory.getItemStack() != null)
			{
				return;
			}

			ITabGroupHandler groupHandler = TabModule.api().getActiveGroup();

			if (groupHandler != null)
			{
				ITabGroup<ITabClient> activeGroup = groupHandler.getClientGroup();

				if (activeGroup != null)
				{
					tabGroupRenderer.render(activeGroup);
				}
			}
		}
	}

}
