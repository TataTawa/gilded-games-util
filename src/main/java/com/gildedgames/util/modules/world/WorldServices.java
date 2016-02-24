package com.gildedgames.util.modules.world;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.modules.world.common.IWorldHookPool;
import com.gildedgames.util.modules.world.common.world.IWorld;
import com.gildedgames.util.modules.world.common.world.IWorldFactory;

public class WorldServices
{

	private List<IWorldHookPool<?>> worldPools;

	private final List<IWorld> worldWrappers = new ArrayList<>();

	private final IWorldFactory<?> wrapperFactory;//Change to use different IWorld wrappers

	private final boolean isRemote;

	public WorldServices(boolean isRemote, IWorldFactory<?> wrapperFactory)
	{
		this.isRemote = isRemote;
		this.wrapperFactory = wrapperFactory;
	}

	public List<IWorldHookPool<?>> getPools()
	{
		if (this.worldPools == null)
		{
			this.worldPools = new ArrayList<>();
		}

		return this.worldPools;
	}

	public void registerWorldPool(IWorldHookPool<?> worldPool)
	{
		this.getPools().add(worldPool);
	}

	public IWorld getWrapper(int dimId)
	{
		for (IWorld wrapper : this.worldWrappers)
		{
			if (wrapper != null && wrapper.isWrapperFor(dimId, this.isRemote))
			{
				return wrapper;
			}
		}
		
		IWorld world = this.wrapperFactory.create(dimId, this.isRemote);
		this.worldWrappers.add(world);
		
		return world;
	}

	public void reset()
	{
		this.worldWrappers.clear();
	}

}