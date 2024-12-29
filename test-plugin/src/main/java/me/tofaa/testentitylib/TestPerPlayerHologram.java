package me.tofaa.testentitylib;

import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.util.Vector3f;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import me.tofaa.entitylib.meta.display.AbstractDisplayMeta;
import me.tofaa.entitylib.meta.display.TextDisplayMeta;
import me.tofaa.entitylib.wrapper.WrapperEntity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TestPerPlayerHologram extends BukkitCommand {
    private @Nullable WrapperEntity hologram = null;

    protected TestPerPlayerHologram() {
        super("testperplayerhologram");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }

        final Player player = (Player) commandSender;

        if (this.hologram == null) {
            this.hologram = new WrapperEntity(EntityTypes.TEXT_DISPLAY);

            // Set base stuff
            this.hologram.consumeEntityMeta(TextDisplayMeta.class, (meta) -> {
                meta.setText(Component.text("Run me again").color(NamedTextColor.RED));
                meta.setBackgroundColor(Color.RED.setAlpha(80).asARGB());
                meta.setShadow(true);
                meta.setBillboardConstraints(AbstractDisplayMeta.BillboardConstraints.CENTER);
                meta.setScale(new Vector3f(1f, 1f, 1f));
                meta.setPositionRotationInterpolationDuration(2);
                meta.setTransformationInterpolationDuration(2);
                meta.setInterpolationDelay(0);
            });

            this.hologram.spawn(SpigotConversionUtil.fromBukkitLocation(player.getLocation()));

            Bukkit.getOnlinePlayers()
                .stream()
                .map(Player::getUniqueId)
                .forEach(hologram::addViewer);

        } else if (!this.hologram.hasCustomViewerMeta(player.getUniqueId())) {
            this.hologram.consumeEntityMetaViewer(player.getUniqueId(), TextDisplayMeta.class, (meta) -> {
                meta.setText(Component.text("Meow :3").color(NamedTextColor.GREEN));
                meta.setBackgroundColor(Color.GREEN.setAlpha(80).asARGB());
                meta.setShadow(true);
                meta.setScale(new Vector3f(1.2f, 1.2f, 100f));
            });
        } else {
            this.hologram.clearCustomViewerMeta(player.getUniqueId());
        }

        return false;
    }
}
