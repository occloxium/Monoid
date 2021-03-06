package synth.modulation;

import net.beadsproject.beads.core.AudioContext;
import synth.container.Device;

/**
 * A basic envelope wrapping class.
 */
public class Envelope extends Modulator implements Modulatable {

    /** attack time in milliseconds */
    protected int attack;

    /** decay time in milliseconds */
    protected int decay;

    /** sustain level in [0,1] */
    protected float sustain;

    /** release time in milliseconds */
    protected int release;

    /** Audio context */
    protected AudioContext context;

    /** Backend Envelope UGen */
    protected net.beadsproject.beads.ugens.Envelope current;

    /** Default envelope */
    public Envelope(AudioContext ac){
        this(ac,5, 0, 1f, 20);
    }

    /**
     * AR-Envelope
     * @param attack attack time
     * @param release release time
     */
    public Envelope(AudioContext ac, int attack, int release){
        this(ac, attack, 0, 1f, release);
    }

    /**
     * ADSR-Envelope
     * @param attack attack time
     * @param decay decay time
     * @param sustain sustain level
     * @param release release time
     */
    public Envelope(AudioContext ac, int attack, int decay, float sustain, int release){
        super(ac);
        this.context = ac;
        this.attack = attack;
        this.decay = decay;
        this.sustain = sustain;
        this.release = release;
        current = new net.beadsproject.beads.ugens.Envelope(this.context);
        this.outputInitializationRegime = OutputInitializationRegime.ZERO;
        this.outputPauseRegime = OutputPauseRegime.ZERO;
    }

    /**
     * Gets the attack time in [ms]
     * @return attack time
     */
    public int attack() {
        return attack;
    }

    /**
     * Sets the attack time
     * @param attack attack time in ms
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }
    
    /**
     * Gets the decay time in [ms]
     * @return decay time
     */
    public int decay() {
        return decay;
    }
    /**
     * Sets the decay time in [ms]
     * @param decay decay time
     */
    public void setDecay(int decay) {
        this.decay = decay;
    }

    /**
     * Gets the sustain level
     * @return sustain level
     */
    public float sustain() {
        return sustain;
    }
    /**
     * Sets the sustain level
     * @param sustain decay time
     */
    public void setSustain(float sustain) {
        this.sustain = sustain;
        this.current.setValue(sustain);
    }
    /**
     * Gets the release time in [ms]
     * @return release time
     */
    public int release() {
        return release;
    }
    /**
     * Sets the release time in [ms]
     * @param release release time
     */
    public void setRelease(int release) {
        this.release = release;
    }

    @Override
    public void calculateBuffer(){
        current.update();
        for(int i = 0; i < bufferSize; i++){
            bufOut[0][i] = modulationStrength * current.getValue(0, i) + centerValue;
        }
    }

    public float getValue(){
        return this.centerValue;
    }

    public Envelope clone(){
        Envelope e = new Envelope(this.ac, this.attack, this.decay, this.sustain, this.release);
        return (Envelope)(e.setModulationStrength(this.modulationStrength).setModulationMode(this.modulationMode).setCenterValue(this.centerValue));
    }

    /**
     * Method for {@link Device} when a send for MIDI data with noteOn command appears to happen
     */
    public void noteOn(){
        this.current.setValue(0);
        this.current.clear();
        this.current.addSegment(1f, this.attack);
        this.current.addSegment(this.sustain, this.decay);
    }
    /**
     * Method for {@link Device} when a send for MIDI data with noteOff command appears to happen
     */
    public void noteOff(){
        this.current.addSegment(0f, this.release);
    }

    public NormEnvelope normalize(){
        return new NormEnvelope(ac, this.attack, this.decay, this.sustain, this.release);
    }
}
