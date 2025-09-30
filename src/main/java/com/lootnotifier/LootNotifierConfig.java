package com.lootnotifier;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;


@ConfigGroup("example")
public interface LootNotifierConfig extends Config
{
	@ConfigItem(
		keyName = "webhookUrl",
		name = "Discord webhook",
		description = "Paste your Discord webhook url here"
	)
	default String webhookUrl(){
		return "";
	}

	@ConfigItem(
			keyName = "minValue",
			name = "Minimum Item Value",
			description = "Only notify for drops worth at least this much (gp)"
	)
	default int minvalue(){
		return 100000;
	}


	@ConfigItem(
			keyName = "webhookGuide",
			name = "Webhook Setup Guide",
			description = ""
	)
	default String webhookGuide()
	{
		return "1. Open Discord → Server Settings → Integrations → Webhooks\n" +
				"2. Create a new webhook.\n" +
				"3. Copy the URL and paste it above.\n" +
				"4. Test it by receiving a drop in-game.\n" +
				"Note: You can select which channel the webhook posts to using the 'Channel' dropdown in the webhook settings.";
	}


}
