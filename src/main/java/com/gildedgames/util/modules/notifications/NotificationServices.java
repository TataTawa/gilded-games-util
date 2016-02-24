package com.gildedgames.util.modules.notifications;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.modules.notifications.common.core.INotification;
import com.gildedgames.util.modules.notifications.common.core.INotificationMessage;
import com.gildedgames.util.modules.notifications.common.core.NotificationDispatcher;
import com.gildedgames.util.modules.notifications.common.networking.messages.PacketRemoveMessage;
import com.gildedgames.util.modules.notifications.common.player.PlayerNotification;
import com.gildedgames.util.modules.notifications.common.player.PlayerNotificationFactory;
import com.gildedgames.util.modules.player.common.IPlayerHookPool;
import com.gildedgames.util.modules.player.common.PlayerHookPool;

public class NotificationServices
{
	private Side side;

	private IPlayerHookPool<PlayerNotification> players;

	private NotificationDispatcher dispatcher;

	public NotificationServices(Side side)
	{
		this.side = side;
	}

	public IPlayerHookPool<PlayerNotification> getPlayers()
	{
		if (this.players == null)
		{
			this.players = new PlayerHookPool<>("group", new PlayerNotificationFactory(), this.side);
		}

		return this.players;
	}

	public void queueNotificationForDisplay(INotification notification)
	{

	}

	public void onRenderOverlay()
	{

	}

	public void onServerTick()
	{
		for (PlayerNotification player : this.players.getPlayerHooks())
		{
			if (player.getNotifications().isEmpty())
			{
				continue;
			}
			List<INotificationMessage> toRemove = new ArrayList<>();
			for (INotificationMessage message : player.getNotifications())
			{
				if (!message.isRelevant())
				{
					toRemove.add(message);
				}
			}
			for (INotificationMessage remove : toRemove)
			{
				player.removeNotification(remove);
				UtilModule.NETWORK.sendTo(new PacketRemoveMessage(remove), (EntityPlayerMP) player.getProfile().getEntity());
			}
		}
	}

	public NotificationDispatcher getDispatcher()
	{
		if (this.dispatcher == null)
		{
			this.dispatcher = new NotificationDispatcher();
		}

		return this.dispatcher;
	}
}