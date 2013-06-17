package no.runsafe.vanishbridge;

import no.runsafe.framework.minecraft.event.player.RunsafeCustomEvent;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class VanishEvent extends RunsafeCustomEvent
{
	public VanishEvent(RunsafePlayer player, boolean vanished)
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
