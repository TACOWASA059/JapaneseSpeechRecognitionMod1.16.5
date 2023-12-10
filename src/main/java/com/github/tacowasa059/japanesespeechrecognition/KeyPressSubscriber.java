package com.github.tacowasa059.japanesespeechrecognition;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JapaneseSpeechRecognition.MOD_ID,bus= Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class KeyPressSubscriber {
    //on/off
    public static boolean executed=true;
    @SubscribeEvent
    public static void onKeyPress(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.world == null) return;
        if (mc.currentScreen == null && KeyBindSubscriber.keyBinding.isPressed()) {
            executed=!executed;
            if(!SpeechRecognition.loaded){
                Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent(TextFormatting.RED+new TranslationTextComponent("text.JapaneseSpeechRecognition.error").getString()),true);
            }
            else if(executed){
                Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent(new TranslationTextComponent("text.JapaneseSpeechRecognition.status").getString()+ TextFormatting.RED +":ON"),true);
            }else{
                Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent(new TranslationTextComponent("text.JapaneseSpeechRecognition.status").getString()+TextFormatting.BLUE+":OFF"),true);
            }

        }
    }
}
