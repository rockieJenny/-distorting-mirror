package com.givewaygames.camera.utils;

import android.os.Handler;
import android.os.Message;
import java.util.Vector;

public abstract class PauseHandler extends Handler {
    final Vector<Message> messageQueueBuffer = new Vector();
    private boolean paused;

    protected abstract void processMessage(Message message);

    public final void resume() {
        this.paused = false;
        while (this.messageQueueBuffer.size() > 0) {
            Message msg = (Message) this.messageQueueBuffer.elementAt(0);
            this.messageQueueBuffer.removeElementAt(0);
            sendMessage(msg);
        }
    }

    public final void pause() {
        this.paused = true;
    }

    protected boolean storeMessage(Message message) {
        return true;
    }

    public final void handleMessage(Message msg) {
        if (!this.paused) {
            processMessage(msg);
        } else if (storeMessage(msg)) {
            Message msgCopy = new Message();
            msgCopy.copyFrom(msg);
            this.messageQueueBuffer.add(msgCopy);
        }
    }
}
