package com.gildedgames.util.group.common.notifications;

import java.util.UUID;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.group.GroupModule;
import com.gildedgames.util.group.common.IGroupPoolListenerClient;
import com.gildedgames.util.group.common.core.Group;
import com.gildedgames.util.group.common.core.GroupInfo;
import com.gildedgames.util.group.common.permissions.GroupPermsDefault;
import com.gildedgames.util.notifications.NotificationModule;

/**
 * Client sided only. Sends notifications to the player to
 * notify him of group changes. 
 * @author Emile
 *
 */
public class NotificationsPoolHook implements IGroupPoolListenerClient<NotificationsGroupHook>
{

	@Override
	public NotificationsGroupHook createGroupHook(Group group)
	{
		return new NotificationsGroupHook(group);
	}

	@Override
	public void onGroupAdded(Group group)
	{
	}

	@Override
	public void onGroupRemoved(Group group)
	{
	}

	@Override
	public void onGroupInfoChanged(Group group, GroupInfo infoOld, GroupInfo infoNew)
	{
		if (!infoOld.getName().equals(infoNew.getName()))
		{
			this.sendPopup(UtilModule.translate("group.namechanged") + " " + infoNew.getName(), this.getOwner(group));
		}
		else
		{
			this.sendPopup(UtilModule.translate("group.changedperms") + " " + UtilModule.translate(infoNew.getPermissions().getName()), this.getOwner(group));
		}
	}

	private UUID getOwner(Group group)
	{
		if (group.getPermissions() instanceof GroupPermsDefault)
		{
			return GroupModule.locate().getPlayers().get(group.getPermissions().owner()).getProfile().getUUID();
		}
		return null;
	}

	private void sendPopup(String message)
	{
		this.sendPopup(message, UtilModule.proxy.getPlayer().getGameProfile().getId());
	}

	private void sendPopup(String message, UUID uuid)
	{
		NotificationModule.sendPopup(message, uuid, UtilModule.proxy.getPlayer().getGameProfile().getId());
	}

	@Override
	public void onJoin(Group group)
	{
		this.sendPopup(UtilModule.translate("group.joined" + " " + group.getName()));
	}

	@Override
	public void onLeave(Group group)
	{
		this.sendPopup(UtilModule.translate("group.left" + " " + group.getName()));
	}

	@Override
	public void onInvited(Group group, UUID inviter)
	{
		NotificationModule.sendNotification(new NotificationMessageInvited(inviter, UtilModule.proxy.getPlayer().getGameProfile().getId(), group));
	}

	@Override
	public void onInviteRemoved(Group group)
	{
		this.sendPopup(UtilModule.translate("group.inviteremoved") + " " + group.getName());
	}

}
