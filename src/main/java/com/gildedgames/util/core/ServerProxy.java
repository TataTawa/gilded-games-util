package com.gildedgames.util.core;

import com.gildedgames.util.core.gui.viewing.MinecraftGuiWrapperEvents;
import com.gildedgames.util.ui.data.TickInfo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

public class ServerProxy implements ICore
{

	public final TickInfo MinecraftTickInfo = new MinecraftGuiWrapperEvents();

	public EntityPlayer getPlayer()
	{
		return null;
	}

	public void addScheduledTask(Runnable runnable)
	{

	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		UtilEvents utilEvents = new UtilEvents();

		UtilCore.registerEventHandler(utilEvents);
	}

	@Override
	public void init(FMLInitializationEvent event)
	{

	}

	@Override
	public void postInit(FMLPostInitializationEvent event)
	{

	}

	@Override
	public void serverAboutToStart(FMLServerAboutToStartEvent event)
	{

	}

	@Override
	public void serverStopping(FMLServerStoppingEvent event)
	{

	}

	@Override
	public void serverStopped(FMLServerStoppedEvent event)
	{

	}

	@Override
	public void serverStarting(FMLServerStartingEvent event)
	{

	}

	@Override
	public void serverStarted(FMLServerStartedEvent event)
	{

	}

	@Override
	public void flushData()
	{
	}

}
