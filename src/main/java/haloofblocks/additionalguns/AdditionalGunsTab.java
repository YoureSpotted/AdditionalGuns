package haloofblocks.additionalguns;

import haloofblocks.additionalguns.common.item.AdditionalGunItem;
import haloofblocks.additionalguns.core.registry.ItemRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;


@Mod.EventBusSubscriber(modid = AdditionalGuns.ID, value = Dist.CLIENT)
public class AdditionalGunsTab {

    public static void registerCreativeTab(IEventBus bus)
    {
        DeferredRegister<CreativeModeTab> register = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AdditionalGuns.ID);
        CreativeModeTab.Builder builder = CreativeModeTab.builder();
        builder.title(Component.translatable("creativetab.additionalguns"));
        builder.icon(() -> {
            ItemStack stack = new ItemStack(ItemRegistry.ACE_OF_SPADES.get());
            stack.getOrCreateTag().putBoolean("IgnoreAmmo", true);
            return stack;
        });
        builder.displayItems((flags, output) ->
        {
            ItemRegistry.ITEMS.getEntries().forEach(RegistryObject ->
            {
                if (RegistryObject.get() instanceof AdditionalGunItem gunItem) {
                    ItemStack stack = new ItemStack(gunItem);
                    stack.getOrCreateTag().putInt("AmmoCount", gunItem.getGun().getGeneral().getMaxAmmo());
                    output.accept(stack);
                    return;
                }
                output.accept(RegistryObject.get());
            });
        });
        register.register("fuckyou", builder::build);
        register.register(bus);
    }
}
