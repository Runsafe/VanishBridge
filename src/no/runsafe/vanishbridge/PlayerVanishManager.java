package no.runsafe.vanishbridge;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.hook.IPlayerDataProvider;
import no.runsafe.framework.api.hook.IPlayerVisibility;
import no.runsafe.framework.api.hook.PlayerData;
import no.runsafe.framework.api.log.IDebug;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.log.Console;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import org.bukkit.entity.Player;
import org.kitteh.vanish.VanishManager;
import org.kitteh.vanish.VanishPlugin;

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
	public void GetPlayerData(PlayerData data)
	{
		if (vanishNoPacket == null)
		{
			return;
		}
		data.addData(
			"vanished",
			() ->
			{
				IPlayer player = data.getContext() instanceof IPlayer ? (IPlayer) data.getContext() : null;
				if (player != null && player.shouldNotSee(data.getPlayer()))
				{
					return null;
				}
				Player bukkitPlayer = ObjectUnwrapper.convert(data.getPlayer());
				return bukkitPlayer != null && vanishNoPacket.isVanished(bukkitPlayer)
					? "true"
					: null;
			}
		);
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
