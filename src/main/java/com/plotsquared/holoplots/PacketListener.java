package com.plotsquared.holoplots;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.plotsquared.core.util.task.TaskManager;
import com.plotsquared.core.util.task.TaskTime;
import org.bukkit.entity.Player;

public class PacketListener {

    public PacketListener() {
        final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketAdapter.AdapterParameteters mapChunkParam = new PacketAdapter.AdapterParameteters().optionAsync()
                .types(PacketType.Play.Server.MAP_CHUNK).listenerPriority(ListenerPriority.NORMAL).plugin(
                        HoloPlotsPlugin.THIS);
        protocolManager.addPacketListener(new PacketAdapter(mapChunkParam) {
            @Override
            public void onPacketSending(final PacketEvent packetEvent) {
                final PacketContainer packetContainer = packetEvent.getPacket();
                final int x = packetContainer.getIntegers().read(0);
                final int z = packetContainer.getIntegers().read(1);
                final Player player = packetEvent.getPlayer();
                final ChunkWrapper chunk = new ChunkWrapper(x, z, player.getWorld().getName());
                TaskManager.getPlatformImplementation().taskLater(() -> HoloPlotsPlugin.HOLO.updatePlayer(player, chunk), TaskTime
                        .ticks(20));
            }
        });
    }

}
