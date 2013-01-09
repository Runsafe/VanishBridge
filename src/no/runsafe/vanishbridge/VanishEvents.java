package no.runsafe.vanishbridge;

import no.runsafe.framework.plugin.PluginResolver;
import no.runsafe.framework.server.ObjectWrapper;
import no.runsafe.framework.server.event.player.RunsafeCustomEvent;
import org.bukkit.entity.Player;
import org.kitteh.vanish.hooks.Hook;

public class VanishEvents extends Hook
{
	public VanishEvents()
	{
		super(null);
	}

	@Override
	public void onUnvanish(Player player)
	{
		new RunsafeCustomEvent(ObjectWrapper.convert(player), "vanished", false).Fire();
	}

	@Override
	public void onVanish(Player player)
	{
		new RunsafeCustomEvent(ObjectWrapper.convert(player), "vanished", true).Fire();
	}
}