package de.jakob.lotm.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import de.jakob.lotm.util.BeyonderData;
import de.jakob.lotm.util.helper.PotionRitualsUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Set;

public class GrantRitualCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("grantritual")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.argument("pathway", StringArgumentType.string())
                                .then(Commands.argument("sequence", IntegerArgumentType.integer(1, 9))
                                        .then(Commands.argument("ritual", StringArgumentType.string())
                                                .executes(context -> execute(
                                                        context.getSource(),
                                                        EntityArgument.getPlayer(context, "player"),
                                                        StringArgumentType.getString(context, "pathway"),
                                                        IntegerArgumentType.getInteger(context, "sequence"),
                                                        StringArgumentType.getString(context, "ritual"),
                                                        true
                                                )))))))
        );

        dispatcher.register(Commands.literal("revokeritual")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.argument("pathway", StringArgumentType.string())
                                .then(Commands.argument("sequence", IntegerArgumentType.integer(1, 9))
                                        .then(Commands.argument("ritual", StringArgumentType.string())
                                                .executes(context -> execute(
                                                        context.getSource(),
                                                        EntityArgument.getPlayer(context, "player"),
                                                        StringArgumentType.getString(context, "pathway"),
                                                        IntegerArgumentType.getInteger(context, "sequence"),
                                                        StringArgumentType.getString(context, "ritual"),
                                                        false
                                                )))))))
        );
    }

    private static int execute(CommandSourceStack source, ServerPlayer target, String pathway, int sequence, String ritual, boolean value) {
        if (!BeyonderData.pathways.contains(pathway)) {
            source.sendFailure(Component.literal("Invalid pathway: " + pathway));
            return 0;
        }

        Set<String> rituals = PotionRitualsUtil.getSupportedRituals(pathway, sequence);
        if (rituals.isEmpty()) {
            source.sendFailure(Component.literal("No configurable rituals for " + pathway + " sequence " + sequence));
            return 0;
        }

        if (!PotionRitualsUtil.setRitual(target, pathway, sequence, ritual, value)) {
            source.sendFailure(Component.literal("Unknown ritual '" + ritual + "'. Supported: " + String.join(", ", rituals)));
            return 0;
        }

        source.sendSuccess(() -> Component.literal((value ? "Granted " : "Revoked ")
                + "ritual '" + ritual + "' for " + target.getName().getString()
                + " (" + pathway + " sequence " + sequence + ")"), true);
        return 1;
    }
}
