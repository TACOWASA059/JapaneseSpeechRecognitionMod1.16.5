package com.github.tacowasa059.japanesespeechrecognition;

import com.github.tacowasa059.japanesespeechrecognition.JapaneseSpeechRecognition;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.io.IOException;

import static com.github.tacowasa059.japanesespeechrecognition.SpeechRecognition.Recognize;

@Mod.EventBusSubscriber(modid = JapaneseSpeechRecognition.MOD_ID,bus= Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class KeyBindSubscriber {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        register(event);
    }
    public static KeyBinding keyBinding;
    public static void register(final FMLClientSetupEvent event){
        keyBinding=keybindCreate("setting", 299);
        ClientRegistry.registerKeyBinding(keyBinding);

    }
    private static KeyBinding keybindCreate(String name ,int key){
        return new KeyBinding("key."+ JapaneseSpeechRecognition.MOD_ID+"."+name,key,"key.category."+JapaneseSpeechRecognition.MOD_ID);
    }
}
