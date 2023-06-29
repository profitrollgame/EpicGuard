package me.xneox.epicguard.core.config.migration;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.List;

import static net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.*;

public final class MiniMessageMigration implements ConfigurationMigration {
    @Override
    public boolean shouldMigrate(final @NotNull ConfigurationNode rootNode) {
        return rootNode.node("version").getInt() == 0;
    }

    @Override
    public void migrate(final @NotNull ConfigurationNode rootNode) throws SerializationException {
        rootNode.node("version").set(1);
        for (final ConfigurationNode node : rootNode.childrenMap().values()) {
            if (node.isList()) {
                migrateList(node);
            } else if (node.isMap()) {
                migrateMap(node);
            } else {
                final String value = node.get(String.class);
                if (value != null) {
                    node.set(String.class, migrateLegacy(value));
                }
            }
        }
    }

    private void migrateList(final @NotNull ConfigurationNode node) throws SerializationException {
        final List<String> original = node.getList(String.class);
        if (original == null) {
            return;
        }

        node.setList(String.class, original.stream()
                .map(st -> st.isBlank() ? st : migrateLegacy(st))
                .toList());
    }

    private void migrateMap(final @NotNull ConfigurationNode node) throws SerializationException {
        for (var childrenNode : node.childrenMap().values()) {
            if (childrenNode.isList()) {
                migrateList(childrenNode);
            } else if (childrenNode.isMap()){
                migrateMap(childrenNode);
            } else {
                final String value = childrenNode.getString();
                if (value != null) {
                    childrenNode.set(String.class, migrateLegacy(value));
                }
            }
        }
    }

    private String migrateLegacy(String string) {
        if (string.indexOf(SECTION_CHAR) != -1) {
            string = string.replace(SECTION_CHAR, AMPERSAND_CHAR);
        }
        if (string.indexOf(AMPERSAND_CHAR) != -1) {
            return MiniMessage.miniMessage().serialize(legacyAmpersand().deserialize(string));
        }
        return string;
    }
}
