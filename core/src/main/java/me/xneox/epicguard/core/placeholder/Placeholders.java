package me.xneox.epicguard.core.placeholder;

import io.github.miniplaceholders.api.Expansion;
import me.xneox.epicguard.core.EpicGuardAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;

import static io.github.miniplaceholders.api.utils.Components.*;

public final class Placeholders {
    public static void register() {
        EpicGuardAPI API = EpicGuardAPI.INSTANCE;
        Expansion.builder("epicguard")
                .globalPlaceholder("attack_status", (queue, ctx) -> {
                    final Component STATUS = API.attackManager().isUnderAttack()
                            ? TRUE_COMPONENT
                            : FALSE_COMPONENT;
                    return Tag.selfClosingInserting(STATUS);
                })
                .globalPlaceholder("connections_per_second", (queue, ctx) -> {
                    final int connections = API.attackManager().connectionCounter();
                    return Tag.selfClosingInserting(Component.text(connections));
                })
                .build()
                .register();
    }
}
