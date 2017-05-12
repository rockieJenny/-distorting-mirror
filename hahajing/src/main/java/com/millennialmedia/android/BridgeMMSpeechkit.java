package com.millennialmedia.android;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.URLUtil;
import com.millennialmedia.android.NVASpeechKit.CustomWordsOp;
import java.io.File;
import java.util.Map;

public class BridgeMMSpeechkit extends MMJSObject implements OnCompletionListener, PeriodicListener {
    private static final String TAG = "BridgeMMSpeechkit";
    private String ADD_CUSTOM_VOICE_WORDS = "addCustomVoiceWords";
    private String CACHE_AUDIO = "cacheAudio";
    private String DELETE_CUSTOM_VOICE_WORDS = "deleteCustomVoiceWords";
    private String END_RECORDING = "endRecording";
    private String GET_SESSION_ID = "getSessionId";
    private String PLAY_AUDIO = "playAudio";
    private String RELEASE_VOICE = "releaseVoice";
    private String SAMPLE_BACKGROUND_AUDIO_LEVEL = "sampleBackgroundAudioLevel";
    private String START_RECORDING = "startRecording";
    private String STOP_AUDIO = "stopAudio";
    private String TEXT_TO_SPEECH = "textToSpeech";

    private static class SingletonHolder {
        public static final SpeechKitHolder INSTANCE = new SpeechKitHolder();

        private SingletonHolder() {
        }
    }

    private static class SpeechKitHolder {
        private NVASpeechKit _speechKit;

        private SpeechKitHolder() {
        }

        public boolean release() {
            if (this._speechKit == null) {
                return false;
            }
            ThreadUtils.execute(new Runnable() {
                public void run() {
                    synchronized (SpeechKitHolder.this) {
                        if (SpeechKitHolder.this._speechKit != null) {
                            SpeechKitHolder.this._speechKit.cancelRecording();
                            SpeechKitHolder.this._speechKit.release();
                            SpeechKitHolder.this._speechKit = null;
                        }
                    }
                }
            });
            return true;
        }

        public NVASpeechKit getSpeechKit() {
            return this._speechKit;
        }

        public void setSpeechKit(NVASpeechKit speechKit) {
            this._speechKit = speechKit;
        }
    }

    BridgeMMSpeechkit() {
    }

    MMJSResponse executeCommand(String name, Map<String, String> arguments) {
        if (this.START_RECORDING.equals(name)) {
            return startRecording(arguments);
        }
        if (this.END_RECORDING.equals(name)) {
            return endRecording(arguments);
        }
        if (this.CACHE_AUDIO.equals(name)) {
            return cacheAudio(arguments);
        }
        if (this.GET_SESSION_ID.equals(name)) {
            return getSessionId(arguments);
        }
        if (this.PLAY_AUDIO.equals(name)) {
            return playAudio(arguments);
        }
        if (this.TEXT_TO_SPEECH.equals(name)) {
            return textToSpeech(arguments);
        }
        if (this.STOP_AUDIO.equals(name)) {
            return stopAudio(arguments);
        }
        if (this.SAMPLE_BACKGROUND_AUDIO_LEVEL.equals(name)) {
            return sampleBackgroundAudioLevel(arguments);
        }
        if (this.RELEASE_VOICE.equals(name)) {
            return releaseVoice(arguments);
        }
        if (this.ADD_CUSTOM_VOICE_WORDS.equals(name)) {
            return addCustomVoiceWords(arguments);
        }
        if (this.DELETE_CUSTOM_VOICE_WORDS.equals(name)) {
            return deleteCustomVoiceWords(arguments);
        }
        return null;
    }

    private NVASpeechKit getCreateSpeechKit() {
        NVASpeechKit speechKit = null;
        MMWebView webView = (MMWebView) this.mmWebViewRef.get();
        if (webView != null && webView.allowSpeechCreationCommands()) {
            if (getSpeechKitInternal() != null) {
                return getSpeechKitInternal();
            }
            Context context = webView.getContext();
            if (context != null) {
                speechKit = new NVASpeechKit(webView);
                setSpeechKit(speechKit);
                NuanceCredentials credentials = HandShake.sharedHandShake(context).nuanceCredentials;
                if (credentials != null) {
                    speechKit.initialize(credentials, context.getApplicationContext());
                }
            }
        }
        return speechKit;
    }

