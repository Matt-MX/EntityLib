package me.tofaa.testentitylib;

import com.github.retrooper.packetevents.protocol.attribute.Attributes;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import me.tofaa.entitylib.meta.mobs.passive.SheepMeta;
import me.tofaa.entitylib.wrapper.WrapperLivingEntity;
import net.kyori.adventure.text.Component;
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

            this.sheep.getAttributes().setAttribute(Attributes.SCALE, 0.75);
            // Set base stuff
            this.sheep.consumeEntityMeta(SheepMeta.class, (meta) -> {
                meta.setBaby(true);
                meta.setColor((byte) Color.BLACK.getRGB());
                meta.setCustomName(Component.text("Run /testperplayer again!"));
            });
        } else if (!this.sheep.hasCustomViewerMeta(player.getUniqueId())) {
            this.sheep.consumeEntityMetaViewer(player.getUniqueId(), SheepMeta.class, (meta) -> {
                meta.setCustomName(Component.text("Run it again!"));
                meta.setColor((byte) Color.RED.getRGB());
            });
        } else {
            this.sheep.clearCustomViewerMeta(player.getUniqueId());
        }

        return false;
    }
}
