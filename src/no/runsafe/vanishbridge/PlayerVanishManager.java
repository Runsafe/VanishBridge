package no.runsafe.vanishbridge;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.hook.IPlayerDataProvider;
import no.runsafe.framework.api.hook.IPlayerVisibility;
import no.runsafe.framework.api.log.IDebug;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.log.Console;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import org.bukkit.entity.Player;
import org.kitteh.vanish.VanishManager;
import org.kitteh.vanish.VanishPlugin;

import java.util.HashMap;

public class PlayerVanishManager implements IPlayerDataProvider, IPlayerVisibility
{
	public PlayerVanishManager(VanishEvents hook, IServer server, IDebug debug)
	{
		this.debug = debug;
		VanishPlugin plugin = server.getPlugin("VanishNoPacket");
		if (plugin == null)
		{
			Console.Global().logWarning("VanishNoPacket not loaded");
			vanishNoPacket = null;
			return;
		}
		vanishNoPacket = plugin.getManager();
		plugin.getHookManager().registerHook("runsafe", hook);
	}

	@Override
	public HashMap<String, String> GetPlayerData(IPlayer player)
	{
		if (vanishNoPacket == null)
		{
			return null;
		}
		Player bukkitPlayer = ObjectUnwrapper.convert(player);
		if (bukkitPlayer != null && vanishNoPacket.isVanished(bukkitPlayer))
		{
			HashMap<String, String> response = new HashMap<>();
			response.put("vanished", "true");
			return response;
		}
		return null;
	}

	@Override
	public boolean isPlayerHidden(IPlayer viewer, IPlayer target)
	{
		if (vanishNoPacket == null)
		{
			return false;
		}
		// DEBUG
		boolean hasPermission = viewer.hasPermission("vanish.see");
		Player player = ObjectUnwrapper.convert(target);
		if (player == null)
			return true;
		boolean isVanished = vanishNoPacket.isVanished(player);
		debug.debugInfo(hasPermission ? "The viewer has override perms" : "The viewer does not have override perms");
		debug.debugInfo(isVanished ? "The target is not vanished" : "The target is vanished");
		// DEBUG END

		return isVanished && !hasPermission;
	}

	@Override
	public boolean isPlayerVanished(IPlayer player)
	{
		if (vanishNoPacket == null)
		{
			return false;
		}
		Player bukkitPlayer = ObjectUnwrapper.convert(player);
		return bukkitPlayer != null && vanishNoPacket.isVanished(bukkitPlayer);
	}

	public void setVanished(IPlayer player, boolean vanished)
	{
		if (vanishNoPacket == null)
		{
			return;
		}
		Player bukkitPlayer = ObjectUnwrapper.convert(player);
		if (bukkitPlayer != null && vanishNoPacket.isVanished(bukkitPlayer) != vanished)
			vanishNoPacket.toggleVanish(bukkitPlayer);
	}

	private final VanishManager vanishNoPacket;
	private final IDebug debug;
}