    private NVASpeechKit getSpeechKit() {
        MMWebView webView = (MMWebView) this.mmWebViewRef.get();
        if (webView == null || !webView.allowRecordingCommands()) {
            return null;
        }
        return getSpeechKitInternal();
    }

    private NVASpeechKit getSpeechKitRelease() {
        return getSpeechKitInternal();
    }

    public MMJSResponse startRecording(Map<String, String> arguments) {
        NVASpeechKit speechKit = getCreateSpeechKit();
        if (speechKit == null) {
            return MMJSResponse.responseWithError("Unable to create speech kit");
        }
        String language = (String) arguments.get("language");
        if (TextUtils.isEmpty(language)) {
            language = "en_GB";
        }
        if (speechKit.startRecording(language)) {
            return MMJSResponse.responseWithSuccess();
        }
        return MMJSResponse.responseWithError("Failed in speechKit");
    }

    public MMJSResponse endRecording(Map<String, String> map) {
        NVASpeechKit speechKit = getSpeechKit();
        if (speechKit == null) {
            return MMJSResponse.responseWithError("Unable to get speech kit");
        }
        synchronized (speechKit) {
            if (speechKit.endRecording()) {
                MMJSResponse responseWithSuccess = MMJSResponse.responseWithSuccess();
                return responseWithSuccess;
            }
            return MMJSResponse.responseWithError("Failed in speechKit");
        }
    }

    public MMJSResponse cacheAudio(Map<String, String> arguments) {
        String url = (String) arguments.get("url");
        if (!URLUtil.isValidUrl(url)) {
            return MMJSResponse.responseWithError("Invalid url");
        }
        if (this.contextRef != null) {
            Context context = (Context) this.contextRef.get();
            if (context != null && AdCache.downloadComponentExternalStorage(url, url.substring(url.lastIndexOf("/") + 1), context)) {
                injectJavascript("javascript:MMJS.sdk.audioCached()");
                return MMJSResponse.responseWithSuccess("Successfully cached audio at " + url);
            }
        }
        return MMJSResponse.responseWithError("Failed to cache audio at" + url);
    }

    public MMJSResponse getSessionId(Map<String, String> map) {
        NVASpeechKit speechKit = getSpeechKit();
        if (speechKit == null) {
            return MMJSResponse.responseWithError("No SpeechKit session open.");
        }
        String sessionId = speechKit.getSessionId();
        if (TextUtils.isEmpty(sessionId)) {
            return MMJSResponse.responseWithError("No SpeechKit session open.");
        }
        return MMJSResponse.responseWithSuccess(sessionId);
    }

    public MMJSResponse playAudio(Map<String, String> arguments) {
        if (getCreateSpeechKit() == null) {
            return MMJSResponse.responseWithError("Unable to create speech kit");
        }
        if (!URLUtil.isValidUrl((String) arguments.get("url"))) {
            return MMJSResponse.responseWithError("Invalid url");
        }
        String path = (String) arguments.get("url");
        if (!TextUtils.isEmpty(path)) {
            Context context = (Context) this.contextRef.get();
            if (context != null) {
                Audio audio = Audio.sharedAudio(context);
                if (audio != null) {
                    audio.addCompletionListener(this);
                    audio.addPeriodicListener(this);
                }
                arguments.put("path", path);
                MMJSResponse response = playAudioInternal(arguments);
                if (response == null || response.result != 1) {
                    return response;
                }
                injectJavascript("javascript:MMJS.sdk.audioStarted()");
                return response;
            }
        }
        return null;
    }

    private MMJSResponse playAudioInternal(Map<String, String> arguments) {
        Context context = (Context) this.contextRef.get();
        String path = (String) arguments.get("path");
        if (context == null || path == null) {
            return null;
        }
        Audio audio = Audio.sharedAudio(context);
        if (audio == null) {
            return null;
        }
        if (audio.isPlaying()) {
            return MMJSResponse.responseWithError("Audio already playing.");
        }
        if (path.startsWith("http")) {
            return audio.playAudio(Uri.parse(path), Boolean.parseBoolean((String) arguments.get("repeat")));
        }
        File file = AdCache.getDownloadFile(context, path);
        if (file.exists()) {
            return audio.playAudio(Uri.fromFile(file), Boolean.parseBoolean((String) arguments.get("repeat")));
        }
        return null;
    }

