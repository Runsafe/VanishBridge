package no.runsafe.vanishbridge;

import no.runsafe.framework.internal.wrapper.ObjectWrapper;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class EventFactory
{
	public void Fire(Player player, boolean vanished)
	{
		new VanishEvent(ObjectWrapper.convert((OfflinePlayer) player), vanished).Fire();
	}
}
