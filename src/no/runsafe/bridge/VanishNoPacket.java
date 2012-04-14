package no.runsafe.bridge;

import no.runsafe.framework.RunsafePlugin;

public class VanishNoPacket extends RunsafePlugin
{
	@Override
	protected void PluginSetup()
	{
		addComponent(PlayerVanishStatus.class);
	}
}
