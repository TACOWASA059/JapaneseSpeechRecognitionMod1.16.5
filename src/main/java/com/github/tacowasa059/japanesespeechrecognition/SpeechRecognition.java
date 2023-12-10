package com.github.tacowasa059.japanesespeechrecognition;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import org.json.JSONObject;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SpeechRecognition {
    //読み込みができたかどうか
    public static boolean loaded=false;
     public  static void Recognize() throws IOException {

        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 60000, 16, 2, 4, 44100, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        TargetDataLine microphone;
        String resourceLocation="mods/JapaneseModel";
        try (Model model = new Model(resourceLocation);
             Recognizer recognizer = new Recognizer(model, 120000)) {
            try {

                microphone = (TargetDataLine) AudioSystem.getLine(info);
                microphone.open(format);
                microphone.start();
                loaded=true;

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int numBytesRead;
                int CHUNK_SIZE = 1024;
                int bytesRead = 0;
                byte[] b = new byte[4096];

                while (bytesRead <= 1000) {
                        numBytesRead = microphone.read(b, 0, CHUNK_SIZE);

                        out.write(b, 0, numBytesRead);

                        String str="";
                        if (recognizer.acceptWaveForm(b, numBytesRead)) {
                            str=recognizer.getResult();
                            output(str);
                            RenderHandler.process=false;
                            RenderHandler.process_text="";
                        } else {
                            str=recognizer.getPartialResult();
                            JSONObject jsonObject = new JSONObject(str);
                            str=jsonObject.getString("partial");
                            if(str.length()>0){
                                RenderHandler.process=true;
                                RenderHandler.process_text=str.replaceAll(" ","");
                            }
                        }
                }
                microphone.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //inputFieldに反映する
    private static void output(String str){
        if(Minecraft.getInstance()==null)return;
        if(str.length()>0&&Minecraft.getInstance().currentScreen instanceof ChatScreen){
            JSONObject jsonObject = new JSONObject(str);
            ChatScreen screen = (ChatScreen) Minecraft.getInstance().currentScreen;
            if(!screen.inputField.getText().startsWith("/")&&KeyPressSubscriber.executed) screen.inputField.setText(screen.inputField.getText()+jsonObject.getString("text").replaceAll(" ",""));
        }
    }
}
