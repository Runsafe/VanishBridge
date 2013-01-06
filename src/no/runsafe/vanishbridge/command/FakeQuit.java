package no.runsafe.vanishbridge.command;

import no.runsafe.framework.command.RunsafeAsyncCommand;
import no.runsafe.framework.server.RunsafeServer;
import no.runsafe.framework.server.event.player.RunsafePlayerQuitEvent;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.framework.timer.IScheduler;
import no.runsafe.vanishbridge.PlayerVanishManager;
import org.bukkit.event.player.PlayerQuitEvent;

public class FakeQuit extends RunsafeAsyncCommand
{
	public FakeQuit(IScheduler scheduler, PlayerVanishManager playerVanishManager)
	{
		super("fakequit", scheduler);
		manager = playerVanishManager;
	}

	@Override
	public String requiredPermission()
	{
		return "runsafe.vanish.fakequit";
	}

	@Override
	public String OnExecute(RunsafePlayer player, String[] strings)
	{
		manager.setVanished(player, false);
		RunsafePlayerQuitEvent fake = new RunsafePlayerQuitEvent(new PlayerQuitEvent(player.getRawPlayer(), null));
		fake.Fire();
		manager.setVanished(player, true);
		RunsafeServer.Instance.broadcastMessage(fake.getQuitMessage());
		return null;
	}

	PlayerVanishManager manager;
}
