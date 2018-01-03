package synth.controller;

import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.ugens.Clock;
import synth.midi.MidiFileReader;
import synth.osc.Oscillator;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

public class MidiOscController {
    /**The controlled oscillator */
    Oscillator osc;

    /**The MidiFileReader*/
    MidiFileReader reader;

    /** Clock, for getting the midi ticks right*/
    Clock clock;

    /**Boolean for starting, stopping and pausing MidiOscController */
    private boolean isActive = false;

    /**Boolean for getting information whether the MidiOscController is paused or not */
    private boolean isPaused = false;

    /**
     * Basic Constructor
     * @param osc The controlled Oscillator
     * @param midiFile The controlling midi file
     * @param ac The AudioContext for the clock
     */
    public MidiOscController(Oscillator osc, File midiFile, AudioContext ac) {
        this.osc = osc;
        this.reader = new MidiFileReader(midiFile, osc);
        //setup clock
        this.clock = new Clock(ac);
        this.clock.setClick(false);
        //sets clock ticks per minute to the delta ticks in midi file
        this.clock.setTicksPerBeat(this.reader.getDeltaTicks());

    }

    //TODO implement method for starting the oscillator playing the midi file
    public void start() {
        setActive(true);
        setPaused(false);
    }

    //TODO implment method for pausing the audio
    public void pause() {
        setActive(false);
        setPaused(true);
    }

    //TODO implement method for stopping the audio
    public void stop() {
        setActive(false);
        setPaused(false);
    }

    /**
     * Setter for isActive
     * @param b New Value of isActive
     */
    private void setActive(boolean b) {
        this.isActive = b;
    }

    /**
     * Getter for isActive
     * @return value of isActive
     */
    public boolean getActive() {
        return this.isActive;
    }

    /**
     * Setter for isPaused
     * @param b new Value of isPaused
     */
    private void setPaused(boolean b) {
        isPaused = b;
    }

    /**
     * Getter for is paused
     * @return the current value of isPaused
     */
     public boolean getPaused() {
        return isPaused;
     }

    //TODO implement method for selecting method performing the next command
    public void control(int[] command) {
        //first, process usual midi signals
        if(command[1] >= 128) {
            int currCommand = command[1] & 0b11110000;
            int currChannel = command[1] & 0b00001111;
            switch(currCommand) {
                //note off
                case 0b10000000: noteOff(this.osc, currChannel, command[2], command[3]);
                case 0b10010000: noteOn(this.osc, currChannel, command[2], command[3]);
                case 0b10100000: releaseNote(this.osc, currChannel, command[2], command[3]);
                case 0b10110000: controlChange(this.osc, currChannel, command[2], command[3]);
                case 0b11000000: programChange(this.osc, currChannel, command[2]);
                case 0b11010000: channelAfterTouch(this.osc, currChannel, command[2]);
                case 0b11100000: pitchWheelChange(this.osc, currChannel, command[2], command[3]);
            }
        }
    }

    public void startPerformance() {
        ArrayList<LinkedList<int[]>> tracks = reader.getTrackLists();
        for(LinkedList<int[]> track: tracks) {
            perform(track);
        }
    }

    public void perform(LinkedList<int[]> trackCommands) {

    }

    public void wait(int deltatime) {
         long currClockVal = this.clock.getCount();
         while(this.clock.getCount() != currClockVal + deltatime) {
             //no op
         }
    }

    /* ---------------------------------------------------------------------------------------------------------------
    * Methods for controlling the oscillator
    -----------------------------------------------------------------------------------------------------------------*/

    public void noteOff(Oscillator osc, int channel, int note, int velocity) {

    }

    public void noteOn(Oscillator osc, int channel, int note, int velocity) {

    }

    //german: abklingen
    public void releaseNote(Oscillator osc, int channel, int note, int velocity) {

    }

    public void controlChange(Oscillator osc, int channel, int controllerNumber, int velocity) {

    }

    public void programChange(Oscillator osc, int channel, int newPatchNumber) {

    }

    public void channelAfterTouch(Oscillator osc, int channel, int channelNumber) {

    }

    public void pitchWheelChange(Oscillator osc, int channel, int bottom, int top) {

    }

}
