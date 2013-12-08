package no.runsafe.vanishbridge;

import no.runsafe.framework.api.player.IPlayer;

public class EventFactory
{
	public void Fire(IPlayer player, boolean vanished)
	{
		new VanishEvent(player, vanished).Fire();
	}
}
