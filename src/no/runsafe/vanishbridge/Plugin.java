package no.runsafe.vanishbridge;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.vanishbridge.command.FakeJoin;
import no.runsafe.vanishbridge.command.FakeQuit;

public class Plugin extends RunsafePlugin
{
	@Override
	protected void PluginSetup()
	{
		addComponent(PlayerVanishManager.class);
		addComponent(FakeJoin.class);
		addComponent(FakeQuit.class);
	}
}
