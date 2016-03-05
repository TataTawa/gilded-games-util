package com.gildedgames.util.modules.group.common.core;

import com.gildedgames.util.core.io.ByteBufBridge;
import com.gildedgames.util.core.io.MessageHandlerClient;
import com.gildedgames.util.modules.group.GroupModule;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketGroupPool implements IMessage
{
	private GroupPool pool;

	public PacketGroupPool()
	{

	}

	public PacketGroupPool(GroupPool pool)
	{
		this.pool = pool;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.pool = GroupModule.locate().getPoolFromID(ByteBufUtils.readUTF8String(buf));
		this.pool.clear();
		int size = buf.readInt();
		ByteBufBridge bridge = new ByteBufBridge(buf);
		for (int i = 0; i < size; i++)
		{
			GroupInfo info = bridge.getIO("");
			Group group = new Group(this.pool);
			group.setGroupInfo(info);
			this.pool.addGroupDirectly(group);
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.pool.getID());
		ByteBufBridge bridge = new ByteBufBridge(buf);
		buf.writeInt(this.pool.groups.size());
		for (Group group : this.pool.groups.values())
		{
			bridge.setIO("", group.getGroupInfo());
		}
	}

	public static class Handler extends MessageHandlerClient<PacketGroupPool, IMessage>
	{
		@Override
		public IMessage onMessage(PacketGroupPool message, EntityPlayer player)
		{
			// WHAT IS THIS SUPPOSED TO DO?!
			return null;
		}
	}
}
