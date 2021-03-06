package tests;

import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.ugens.RangeLimiter;
import synth.auxilliary.ContextProvider;
import synth.osc.SmartOscillator;
import synth.osc.Waveform;

import javax.sound.midi.ShortMessage;
import java.util.concurrent.TimeUnit;

public class MIDISendTest {
    public static void main(String[] args){
        AudioContext ac = ContextProvider.ac();
        ac.start();
        SmartOscillator osc = new SmartOscillator(ac, 0f, Waveform.SAW, 5, 2f, 1);
        RangeLimiter limiter = new RangeLimiter(ac, 2);
        limiter.addInput(osc);
        ac.out.addInput(limiter);
        osc.start();
        try {
            for(int i = 7; i > 0; i--){
                osc.send(new ShortMessage(ShortMessage.NOTE_ON, 105-(12 * i), 100), -1);
                TimeUnit.SECONDS.sleep(3);
            }
        } catch (Exception e){

        }

    }
}
