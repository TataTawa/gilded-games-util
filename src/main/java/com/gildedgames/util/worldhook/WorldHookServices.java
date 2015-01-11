package com.gildedgames.util.worldhook;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.worldhook.common.IWorldPool;
import com.gildedgames.util.worldhook.common.world.IWorld;
import com.gildedgames.util.worldhook.common.world.IWorldWrapperFactory;
import com.gildedgames.util.worldhook.common.world.WorldMinecraftFactory;

public class WorldHookServices
{

	private List<IWorldPool> worldPools;

	private List<IWorld> worldWrappers = new ArrayList<IWorld>();

	private final IWorldWrapperFactory wrapperFactory = new WorldMinecraftFactory();//Change to use different IWorld wrappers

	private final boolean isRemote;

	public WorldHookServices(boolean isRemote)
	{
		this.isRemote = isRemote;
	}

	public List<IWorldPool> getPools()
	{
		if (this.worldPools == null)
		{
			this.worldPools = new ArrayList<IWorldPool>();
		}

		return this.worldPools;
	}

	public void registerWorldPool(IWorldPool worldPool)
	{
		this.getPools().add(worldPool);
	}

	public IWorld getWrapper(int dimId)
	{
		for (IWorld wrapper : this.worldWrappers)
		{
			if (wrapper.isWrapperFor(dimId, this.isRemote))
			{
				return wrapper;
			}
		}
		return this.wrapperFactory.create(dimId, this.isRemote);
	}

}
