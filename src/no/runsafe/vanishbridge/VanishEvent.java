package no.runsafe.vanishbridge;

import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.event.player.RunsafeCustomEvent;

public class VanishEvent extends RunsafeCustomEvent
{
	public VanishEvent(IPlayer player, boolean vanished)
	{
		super(player, "vanished");
		this.vanished = vanished;
	}

	public boolean hasVanished()
	{
		return vanished;
	}

	@Override
	public Object getData()
	{
		return vanished;
	}

	private final boolean vanished;
}
