package no.runsafe.vanishbridge;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.features.Commands;
import no.runsafe.framework.features.FrameworkHooks;
import no.runsafe.vanishbridge.command.FakeJoin;
import no.runsafe.vanishbridge.command.FakeQuit;

public class Plugin extends RunsafePlugin
{
	@Override
	protected void pluginSetup()
	{
		// Framework features
		addComponent(Commands.class);
		addComponent(FrameworkHooks.class);

		// Plugin components
		addComponent(PlayerVanishManager.class);
		addComponent(FakeJoin.class);
		addComponent(FakeQuit.class);
		addComponent(VanishEvents.class);
		addComponent(EventFactory.class);
	}
}
