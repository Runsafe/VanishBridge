package no.runsafe.vanishbridge.command;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerQuitEvent;
import no.runsafe.vanishbridge.PlayerVanishManager;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

public class FakeQuit extends PlayerCommand
{
	public FakeQuit(PlayerVanishManager playerVanishManager, IServer server)
	{
		super("fakequit", "Fakes a quit event and vanishes", "runsafe.vanish.fakequit");
		manager = playerVanishManager;
		this.server = server;
	}

	@Override
	public String OnExecute(IPlayer player, IArgumentList stringStringHashMap)
	{
		manager.setVanished(player, false);
		RunsafePlayerQuitEvent fake = new RunsafePlayerQuitEvent(
			new PlayerQuitEvent((Player) ObjectUnwrapper.convert(player), null)
		);
		fake.Fire();
		manager.setVanished(player, true);
		server.broadcastMessage(fake.getQuitMessage());
		return null;
	}

	final PlayerVanishManager manager;
	private final IServer server;
}
