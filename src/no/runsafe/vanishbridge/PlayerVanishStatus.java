package no.runsafe.vanishbridge;

import no.runsafe.framework.hook.IPlayerDataProvider;
import no.runsafe.framework.hook.IPlayerVisibility;
import no.runsafe.framework.messaging.IMessageBusService;
import no.runsafe.framework.messaging.Message;
import no.runsafe.framework.messaging.MessageBusStatus;
import no.runsafe.framework.messaging.Response;
import no.runsafe.framework.plugin.PluginResolver;
import no.runsafe.framework.server.player.RunsafePlayer;
import org.bukkit.entity.Player;
import org.kitteh.vanish.VanishManager;
import org.kitteh.vanish.VanishPlugin;

import java.util.HashMap;

public class PlayerVanishStatus implements IMessageBusService, IPlayerDataProvider, IPlayerVisibility
{
	public PlayerVanishStatus(PluginResolver resolver)
	{
		vanishNoPacket = resolver.<VanishPlugin>getPlugin("VanishNoPacket").getManager();
	}

	@Override
	public HashMap<String, String> GetPlayerData(RunsafePlayer player)
	{
		if (player.getRawPlayer() != null && vanishNoPacket.isVanished(player.getRawPlayer()))
		{
			HashMap<String, String> response = new HashMap<String, String>();
			response.put("vanished", "true");
			return response;
		}
		return null;
	}

	@Override
	public String getServiceName()
	{
		return "PlayerStatus";
	}

	@Override
	public boolean canPlayerASeeB(RunsafePlayer a, RunsafePlayer b)
	{
		return !vanishNoPacket.isVanished(b.getRawPlayer()) || a.hasPermission("vanish.see");
	}

	@Override
	public boolean isPlayerVanished(RunsafePlayer player)
	{
		return vanishNoPacket.isVanished(player.getRawPlayer());
	}

	@Override
	public Response processMessage(Message message)
	{
		Player player = message.getPlayer().getRawPlayer();
		if (message.getQuestion().equals("get.player.invisibility.on"))
		{
			Response response = new Response();
			if (!vanishNoPacket.isVanished(player))
			{
				response.setStatus(MessageBusStatus.OK);
				response.setResponse("Player is not vanished");
			}
			else
			{
				response.setStatus(MessageBusStatus.NOT_OK);
				response.setResponse("Player is vanished");
			}
			return response;
		}
		if (message.getQuestion().equals("set.player.invisibility.on"))
		{
			if (!vanishNoPacket.isVanished(player))
			{
				vanishNoPacket.toggleVanish(player);
				Response response = new Response();
				response.setStatus(MessageBusStatus.OK);
				response.setResponse("Player has vanished");
				return response;
			}
		}
		if (message.getQuestion().equals("set.player.invisibility.off"))
		{
			if (vanishNoPacket.isVanished(player))
			{
				vanishNoPacket.toggleVanish(player);
				Response response = new Response();
				response.setStatus(MessageBusStatus.OK);
				response.setResponse("Player has appeared");
				return response;
			}
		}
		return null;
	}

	private final VanishManager vanishNoPacket;
}
