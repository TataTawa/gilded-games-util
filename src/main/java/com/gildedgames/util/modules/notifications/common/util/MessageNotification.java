package com.gildedgames.util.modules.notifications.common.util;

import java.util.UUID;

import com.gildedgames.util.core.io.ByteBufBridge;
import com.gildedgames.util.modules.notifications.common.core.INotificationMessage;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class MessageNotification extends AbstractNotification
{

	private String title;

	private final DefaultMessage message;

	private MessageNotification()
	{
		this.message = new DefaultMessage("", "", null, null);
	}

	public MessageNotification(String title, String message, UUID sender, UUID receiver)
	{
		super(sender, receiver);
		this.title = title;
		this.message = new DefaultMessage(title, message, sender, receiver);
	}

	@Override
	public void read(ByteBuf input)
	{
		super.read(input);
		this.message.read(new ByteBufBridge(input));
		this.title = ByteBufUtils.readUTF8String(input);
	}

	@Override
	public void write(ByteBuf output)
	{
		super.write(output);
		this.message.write(new ByteBufBridge(output));
		ByteBufUtils.writeUTF8String(output, this.title);
	}

	@Override
	public String getName()
	{
		return this.title;
	}

	@Override
	public INotificationMessage getMessage()
	{
		return this.message;
	}

}