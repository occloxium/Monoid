/*
 * This file is part of Beads. See http://www.beadsproject.net for all information.
 */
package net.beadsproject.beads.data.buffers;

import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.data.BufferFactory;

/**
 * Creates a {@link Buffer} consisting of a triangle wave in the range [-1,1].
 * 
 * @see Buffer BufferFactory
 * @author ollie
 */
public class TriangleBuffer extends BufferFactory {



    /* (non-Javadoc)
     * @see net.beadsproject.beads.data.BufferFactory#generateBuffer(int)
     */
    public Buffer generateBuffer(int bufferSize) {
    	Buffer b = new Buffer(bufferSize);
        for(int i = 0; i < bufferSize; i++) {
        	if(i < bufferSize / 2) {
        		b.buf[i] = (float)i / ((float)bufferSize / 2f) * 2.0f - 1.0f;
        	} else {
        		b.buf[i] = 1f - ((float)(i - (bufferSize / 2)) / ((float)bufferSize / 2f) * 2.0f - 1.0f);
        	}
        }
    	return b;
    }

    /* (non-Javadoc)
     * @see net.beadsproject.beads.data.BufferFactory#getName()
     */
    public String getName() {
    	return "Triangle";
    }

    
}