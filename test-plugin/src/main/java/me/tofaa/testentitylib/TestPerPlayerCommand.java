package me.tofaa.testentitylib;

import com.github.retrooper.packetevents.protocol.attribute.Attributes;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.util.Vector3f;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import me.tofaa.entitylib.meta.display.AbstractDisplayMeta;
import me.tofaa.entitylib.meta.display.TextDisplayMeta;
import me.tofaa.entitylib.meta.mobs.passive.SheepMeta;
import me.tofaa.entitylib.wrapper.WrapperEntity;
import me.tofaa.entitylib.wrapper.WrapperLivingEntity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class TestPerPlayerCommand extends BukkitCommand {

    private @Nullable WrapperLivingEntity sheep = null;

    protected TestPerPlayerCommand() {
        super("testperplayer");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }

        final Player player = (Player) commandSender;

        if (this.sheep == null) {
            this.sheep = new WrapperLivingEntity(EntityTypes.SHEEP);

            // Set base stuff
            this.sheep.consumeEntityMeta(SheepMeta.class, (meta) -> {
                meta.setBaby(true);
                meta.setColor((byte) Color.BLACK.getRGB());
                meta.setCustomName(net.kyori.adventure.text.Component.text("Run /testperplayer again!"));
            });

            this.sheep.spawn(SpigotConversionUtil.fromBukkitLocation(player.getLocation()));
            this.sheep.getAttributes().setAttribute(Attributes.SCALE, 0.5);

            Bukkit.getOnlinePlayers()
                .stream()
                .map(Player::getUniqueId)
                .forEach(sheep::addViewer);

            this.sheep.getAttributes().refresh();

        } else if (!this.sheep.hasCustomViewerMeta(player.getUniqueId())) {
            this.sheep.getViewerTracker().<WrapperLivingEntity>consumeEntity(player.getUniqueId(), (entity) -> {
                entity.consumeEntityMeta(SheepMeta.class, (meta) -> {
                    meta.setCustomName(Component.text("Run it again!"));
                    meta.setColor((byte) Color.RED.getRGB());
                    meta.setBaby(false);
                });

                entity.getAttributes().setAttribute(Attributes.SCALE, 3.0);
            });
        } else {
            this.sheep.clearCustomViewerMeta(player.getUniqueId());
        }

        return false;
    }
}
