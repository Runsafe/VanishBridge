package no.runsafe.vanishbridge;

import no.runsafe.framework.internal.wrapper.ObjectWrapper;
import no.runsafe.framework.minecraft.event.player.RunsafeCustomEvent;
import org.bukkit.entity.Player;

public class EventFactory
{
	public void Fire(Player player, boolean vanished)
	{
		new RunsafeCustomEvent(ObjectWrapper.convert(player), "vanished", vanished).Fire();
	}
}
