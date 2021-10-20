package com.bisxsh.whosthatpixelmon.managers;

import com.bisxsh.whosthatpixelmon.Whosthatpixelmon;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.io.IOException;
import java.net.URISyntaxException;

public class CommandManager {

    public void setupCommand() {
        // /Command to start the chat game
        CommandSpec startChatGame = CommandSpec.builder()
                .description(Text.of("Starts 'Whos that Pixelmon'"))
                .permission("whosthatpixelmon.comamnd.start")
                .executor((CommandSource src, CommandContext args) -> {
                    try {
                        new ChatGameManager().startChatGame();
                        if (src instanceof Player) {
                            Player player = (Player) src;
                            Text txt = Text.builder("Forcibly started 'Whos that Pixelmon'").build();
                            BroadcastManager.getInstance().sendPlayerBroadcast(txt, player);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    return CommandResult.success();
                }).build();
        //

        //Setup the /wtp parent command
        CommandSpec wtp = CommandSpec.builder()
                .description (Text.of("Start 'Whos that Pixelmon'"))
                .permission("whosthatpixelmon.command.start")
                .child(startChatGame, "start")
                .build();
        Sponge.getCommandManager().register(Whosthatpixelmon.getInstance(), wtp, "wtp",  "whosthatpixelmon");
        //
    }
}
