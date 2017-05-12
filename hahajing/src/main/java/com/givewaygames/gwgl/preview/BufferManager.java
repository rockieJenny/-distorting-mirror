package com.givewaygames.gwgl.preview;

import android.hardware.Camera.Size;
import java.util.ArrayList;

public class BufferManager {
    ArrayList<byte[]> addingBuffers;
    int bufferSize = 0;
    ArrayList<byte[]> buffers;
    boolean destroyed = false;
    long lastReadId = 0;
    int lastWriteIdx = -1;
    Size previewSize;
    long uniqueId = 0;

    public int getBufferSize() {
        return this.bufferSize;
    }

    public void destroy() {
        this.destroyed = true;
    }

    public boolean isDestroyed() {
        return this.destroyed;
    }

    public Size getPreviewSize() {
        return this.previewSize;
    }

    public void addBuffer(byte[] buffer) {
        if (this.addingBuffers == null) {
            this.addingBuffers = new ArrayList();
        }
        this.bufferSize = buffer.length;
        this.addingBuffers.add(buffer);
    }

    public void doneAddingBuffers(Size previewSize) {
        this.uniqueId = 0;
        this.lastReadId = 0;
        this.previewSize = previewSize;
        this.buffers = this.addingBuffers;
        this.addingBuffers = null;
    }

    public int setBufferForWrite(byte[] buffer) {
        if (this.destroyed || this.buffers == null) {
            return -1;
        }
        for (int i = 0; i < this.buffers.size(); i++) {
            if (buffer == this.buffers.get(i)) {
                this.lastWriteIdx = i;
                this.uniqueId++;
                return i;
            }
        }
        return -1;
    }

    public byte[] getBufferForRead() {
        if (this.destroyed || this.buffers == null || this.lastWriteIdx == -1 || this.uniqueId == this.lastReadId) {
            return null;
        }
        this.lastReadId = this.uniqueId;
        return (byte[]) this.buffers.get(this.lastWriteIdx);
    }
}
