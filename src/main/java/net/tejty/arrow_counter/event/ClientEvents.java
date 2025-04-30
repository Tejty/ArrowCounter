package net.tejty.arrow_counter.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tejty.arrow_counter.ArrowCounter;
import net.tejty.arrow_counter.client.ArrowCounterOverlay;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = ArrowCounter.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {

        //Overlays
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            ArrowCounterOverlay.init();
            event.registerAbove(VanillaGuiOverlay.FROSTBITE.id(), "arrow", ArrowCounterOverlay.HUD_INSTANCE);
        }
    }
}
