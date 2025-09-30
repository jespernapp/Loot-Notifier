package com.lootnotifier;

import com.google.inject.Provides;
import javax.inject.Inject;


import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.client.events.NpcLootReceived;
import net.runelite.client.events.PlayerLootReceived;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.Text;



@PluginDescriptor(
		name = "Loot Notifier Discord"
)
public class LootNotifierPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private LootNotifierConfig config;

	@Inject
	private ItemManager itemManager;

	@Provides
	LootNotifierConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(LootNotifierConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		System.out.println("Loot Notifier started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		System.out.println("Loot Notifier stopped!");
	}

	@Subscribe
	public void onNpcLootReceived(NpcLootReceived event){
		handleLoot(event.getItems(), "NPC: " + event.getNpc().getName());
		System.out.println("Loot received from NPC: " + event.getNpc().getName());

	}

	@Subscribe
	public void onPlayerLootReceived(PlayerLootReceived event){
		handleLoot(event.getItems(), "Player: " + event.getPlayer().getName());
	}

	private void handleLoot(java.util.Collection<net.runelite.client.game.ItemStack> items, String source){
		for (net.runelite.client.game.ItemStack item : items){
			String itemName = Text.removeTags(itemManager.getItemComposition(item.getId()).getName());
			int unitPrice = itemManager.getItemPrice(item.getId());
			int totalValue = unitPrice * item.getQuantity();

			if (totalValue >= config.minvalue()){
				String msg = client.getLocalPlayer().getName()
						+ " got: " + itemName
						+ " x" + item.getQuantity()
						+ " (**" + totalValue + " gp**)"
						+ " from " + source;
				System.out.println(msg);
				DiscordWebhook.send(config.webhookUrl(), msg, totalValue);
			}
		}
	}
}