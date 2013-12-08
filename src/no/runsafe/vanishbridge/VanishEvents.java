package no.runsafe.vanishbridge;

import no.runsafe.framework.internal.wrapper.ObjectWrapper;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.kitteh.vanish.hooks.Hook;

public class VanishEvents extends Hook
{
	public VanishEvents(EventFactory factory)
	{
		super(null);
		this.factory = factory;
	}

	@Override
	public void onUnvanish(Player player)
	{
		factory.Fire(ObjectWrapper.convert((OfflinePlayer) player), false);
	}

	@Override
	public void onVanish(Player player)
	{
		factory.Fire(ObjectWrapper.convert((OfflinePlayer) player), true);
	}

	private final EventFactory factory;
}
