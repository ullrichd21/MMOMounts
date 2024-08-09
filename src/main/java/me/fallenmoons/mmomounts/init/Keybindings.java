package me.fallenmoons.mmomounts.init;

import com.mojang.blaze3d.platform.InputConstants;
import me.fallenmoons.mmomounts.Mmomounts;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.checkerframework.checker.units.qual.K;

public class Keybindings {
    public static final Keybindings INSTANCE = new Keybindings();

    private Keybindings() {
        // This is a private constructor to prevent external instantiation
    }

    private static final String CATEGORY = "key.categories." + Mmomounts.MODID;

    public final KeyMapping toggleMount = new KeyMapping(
            "key." + Mmomounts.MODID + ".toggle_mount",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM.getOrCreate(InputConstants.KEY_X),
            CATEGORY
    );
}
