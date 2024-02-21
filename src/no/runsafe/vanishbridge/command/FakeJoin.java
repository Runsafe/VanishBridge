package no.runsafe.vanishbridge.command;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerJoinEvent;
import no.runsafe.vanishbridge.PlayerVanishManager;
import org.bukkit.event.player.PlayerJoinEvent;

public class FakeJoin extends PlayerCommand
{
	public FakeJoin(PlayerVanishManager playerVanishManager, IServer server)
	{
		super("fakejoin", "Unvanishes and fakes a join event", "runsafe.vanish.fakequit");
		manager = playerVanishManager;
		this.server = server;
	}

	@Override
	public String OnExecute(IPlayer player, IArgumentList stringStringHashMap)
	{
		manager.setVanished(player, false);
		RunsafePlayerJoinEvent fake = new RunsafePlayerJoinEvent(
			new PlayerJoinEvent(ObjectUnwrapper.convert(player), null)
		);
		fake.Fire();
		server.broadcastMessage(fake.getJoinMessage());
		return null;
	}

	final PlayerVanishManager manager;
	private final IServer server;
}