    public void onCompletion(MediaPlayer mp) {
        injectJavascript("javascript:MMJS.sdk.audioCompleted()");
        Context context = (Context) this.contextRef.get();
        if (context != null) {
            Audio audio = Audio.sharedAudio(context);
            if (audio != null) {
                audio.removeCompletionListener(this);
                audio.removePeriodicListener(this);
            }
        }
    }

    public void onUpdate(int currentPositionMillis) {
        injectJavascript("javascript:MMJS.sdk.audioPositionChange(" + currentPositionMillis + ")");
    }

    void injectJavascript(String javascript) {
        MMWebView webView = (MMWebView) this.mmWebViewRef.get();
        if (webView != null) {
            webView.loadUrl(javascript);
        }
    }

    public MMJSResponse textToSpeech(Map<String, String> arguments) {
        MMLog.d(TAG, "@@-Calling textToSpeech");
        NVASpeechKit speechKit = getCreateSpeechKit();
        if (speechKit == null) {
            return MMJSResponse.responseWithError("Unable to create speech kit");
        }
        String language = (String) arguments.get("language");
        String text = (String) arguments.get("text");
        if (TextUtils.isEmpty(language)) {
            language = "en_GB";
        }
        speechKit.stopActions();
        if (speechKit.textToSpeech(text, language)) {
            return MMJSResponse.responseWithSuccess();
        }
        return MMJSResponse.responseWithError("Failed in speechKit");
    }

    public MMJSResponse stopAudio(Map<String, String> map) {
        NVASpeechKit speechKit = getSpeechKit();
        if (speechKit == null) {
            return MMJSResponse.responseWithError("Unable to get speech kit");
        }
        speechKit.stopActions();
        if (this.contextRef != null) {
            Audio audio = Audio.sharedAudio((Context) this.contextRef.get());
            if (audio != null) {
                return audio.stop();
            }
        }
        return MMJSResponse.responseWithSuccess();
    }

    public MMJSResponse sampleBackgroundAudioLevel(Map<String, String> map) {
        NVASpeechKit speechKit = getCreateSpeechKit();
        if (speechKit == null) {
            return MMJSResponse.responseWithError("Unable to create speech kit");
        }
        speechKit.startSampleRecording();
        return null;
    }

    public MMJSResponse releaseVoice(Map<String, String> map) {
        if (releaseSpeechKit()) {
            return MMJSResponse.responseWithError("Unable to get speech kit");
        }
        return MMJSResponse.responseWithSuccess("released speechkit");
    }

    static boolean releaseSpeechKit() {
        return getInstance().release();
    }

    public MMJSResponse addCustomVoiceWords(Map<String, String> arguments) {
        NVASpeechKit speechKit = getCreateSpeechKit();
        if (speechKit == null) {
            return MMJSResponse.responseWithError("Unable to create speech kit");
        }
        String words = (String) arguments.get("words");
        if (TextUtils.isEmpty(words)) {
            return null;
        }
        speechKit.updateCustomWords(CustomWordsOp.Add, words.split(","));
        injectJavascript("javascript:MMJS.sdk.customVoiceWordsAdded()");
        return MMJSResponse.responseWithSuccess("Added " + words);
    }

    public MMJSResponse deleteCustomVoiceWords(Map<String, String> arguments) {
        NVASpeechKit speechKit = getCreateSpeechKit();
        if (speechKit == null) {
            return MMJSResponse.responseWithError("Unable to create speech kit");
        }
        String words = (String) arguments.get("words");
        if (TextUtils.isEmpty(words)) {
            return null;
        }
        speechKit.updateCustomWords(CustomWordsOp.Remove, words.split(","));
        injectJavascript("javascript:MMJS.sdk.customVoiceWordsDeleted()");
        return MMJSResponse.responseWithSuccess("Deleted " + words);
    }

    static SpeechKitHolder getInstance() {
        return SingletonHolder.INSTANCE;
    }

    static NVASpeechKit getSpeechKitInternal() {
        return getInstance().getSpeechKit();
    }

    static void setSpeechKit(NVASpeechKit speechKit) {
        getInstance().release();
        getInstance().setSpeechKit(speechKit);
    }
}
