package tests.sounds;

import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import synth.filter.models.BiquadFilter;
import synth.filter.Filter;
import synth.osc.SmartOscillator;
import tests.ContextProvider;

public class FilterTest {
    public static void main(String[] args){
        AudioContext ac = ContextProvider.ac();
        ac.start();
        SmartOscillator osc = new SmartOscillator(ac, 55f, Buffer.SAW);
        osc.setFrequency(55f);


        Filter filter = new Filter(ac, BiquadFilter.BUTTERWORTH_LP, 220, 24, 0.33f);

        filter.addInput(osc);

        ac.out.addInput(filter);
    }
}