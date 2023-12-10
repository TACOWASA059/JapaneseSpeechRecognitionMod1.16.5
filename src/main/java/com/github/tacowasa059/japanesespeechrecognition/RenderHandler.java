package com.github.tacowasa059.japanesespeechrecognition;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

import static net.minecraft.client.gui.AbstractGui.fill;

@Mod.EventBusSubscriber(modid = JapaneseSpeechRecognition.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class RenderHandler {
    //実行中かどうか
    public static boolean process=false;
    public static String process_text;
    private static final String[] INPUT_ANIMATION = {"▅.. ", ".▅. ", "..▅ "};
    private static int  animationIndex = 0;
    private static int  dt=0;

    @SubscribeEvent
    public static void UpdateInputAnimation(TickEvent.ClientTickEvent event) {
        if(dt>6){
            animationIndex = (animationIndex + 1) % INPUT_ANIMATION.length;
            dt=0;
        }
        else{
            dt++;
        }
    }

    @SubscribeEvent
    public static void onPostRenderGuiOverlayEvent(RenderGameOverlayEvent.Text event) {
        if (Minecraft.getInstance().player == null||!KeyPressSubscriber.executed) return;
        if(Minecraft.getInstance().currentScreen instanceof ChatScreen){
            MatrixStack stack = event.getMatrixStack();
            stack.push();
            int chatWidgetY = event.getWindow().getScaledHeight() - 25;
            if(process){
                extracted(stack, chatWidgetY);
            }
            stack.pop();
        }
    }

    private static void extracted(MatrixStack stack,int chatWidgetY) {
        Minecraft mc=Minecraft.getInstance();
        String pos= TextFormatting.GREEN+INPUT_ANIMATION[animationIndex]+process_text;
        // テキストの位置を計算
        fill(stack,2,chatWidgetY-2,5+mc.fontRenderer.getStringWidth(pos),chatWidgetY+mc.fontRenderer.FONT_HEIGHT,0x80000000);
        renderText(Minecraft.getInstance(),pos, stack,5, chatWidgetY);
    }

    private static void renderText(Minecraft minecraft,String s,MatrixStack stack,float a,float b){
        if (!minecraft.gameSettings.showDebugInfo) {
            minecraft.fontRenderer.drawStringWithShadow(stack,s,a-1,b-1, Color.WHITE.getRGB());
        }
    }

}