package com.flurry.sdk;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import com.flurry.android.AdCreative;
import com.flurry.android.impl.ads.protocol.v13.AdFrame;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SuppressLint({"SetJavaScriptEnabled"})
public class eb extends ec implements OnKeyListener {
    private boolean A;
    private boolean B = true;
    private com.flurry.sdk.ec.a C = new com.flurry.sdk.ec.a(this) {
        final /* synthetic */ eb a;

        {
            this.a = r1;
        }

        public void a() {
            if (this.a.getCurrentBinding() == 3 && this.a.e != null) {
                if (this.a.isViewAttachedToActivity() && this.a.a(this.a.e)) {
                    this.a.removeView(this.a.e);
                }
                this.a.e.cleanupLayout();
                this.a.e = null;
            }
        }

        public void b() {
            if (this.a.getCurrentBinding() == 3 && this.a.e != null) {
                if (this.a.isViewAttachedToActivity() && this.a.a(this.a.e)) {
                    this.a.removeView(this.a.e);
                }
                this.a.e.cleanupLayout();
                this.a.e = null;
            }
        }

        public void c() {
            if (this.a.getCurrentBinding() == 3 && this.a.e != null) {
                this.a.e.cleanupLayout();
                this.a.e = null;
            }
        }
    };
    String a = null;
    fy<ed> b = new fy<ed>(this) {
        final /* synthetic */ eb a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((ed) fxVar);
        }

        public void a(final ed edVar) {
            fp.a().a(new Runnable(this) {
                final /* synthetic */ AnonymousClass1 b;

                public void run() {
                    switch (edVar.a) {
                        case SHOW_VIDEO_DIALOG:
                            this.b.a.a(edVar);
                            return;
                        case CLOSE_AD:
                            this.b.a.m();
                            return;
                        case DO_COLLAPSE:
                            this.b.a.b(edVar.b);
                            return;
                        case DO_EXPAND:
                            this.b.a.a(edVar.b);
                            return;
                        case CALL_COMPLETE:
                            this.b.a.c(edVar.b);
                            return;
                        default:
                            return;
                    }
                }
            });
        }
    };
    private boolean c = false;
    private final String d = eb.class.getSimpleName();
    private dq e;
    private boolean f;
    private WebView g;
    private ImageButton h;
    private int i;
    private boolean j;
    private WebViewClient k;
    private WebChromeClient l;
    private View m;
    private int n;
    private CustomViewCallback o;
    private Dialog p;
    private FrameLayout q;
    private int r;
    private Dialog s;
    private FrameLayout t;
    private boolean u;
    private boolean v;
    private boolean w;
    private boolean x;
    private boolean y = false;
    private AlertDialog z;

    final class a extends WebChromeClient {
        final /* synthetic */ eb a;

        private a(eb ebVar) {
            this.a = ebVar;
        }

        public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
            gd.a(3, this.a.d, "onShowCustomView(7)");
            if (this.a.getContext() instanceof Activity) {
                onShowCustomView(view, ((Activity) this.a.getContext()).getRequestedOrientation(), customViewCallback);
            } else {
                gd.a(3, this.a.d, "no activity present");
            }
        }

        public void onShowCustomView(View view, int i, CustomViewCallback customViewCallback) {
            gd.a(3, this.a.d, "onShowCustomView(14)");
            if (this.a.getContext() instanceof Activity) {
                final Activity activity = (Activity) this.a.getContext();
                if (this.a.m == null || this.a.l == null) {
                    this.a.m = view;
                    this.a.n = activity.getRequestedOrientation();
                    this.a.o = customViewCallback;
                    this.a.q = new FrameLayout(activity);
                    this.a.q.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
                    this.a.q.addView(this.a.m, new LayoutParams(-1, -1, 17));
                    ((ViewGroup) activity.getWindow().getDecorView()).addView(this.a.q, -1, -1);
                    if (this.a.p == null) {
                        this.a.p = new Dialog(this, 16973841, activity) {
                            final /* synthetic */ a b;

                            public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                                return activity.dispatchTouchEvent(motionEvent);
                            }

                            public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
                                return activity.dispatchTrackballEvent(motionEvent);
                            }
                        };
                        this.a.p.getWindow().setType(1000);
                        this.a.p.setOnShowListener(new OnShowListener(this) {
                            final /* synthetic */ a a;

                            {
                                this.a = r1;
                            }

                            public void onShow(DialogInterface dialogInterface) {
                                if (this.a.a.s != null) {
                                    this.a.a.s.hide();
                                }
                            }
                        });
                        this.a.p.setOnDismissListener(new OnDismissListener(this) {
                            final /* synthetic */ a a;

                            {
                                this.a = r1;
                            }

                            public void onDismiss(DialogInterface dialogInterface) {
                                gd.a(3, this.a.a.d, "customViewFullScreenDialog.onDismiss()");
                                if (this.a.a.m != null && this.a.a.l != null) {
                                    this.a.a.l.onHideCustomView();
                                }
                            }
                        });
                        this.a.p.setCancelable(true);
                        this.a.p.show();
                    }
                    cn.a(activity, i, true);
                    return;
                }
                this.a.l.onHideCustomView();
                return;
            }
            gd.a(3, this.a.d, "no activity present");
        }

        public void onHideCustomView() {
            gd.a(3, this.a.d, "onHideCustomView()");
            if (this.a.getContext() instanceof Activity) {
                Activity activity = (Activity) this.a.getContext();
                if (this.a.m != null) {
                    if (this.a.s != null) {
                        this.a.s.show();
                    }
                    ((ViewGroup) activity.getWindow().getDecorView()).removeView(this.a.q);
                    this.a.q.removeView(this.a.m);
                    if (this.a.p != null && this.a.p.isShowing()) {
                        this.a.p.hide();
                        this.a.p.setOnDismissListener(null);
                        this.a.p.dismiss();
                    }
                    this.a.p = null;
                    cn.a(activity, this.a.n);
                    this.a.o.onCustomViewHidden();
                    this.a.o = null;
                    this.a.q = null;
                    this.a.m = null;
                    return;
                }
                return;
            }
            gd.a(3, this.a.d, "no activity present");
        }
    }

    class b extends WebViewClient {
        final /* synthetic */ eb a;

        private b(eb ebVar) {
            this.a = ebVar;
        }

        public void onLoadResource(WebView webView, String str) {
            gd.a(3, this.a.d, "onLoadResource: url = " + str);
            super.onLoadResource(webView, str);
            if (str != null && webView != null && webView == this.a.g) {
                if (!str.equalsIgnoreCase(this.a.g.getUrl())) {
                    this.a.b();
                }
                if (!this.a.v && Uri.parse(str).getLastPathSegment() != null) {
                    if (this.a.w) {
                        this.a.v = true;
                        this.a.h();
                        if (this.a.u) {
                            this.a.k();
                            this.a.n();
                            this.a.o();
                        }
                    } else if (this.a.u) {
                        this.a.p();
                        if (this.a.r() && this.a.getCurrentBinding() == 2) {
                            this.a.q();
                        }
                    }
                }
            }
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            gd.a(3, this.a.d, "onPageStarted: url = " + str);
            if (str != null && webView != null && webView == this.a.g) {
                this.a.c();
                this.a.i();
                this.a.u = false;
                this.a.v = false;
            }
        }

        public void onPageFinished(WebView webView, String str) {
            gd.a(3, this.a.d, "onPageFinished: url = " + str + " adcontroller index: " + this.a.getAdController().c());
            if (str != null && webView != null && webView == this.a.g) {
                this.a.b();
                this.a.e();
                this.a.dismissProgressDialog();
                if (!this.a.a(this.a.g) && (this.a.getCurrentBinding() == 2 || this.a.getCurrentBinding() == 1)) {
                    gd.a(3, this.a.d, "adding WebView to AdUnityView");
                    this.a.addView(this.a.g);
                }
                this.a.u = true;
                if (this.a.w) {
                    if (this.a.v) {
                        this.a.k();
                        this.a.n();
                        this.a.o();
                    }
                } else if (this.a.v) {
                    aw a = b.a("mraidAdNotSupported");
                    this.a.b(a);
                    this.a.a(a, new HashMap(), this.a.getAdController(), 0);
                    if (this.a.r() && this.a.getCurrentBinding() == 2) {
                        this.a.q();
                    }
                }
            }
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            gd.a(3, this.a.d, "shouldOverrideUrlLoading: url = " + str);
            if (str == null || webView == null || webView != this.a.g) {
                return false;
            }
            String substring;
            Uri parse;
            boolean z;
            Object queryParameter;
            String c = cv.c(this.a.g.getUrl());
            if (!TextUtils.isEmpty(c) && str.startsWith(c)) {
                substring = str.substring(c.length());
                parse = Uri.parse(substring);
                if (!(!parse.isHierarchical() || TextUtils.isEmpty(parse.getScheme()) || TextUtils.isEmpty(parse.getAuthority()))) {
                    gd.a(3, this.a.d, "shouldOverrideUrlLoading: target url = " + substring);
                    parse = Uri.parse(substring);
                    gd.a(3, this.a.d, "shouldOverrideUrlLoading: getScheme = " + parse.getScheme());
                    if ("flurry".equals(parse.getScheme())) {
                        if (this.a.B) {
                            this.a.B = false;
                            this.a.a(aw.EV_CLICKED, new HashMap(), this.a.getAdController(), 0);
                        }
                        if (this.a.getAdController().l()) {
                            gd.a(3, this.a.d, "shouldOverrideUrlLoading: doGenericLaunch ");
                            i.a().o().a(this.a.getContext(), substring, true, this.a.getAdObject(), true, this.a.c);
                            return true;
                        }
                        if (cv.d(substring)) {
                            z = false;
                        } else {
                            gd.a(3, this.a.d, "shouldOverrideUrlLoading: isMarketUrl ");
                            z = cu.a(this.a.getContext(), substring);
                        }
                        if (!z && cv.f(substring)) {
                            gd.a(3, this.a.d, "shouldOverrideUrlLoading: isGooglePlayUrl ");
                            z = cu.b(this.a.getContext(), substring);
                        }
                        if (z) {
                            gd.a(3, this.a.d, "shouldOverrideUrlLoading: loadUrl doGenericLaunch ");
                            i.a().o().a(this.a.getContext(), substring, true, this.a.getAdObject(), true, this.a.c);
                            return true;
                        }
                        this.a.a(aw.INTERNAL_EV_APP_EXIT, Collections.emptyMap(), this.a.getAdController(), 0);
                        return true;
                    }
                    queryParameter = parse.getQueryParameter("event");
                    if (!TextUtils.isEmpty(queryParameter)) {
                        return true;
                    }
                    aw a = b.a(queryParameter);
                    this.a.a(a);
                    this.a.a(a, parse);
                    Map h = hp.h(parse.getEncodedQuery());
                    h.put("requiresCallComplete", "true");
                    this.a.a(a, h, this.a.getAdController(), 0);
                    return true;
                }
            }
            substring = str;
            parse = Uri.parse(substring);
            gd.a(3, this.a.d, "shouldOverrideUrlLoading: getScheme = " + parse.getScheme());
            if ("flurry".equals(parse.getScheme())) {
                if (this.a.B) {
                    this.a.B = false;
                    this.a.a(aw.EV_CLICKED, new HashMap(), this.a.getAdController(), 0);
                }
                if (this.a.getAdController().l()) {
                    gd.a(3, this.a.d, "shouldOverrideUrlLoading: doGenericLaunch ");
                    i.a().o().a(this.a.getContext(), substring, true, this.a.getAdObject(), true, this.a.c);
                    return true;
                }
                if (cv.d(substring)) {
                    z = false;
                } else {
                    gd.a(3, this.a.d, "shouldOverrideUrlLoading: isMarketUrl ");
                    z = cu.a(this.a.getContext(), substring);
                }
                gd.a(3, this.a.d, "shouldOverrideUrlLoading: isGooglePlayUrl ");
                z = cu.b(this.a.getContext(), substring);
                if (z) {
                    gd.a(3, this.a.d, "shouldOverrideUrlLoading: loadUrl doGenericLaunch ");
                    i.a().o().a(this.a.getContext(), substring, true, this.a.getAdObject(), true, this.a.c);
                    return true;
                }
                this.a.a(aw.INTERNAL_EV_APP_EXIT, Collections.emptyMap(), this.a.getAdController(), 0);
                return true;
            }
            queryParameter = parse.getQueryParameter("event");
            if (!TextUtils.isEmpty(queryParameter)) {
                return true;
            }
            aw a2 = b.a(queryParameter);
            this.a.a(a2);
            this.a.a(a2, parse);
            Map h2 = hp.h(parse.getEncodedQuery());
            h2.put("requiresCallComplete", "true");
            this.a.a(a2, h2, this.a.getAdController(), 0);
            return true;
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            gd.a(3, this.a.d, "onReceivedError: url = " + str2);
            this.a.dismissProgressDialog();
            Uri parse = Uri.parse(str2);
            if (Event.INTENT_MARKET.equals(parse.getScheme())) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(parse);
                this.a.getContext().startActivity(intent);
                this.a.m();
                return;
            }
            Map hashMap = new HashMap();
            hashMap.put("errorCode", Integer.toString(av.kAdDisplayError.a()));
            hashMap.put("webViewErrorCode", Integer.toString(i));
            hashMap.put("failingUrl", str2);
            this.a.a(aw.EV_RENDER_FAILED, hashMap, this.a.getAdController(), 0);
        }
    }

    private void a(a aVar) {
        int f = hn.f();
        int g = hn.g();
        gd.a(3, this.d, "expand to width = " + f + " height = " + g);
        r b = aVar.c().b();
        ap c = aVar.c().c();
        if ((b instanceof s) && ((s) b).s() != null) {
            a(aw.EV_CLICKED, Collections.emptyMap(), c, 0);
            a(f, g);
        }
        if (aVar.c().b.containsKey("url")) {
            this.a = (String) aVar.c().b.get("url");
            c.a(true);
            cu.a(getContext(), this.a, false, b, true, false);
        }
    }

    public boolean onBackKey() {
        a(aw.EV_AD_WILL_CLOSE, Collections.emptyMap(), getAdController(), 0);
        return true;
    }

    private void b(a aVar) {
        int i = getCurrentAdFrame().adSpaceLayout.adWidth;
        int i2 = getCurrentAdFrame().adSpaceLayout.adHeight;
        int b = hn.b(i);
        i2 = hn.b(i2);
        if (this.a != null) {
            this.a = null;
            initLayout();
        }
        r b2 = aVar.c().b();
        if ((b2 instanceof s) && ((s) b2).s() != null) {
            b(b, i2);
        }
    }

    private void a(ed edVar) {
        gd.a(6, this.d, "show Video dialog.");
        final a aVar = edVar.b;
        final int i = edVar.c;
        if (isViewAttachedToActivity()) {
            Builder builder = new Builder(getContext());
            CharSequence a = aVar.a("message");
            CharSequence a2 = aVar.a("confirmDisplay");
            CharSequence a3 = aVar.a("cancelDisplay");
            if (TextUtils.isEmpty(a) || TextUtils.isEmpty(a2) || TextUtils.isEmpty(a3)) {
                a = "Are you sure?";
                a2 = "Cancel";
                a3 = "OK";
            }
            builder.setMessage(a);
            builder.setCancelable(false);
            builder.setPositiveButton(a3, new OnClickListener(this) {
                final /* synthetic */ eb c;

                public void onClick(DialogInterface dialogInterface, int i) {
                    Map hashMap = new HashMap();
                    hashMap.put("sourceEvent", aVar.c().a.a());
                    this.c.a(aw.EV_USER_CONFIRMED, hashMap, this.c.getAdController(), i + 1);
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton(a2, new OnClickListener(this) {
                final /* synthetic */ eb c;

                public void onClick(DialogInterface dialogInterface, int i) {
                    Map hashMap = new HashMap();
                    hashMap.put("sourceEvent", aVar.c().a.a());
                    this.c.a(aw.EV_USER_CANCELLED, hashMap, this.c.getAdController(), i + 1);
                    dialogInterface.dismiss();
                    if (this.c.e != null) {
                        this.c.e.a(this.c.getAdController().m().a());
                    }
                }
            });
            if (this.e != null && isViewAttachedToActivity()) {
                this.z = builder.create();
                this.z.show();
                this.e.c();
                return;
            }
            return;
        }
        gd.a(6, this.d, "View not attached to any window.");
    }

    private synchronized void setFlurryJsEnvInitialized(boolean z) {
        this.j = z;
    }

    private synchronized boolean a() {
        return this.j;
    }

    private synchronized void b() {
        if (!a()) {
            d();
            setFlurryJsEnvInitialized(true);
        }
    }

    private synchronized void c() {
        setFlurryJsEnvInitialized(false);
    }

    private void d() {
        gd.a(3, this.d, "initializeFlurryJsEnv");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("javascript:(function() {");
        stringBuilder.append("var Hogan={};(function(Hogan,useArrayBuffer){Hogan.Template=function(renderFunc,text,compiler,options){this.r=renderFunc||this.r;this.c=compiler;this.options=options;this.text=text||\"\";this.buf=useArrayBuffer?[]:\"\"};Hogan.Template.prototype={r:function(context,partials,indent){return\"\"},v:hoganEscape,t:coerceToString,render:function render(context,partials,indent){return this.ri([context],partials||{},indent)},ri:function(context,partials,indent){return this.r(context,partials,indent)},rp:function(name,context,partials,indent){var partial=partials[name];if(!partial){return\"\"}if(this.c&&typeof partial==\"string\"){partial=this.c.compile(partial,this.options)}return partial.ri(context,partials,indent)},rs:function(context,partials,section){var tail=context[context.length-1];if(!isArray(tail)){section(context,partials,this);return}for(var i=0;i<tail.length;i++){context.push(tail[i]);section(context,partials,this);context.pop()}},s:function(val,ctx,partials,inverted,start,end,tags){var pass;if(isArray(val)&&val.length===0){return false}if(typeof val==\"function\"){val=this.ls(val,ctx,partials,inverted,start,end,tags)}pass=(val===\"\")||!!val;if(!inverted&&pass&&ctx){ctx.push((typeof val==\"object\")?val:ctx[ctx.length-1])}return pass},d:function(key,ctx,partials,returnFound){var names=key.split(\".\"),val=this.f(names[0],ctx,partials,returnFound),cx=null;if(key===\".\"&&isArray(ctx[ctx.length-2])){return ctx[ctx.length-1]}for(var i=1;i<names.length;i++){if(val&&typeof val==\"object\"&&names[i] in val){cx=val;val=val[names[i]]}else{val=\"\"}}if(returnFound&&!val){return false}if(!returnFound&&typeof val==\"function\"){ctx.push(cx);val=this.lv(val,ctx,partials);ctx.pop()}return val},f:function(key,ctx,partials,returnFound){var val=false,v=null,found=false;for(var i=ctx.length-1;i>=0;i--){v=ctx[i];if(v&&typeof v==\"object\"&&key in v){val=v[key];found=true;break}}if(!found){return(returnFound)?false:\"\"}if(!returnFound&&typeof val==\"function\"){val=this.lv(val,ctx,partials)}return val},ho:function(val,cx,partials,text,tags){var compiler=this.c;var options=this.options;options.delimiters=tags;var text=val.call(cx,text);text=(text==null)?String(text):text.toString();this.b(compiler.compile(text,options).render(cx,partials));return false},b:(useArrayBuffer)?function(s){this.buf.push(s)}:function(s){this.buf+=s},fl:(useArrayBuffer)?function(){var r=this.buf.join(\"\");this.buf=[];return r}:function(){var r=this.buf;this.buf=\"\";return r},ls:function(val,ctx,partials,inverted,start,end,tags){var cx=ctx[ctx.length-1],t=null;if(!inverted&&this.c&&val.length>0){return this.ho(val,cx,partials,this.text.substring(start,end),tags)}t=val.call(cx);if(typeof t==\"function\"){if(inverted){return true}else{if(this.c){return this.ho(t,cx,partials,this.text.substring(start,end),tags)}}}return t},lv:function(val,ctx,partials){var cx=ctx[ctx.length-1];var result=val.call(cx);if(typeof result==\"function\"){result=coerceToString(result.call(cx));if(this.c&&~result.indexOf(\"{\\u007B\")){return this.c.compile(result,this.options).render(cx,partials)}}return coerceToString(result)}};var rAmp=/&/g,rLt=/</g,rGt=/>/g,rApos=/\\'/g,rQuot=/\\\"/g,hChars=/[&<>\\\"\\']/;function coerceToString(val){return String((val===null||val===undefined)?\"\":val)}function hoganEscape(str){str=coerceToString(str);return hChars.test(str)?str.replace(rAmp,\"&amp;\").replace(rLt,\"&lt;\").replace(rGt,\"&gt;\").replace(rApos,\"&#39;\").replace(rQuot,\"&quot;\"):str}var isArray=Array.isArray||function(a){return Object.prototype.toString.call(a)===\"[object Array]\"}})(typeof exports!==\"undefined\"?exports:Hogan);(function(Hogan){var rIsWhitespace=/\\S/,rQuot=/\\\"/g,rNewline=/\\n/g,rCr=/\\r/g,rSlash=/\\\\/g,tagTypes={\"#\":1,\"^\":2,\"/\":3,\"!\":4,\">\":5,\"<\":6,\"=\":7,_v:8,\"{\":9,\"&\":10};Hogan.scan=function scan(text,delimiters){var len=text.length,IN_TEXT=0,IN_TAG_TYPE=1,IN_TAG=2,state=IN_TEXT,tagType=null,tag=null,buf=\"\",tokens=[],seenTag=false,i=0,lineStart=0,otag=\"{{\",ctag=\"}}\";function addBuf(){if(buf.length>0){tokens.push(new String(buf));buf=\"\"}}function lineIsWhitespace(){var isAllWhitespace=true;for(var j=lineStart;j<tokens.length;j++){isAllWhitespace=(tokens[j].tag&&tagTypes[tokens[j].tag]<tagTypes._v)||(!tokens[j].tag&&tokens[j].match(rIsWhitespace)===null);if(!isAllWhitespace){return false}}return isAllWhitespace}function filterLine(haveSeenTag,noNewLine){addBuf();if(haveSeenTag&&lineIsWhitespace()){for(var j=lineStart,next;j<tokens.length;j++){if(!tokens[j].tag){if((next=tokens[j+1])&&next.tag==\">\"){next.indent=tokens[j].toString()}tokens.splice(j,1)}}}else{if(!noNewLine){tokens.push({tag:\"\\n\"})}}seenTag=false;lineStart=tokens.length}function changeDelimiters(text,index){var close=\"=\"+ctag,closeIndex=text.indexOf(close,index),delimiters=trim(text.substring(text.indexOf(\"=\",index)+1,closeIndex)).split(\" \");otag=delimiters[0];ctag=delimiters[1];return closeIndex+close.length-1}if(delimiters){delimiters=delimiters.split(\" \");otag=delimiters[0];ctag=delimiters[1]}for(i=0;i<len;i++){if(state==IN_TEXT){if(tagChange(otag,text,i)){--i;addBuf();state=IN_TAG_TYPE}else{if(text.charAt(i)==\"\\n\"){filterLine(seenTag)}else{buf+=text.charAt(i)}}}else{if(state==IN_TAG_TYPE){i+=otag.length-1;tag=tagTypes[text.charAt(i+1)];tagType=tag?text.charAt(i+1):\"_v\";if(tagType==\"=\"){i=changeDelimiters(text,i);state=IN_TEXT}else{if(tag){i++}state=IN_TAG}seenTag=i}else{if(tagChange(ctag,text,i)){tokens.push({tag:tagType,n:trim(buf),otag:otag,ctag:ctag,i:(tagType==\"/\")?seenTag-ctag.length:i+otag.length});buf=\"\";i+=ctag.length-1;state=IN_TEXT;if(tagType==\"{\"){if(ctag==\"}}\"){i++}else{cleanTripleStache(tokens[tokens.length-1])}}}else{buf+=text.charAt(i)}}}}filterLine(seenTag,true);return tokens};function cleanTripleStache(token){if(token.n.substr(token.n.length-1)===\"}\"){token.n=token.n.substring(0,token.n.length-1)}}function trim(s){if(s.trim){return s.trim()}return s.replace(/^\\s*|\\s*$/g,\"\")}function tagChange(tag,text,index){if(text.charAt(index)!=tag.charAt(0)){return false}for(var i=1,l=tag.length;i<l;i++){if(text.charAt(index+i)!=tag.charAt(i)){return false}}return true}function buildTree(tokens,kind,stack,customTags){var instructions=[],opener=null,token=null;while(tokens.length>0){token=tokens.shift();if(token.tag==\"#\"||token.tag==\"^\"||isOpener(token,customTags)){stack.push(token);token.nodes=buildTree(tokens,token.tag,stack,customTags);instructions.push(token)}else{if(token.tag==\"/\"){if(stack.length===0){throw new Error(\"Closing tag without opener: /\"+token.n)}opener=stack.pop();if(token.n!=opener.n&&!isCloser(token.n,opener.n,customTags)){throw new Error(\"Nesting error: \"+opener.n+\" vs. \"+token.n)}opener.end=token.i;return instructions}else{instructions.push(token)}}}if(stack.length>0){throw new Error(\"missing closing tag: \"+stack.pop().n)}return instructions}function isOpener(token,tags){for(var i=0,l=tags.length;i<l;i++){if(tags[i].o==token.n){token.tag=\"#\";return true}}}function isCloser(close,open,tags){for(var i=0,l=tags.length;i<l;i++){if(tags[i].c==close&&tags[i].o==open){return true}}}Hogan.generate=function(tree,text,options){var code='var _=this;_.b(i=i||\"\");'+walk(tree)+\"return _.fl();\";if(options.asString){return\"function(c,p,i){\"+code+\";}\"}return new Hogan.Template(new Function(\"c\",\"p\",\"i\",code),text,Hogan,options)};function esc(s){return s.replace(rSlash,\"\\\\\\\\\").replace(rQuot,'\\\\\"').replace(rNewline,\"\\\\n\").replace(rCr,\"\\\\r\")}function chooseMethod(s){return(~s.indexOf(\".\"))?\"d\":\"f\"}function walk(tree){var code=\"\";for(var i=0,l=tree.length;i<l;i++){var tag=tree[i].tag;if(tag==\"#\"){code+=section(tree[i].nodes,tree[i].n,chooseMethod(tree[i].n),tree[i].i,tree[i].end,tree[i].otag+\" \"+tree[i].ctag)}else{if(tag==\"^\"){code+=invertedSection(tree[i].nodes,tree[i].n,chooseMethod(tree[i].n))}else{if(tag==\"<\"||tag==\">\"){code+=partial(tree[i])}else{if(tag==\"{\"||tag==\"&\"){code+=tripleStache(tree[i].n,chooseMethod(tree[i].n))}else{if(tag==\"\\n\"){code+=text('\"\\\\n\"'+(tree.length-1==i?\"\":\" + i\"))}else{if(tag==\"_v\"){code+=variable(tree[i].n,chooseMethod(tree[i].n))}else{if(tag===undefined){code+=text('\"'+esc(tree[i])+'\"')}}}}}}}}return code}function section(nodes,id,method,start,end,tags){return\"if(_.s(_.\"+method+'(\"'+esc(id)+'\",c,p,1),c,p,0,'+start+\",\"+end+',\"'+tags+'\")){_.rs(c,p,function(c,p,_){'+walk(nodes)+\"});c.pop();}\"}function invertedSection(nodes,id,method){return\"if(!_.s(_.\"+method+'(\"'+esc(id)+'\",c,p,1),c,p,1,0,0,\"\")){'+walk(nodes)+\"};\"}function partial(tok){return'_.b(_.rp(\"'+esc(tok.n)+'\",c,p,\"'+(tok.indent||\"\")+'\"));'}function tripleStache(id,method){return\"_.b(_.t(_.\"+method+'(\"'+esc(id)+'\",c,p,0)));'}function variable(id,method){return\"_.b(_.v(_.\"+method+'(\"'+esc(id)+'\",c,p,0)));'}function text(id){return\"_.b(\"+id+\");\"}Hogan.parse=function(tokens,text,options){options=options||{};return buildTree(tokens,\"\",[],options.sectionTags||[])},Hogan.cache={};Hogan.compile=function(text,options){options=options||{};var key=text+\"||\"+!!options.asString;var t=this.cache[key];if(t){return t}t=this.generate(this.parse(this.scan(text,options.delimiters),text,options),text,options);return this.cache[key]=t}})(typeof exports!==\"undefined\"?exports:Hogan);");
        stringBuilder.append("var flurryBridgeCtor=function(w){var flurryadapter={};flurryadapter.flurryCallQueue=[];flurryadapter.flurryCallInProgress=false;flurryadapter.callComplete=function(cmd){if(this.flurryCallQueue.length==0){this.flurryCallInProgress=false;return}var adapterCall=this.flurryCallQueue.splice(0,1)[0];this.executeNativeCall(adapterCall);return\"OK\"};flurryadapter.executeCall=function(command){var adapterCall=\"flurry://flurrycall?event=\"+command;var value;for(var i=1;i<arguments.length;i+=2){value=arguments[i+1];if(value==null)continue;adapterCall+=\"&\"+arguments[i]+\"=\"+escape(value)}if(this.flurryCallInProgress)this.flurryCallQueue.push(adapterCall);else this.executeNativeCall(adapterCall)};flurryadapter.executeNativeCall=function(adapterCall){if(adapterCall.length==0)return;this.flurryCallInProgress=true;w.location=adapterCall};return flurryadapter};");
        stringBuilder.append("window.Hogan=Hogan;window.flurryadapter=flurryBridgeCtor(window);");
        stringBuilder.append("window.flurryAdapterAvailable=true;if(typeof window.FlurryAdapterReady === 'function'){window.FlurryAdapterReady();}");
        stringBuilder.append("})();");
        if (this.g != null) {
            this.g.loadUrl(stringBuilder.toString());
        }
    }

    private void e() {
        gd.a(3, this.d, "activateFlurryJsEnv");
        String currentContent = getCurrentContent();
        if (currentContent != null && currentContent.length() > 0 && !currentContent.equals("{}")) {
            String url = this.g.getUrl();
            CharSequence b = cv.b(url);
            CharSequence a = cv.a(b, url);
            if (!(TextUtils.isEmpty(a) || a.equals(b))) {
                gd.a(3, this.d, "content before {{mustached}} tags replacement = '" + currentContent + "'");
                currentContent = currentContent.replace(b, a);
                gd.a(3, this.d, "content after {{mustached}} tags replacement = '" + currentContent + "'");
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("javascript:");
            stringBuilder.append("(function(){");
            stringBuilder.append("if(!window.Hogan){var Hogan={};(function(Hogan,useArrayBuffer){Hogan.Template=function(renderFunc,text,compiler,options){this.r=renderFunc||this.r;this.c=compiler;this.options=options;this.text=text||\"\";this.buf=useArrayBuffer?[]:\"\"};Hogan.Template.prototype={r:function(context,partials,indent){return\"\"},v:hoganEscape,t:coerceToString,render:function render(context,partials,indent){return this.ri([context],partials||{},indent)},ri:function(context,partials,indent){return this.r(context,partials,indent)},rp:function(name,context,partials,indent){var partial=partials[name];if(!partial){return\"\"}if(this.c&&typeof partial==\"string\"){partial=this.c.compile(partial,this.options)}return partial.ri(context,partials,indent)},rs:function(context,partials,section){var tail=context[context.length-1];if(!isArray(tail)){section(context,partials,this);return}for(var i=0;i<tail.length;i++){context.push(tail[i]);section(context,partials,this);context.pop()}},s:function(val,ctx,partials,inverted,start,end,tags){var pass;if(isArray(val)&&val.length===0){return false}if(typeof val==\"function\"){val=this.ls(val,ctx,partials,inverted,start,end,tags)}pass=(val===\"\")||!!val;if(!inverted&&pass&&ctx){ctx.push((typeof val==\"object\")?val:ctx[ctx.length-1])}return pass},d:function(key,ctx,partials,returnFound){var names=key.split(\".\"),val=this.f(names[0],ctx,partials,returnFound),cx=null;if(key===\".\"&&isArray(ctx[ctx.length-2])){return ctx[ctx.length-1]}for(var i=1;i<names.length;i++){if(val&&typeof val==\"object\"&&names[i] in val){cx=val;val=val[names[i]]}else{val=\"\"}}if(returnFound&&!val){return false}if(!returnFound&&typeof val==\"function\"){ctx.push(cx);val=this.lv(val,ctx,partials);ctx.pop()}return val},f:function(key,ctx,partials,returnFound){var val=false,v=null,found=false;for(var i=ctx.length-1;i>=0;i--){v=ctx[i];if(v&&typeof v==\"object\"&&key in v){val=v[key];found=true;break}}if(!found){return(returnFound)?false:\"\"}if(!returnFound&&typeof val==\"function\"){val=this.lv(val,ctx,partials)}return val},ho:function(val,cx,partials,text,tags){var compiler=this.c;var options=this.options;options.delimiters=tags;var text=val.call(cx,text);text=(text==null)?String(text):text.toString();this.b(compiler.compile(text,options).render(cx,partials));return false},b:(useArrayBuffer)?function(s){this.buf.push(s)}:function(s){this.buf+=s},fl:(useArrayBuffer)?function(){var r=this.buf.join(\"\");this.buf=[];return r}:function(){var r=this.buf;this.buf=\"\";return r},ls:function(val,ctx,partials,inverted,start,end,tags){var cx=ctx[ctx.length-1],t=null;if(!inverted&&this.c&&val.length>0){return this.ho(val,cx,partials,this.text.substring(start,end),tags)}t=val.call(cx);if(typeof t==\"function\"){if(inverted){return true}else{if(this.c){return this.ho(t,cx,partials,this.text.substring(start,end),tags)}}}return t},lv:function(val,ctx,partials){var cx=ctx[ctx.length-1];var result=val.call(cx);if(typeof result==\"function\"){result=coerceToString(result.call(cx));if(this.c&&~result.indexOf(\"{\\u007B\")){return this.c.compile(result,this.options).render(cx,partials)}}return coerceToString(result)}};var rAmp=/&/g,rLt=/</g,rGt=/>/g,rApos=/\\'/g,rQuot=/\\\"/g,hChars=/[&<>\\\"\\']/;function coerceToString(val){return String((val===null||val===undefined)?\"\":val)}function hoganEscape(str){str=coerceToString(str);return hChars.test(str)?str.replace(rAmp,\"&amp;\").replace(rLt,\"&lt;\").replace(rGt,\"&gt;\").replace(rApos,\"&#39;\").replace(rQuot,\"&quot;\"):str}var isArray=Array.isArray||function(a){return Object.prototype.toString.call(a)===\"[object Array]\"}})(typeof exports!==\"undefined\"?exports:Hogan);(function(Hogan){var rIsWhitespace=/\\S/,rQuot=/\\\"/g,rNewline=/\\n/g,rCr=/\\r/g,rSlash=/\\\\/g,tagTypes={\"#\":1,\"^\":2,\"/\":3,\"!\":4,\">\":5,\"<\":6,\"=\":7,_v:8,\"{\":9,\"&\":10};Hogan.scan=function scan(text,delimiters){var len=text.length,IN_TEXT=0,IN_TAG_TYPE=1,IN_TAG=2,state=IN_TEXT,tagType=null,tag=null,buf=\"\",tokens=[],seenTag=false,i=0,lineStart=0,otag=\"{{\",ctag=\"}}\";function addBuf(){if(buf.length>0){tokens.push(new String(buf));buf=\"\"}}function lineIsWhitespace(){var isAllWhitespace=true;for(var j=lineStart;j<tokens.length;j++){isAllWhitespace=(tokens[j].tag&&tagTypes[tokens[j].tag]<tagTypes._v)||(!tokens[j].tag&&tokens[j].match(rIsWhitespace)===null);if(!isAllWhitespace){return false}}return isAllWhitespace}function filterLine(haveSeenTag,noNewLine){addBuf();if(haveSeenTag&&lineIsWhitespace()){for(var j=lineStart,next;j<tokens.length;j++){if(!tokens[j].tag){if((next=tokens[j+1])&&next.tag==\">\"){next.indent=tokens[j].toString()}tokens.splice(j,1)}}}else{if(!noNewLine){tokens.push({tag:\"\\n\"})}}seenTag=false;lineStart=tokens.length}function changeDelimiters(text,index){var close=\"=\"+ctag,closeIndex=text.indexOf(close,index),delimiters=trim(text.substring(text.indexOf(\"=\",index)+1,closeIndex)).split(\" \");otag=delimiters[0];ctag=delimiters[1];return closeIndex+close.length-1}if(delimiters){delimiters=delimiters.split(\" \");otag=delimiters[0];ctag=delimiters[1]}for(i=0;i<len;i++){if(state==IN_TEXT){if(tagChange(otag,text,i)){--i;addBuf();state=IN_TAG_TYPE}else{if(text.charAt(i)==\"\\n\"){filterLine(seenTag)}else{buf+=text.charAt(i)}}}else{if(state==IN_TAG_TYPE){i+=otag.length-1;tag=tagTypes[text.charAt(i+1)];tagType=tag?text.charAt(i+1):\"_v\";if(tagType==\"=\"){i=changeDelimiters(text,i);state=IN_TEXT}else{if(tag){i++}state=IN_TAG}seenTag=i}else{if(tagChange(ctag,text,i)){tokens.push({tag:tagType,n:trim(buf),otag:otag,ctag:ctag,i:(tagType==\"/\")?seenTag-ctag.length:i+otag.length});buf=\"\";i+=ctag.length-1;state=IN_TEXT;if(tagType==\"{\"){if(ctag==\"}}\"){i++}else{cleanTripleStache(tokens[tokens.length-1])}}}else{buf+=text.charAt(i)}}}}filterLine(seenTag,true);return tokens};function cleanTripleStache(token){if(token.n.substr(token.n.length-1)===\"}\"){token.n=token.n.substring(0,token.n.length-1)}}function trim(s){if(s.trim){return s.trim()}return s.replace(/^\\s*|\\s*$/g,\"\")}function tagChange(tag,text,index){if(text.charAt(index)!=tag.charAt(0)){return false}for(var i=1,l=tag.length;i<l;i++){if(text.charAt(index+i)!=tag.charAt(i)){return false}}return true}function buildTree(tokens,kind,stack,customTags){var instructions=[],opener=null,token=null;while(tokens.length>0){token=tokens.shift();if(token.tag==\"#\"||token.tag==\"^\"||isOpener(token,customTags)){stack.push(token);token.nodes=buildTree(tokens,token.tag,stack,customTags);instructions.push(token)}else{if(token.tag==\"/\"){if(stack.length===0){throw new Error(\"Closing tag without opener: /\"+token.n)}opener=stack.pop();if(token.n!=opener.n&&!isCloser(token.n,opener.n,customTags)){throw new Error(\"Nesting error: \"+opener.n+\" vs. \"+token.n)}opener.end=token.i;return instructions}else{instructions.push(token)}}}if(stack.length>0){throw new Error(\"missing closing tag: \"+stack.pop().n)}return instructions}function isOpener(token,tags){for(var i=0,l=tags.length;i<l;i++){if(tags[i].o==token.n){token.tag=\"#\";return true}}}function isCloser(close,open,tags){for(var i=0,l=tags.length;i<l;i++){if(tags[i].c==close&&tags[i].o==open){return true}}}Hogan.generate=function(tree,text,options){var code='var _=this;_.b(i=i||\"\");'+walk(tree)+\"return _.fl();\";if(options.asString){return\"function(c,p,i){\"+code+\";}\"}return new Hogan.Template(new Function(\"c\",\"p\",\"i\",code),text,Hogan,options)};function esc(s){return s.replace(rSlash,\"\\\\\\\\\").replace(rQuot,'\\\\\"').replace(rNewline,\"\\\\n\").replace(rCr,\"\\\\r\")}function chooseMethod(s){return(~s.indexOf(\".\"))?\"d\":\"f\"}function walk(tree){var code=\"\";for(var i=0,l=tree.length;i<l;i++){var tag=tree[i].tag;if(tag==\"#\"){code+=section(tree[i].nodes,tree[i].n,chooseMethod(tree[i].n),tree[i].i,tree[i].end,tree[i].otag+\" \"+tree[i].ctag)}else{if(tag==\"^\"){code+=invertedSection(tree[i].nodes,tree[i].n,chooseMethod(tree[i].n))}else{if(tag==\"<\"||tag==\">\"){code+=partial(tree[i])}else{if(tag==\"{\"||tag==\"&\"){code+=tripleStache(tree[i].n,chooseMethod(tree[i].n))}else{if(tag==\"\\n\"){code+=text('\"\\\\n\"'+(tree.length-1==i?\"\":\" + i\"))}else{if(tag==\"_v\"){code+=variable(tree[i].n,chooseMethod(tree[i].n))}else{if(tag===undefined){code+=text('\"'+esc(tree[i])+'\"')}}}}}}}}return code}function section(nodes,id,method,start,end,tags){return\"if(_.s(_.\"+method+'(\"'+esc(id)+'\",c,p,1),c,p,0,'+start+\",\"+end+',\"'+tags+'\")){_.rs(c,p,function(c,p,_){'+walk(nodes)+\"});c.pop();}\"}function invertedSection(nodes,id,method){return\"if(!_.s(_.\"+method+'(\"'+esc(id)+'\",c,p,1),c,p,1,0,0,\"\")){'+walk(nodes)+\"};\"}function partial(tok){return'_.b(_.rp(\"'+esc(tok.n)+'\",c,p,\"'+(tok.indent||\"\")+'\"));'}function tripleStache(id,method){return\"_.b(_.t(_.\"+method+'(\"'+esc(id)+'\",c,p,0)));'}function variable(id,method){return\"_.b(_.v(_.\"+method+'(\"'+esc(id)+'\",c,p,0)));'}function text(id){return\"_.b(\"+id+\");\"}Hogan.parse=function(tokens,text,options){options=options||{};return buildTree(tokens,\"\",[],options.sectionTags||[])},Hogan.cache={};Hogan.compile=function(text,options){options=options||{};var key=text+\"||\"+!!options.asString;var t=this.cache[key];if(t){return t}t=this.generate(this.parse(this.scan(text,options.delimiters),text,options),text,options);return this.cache[key]=t}})(typeof exports!==\"undefined\"?exports:Hogan);window.Hogan=Hogan;}");
            stringBuilder.append("if(!window.flurryadapter){var flurryBridgeCtor=function(w){var flurryadapter={};flurryadapter.flurryCallQueue=[];flurryadapter.flurryCallInProgress=false;flurryadapter.callComplete=function(cmd){if(this.flurryCallQueue.length==0){this.flurryCallInProgress=false;return}var adapterCall=this.flurryCallQueue.splice(0,1)[0];this.executeNativeCall(adapterCall);return\"OK\"};flurryadapter.executeCall=function(command){var adapterCall=\"flurry://flurrycall?event=\"+command;var value;for(var i=1;i<arguments.length;i+=2){value=arguments[i+1];if(value==null)continue;adapterCall+=\"&\"+arguments[i]+\"=\"+escape(value)}if(this.flurryCallInProgress)this.flurryCallQueue.push(adapterCall);else this.executeNativeCall(adapterCall)};flurryadapter.executeNativeCall=function(adapterCall){if(adapterCall.length==0)return;this.flurryCallInProgress=true;w.location=adapterCall};return flurryadapter};window.flurryadapter=flurryBridgeCtor(window);}");
            stringBuilder.append("if(!window.flurryAdapterAvailable){window.flurryAdapterAvailable=true;if(typeof window.FlurryAdapterReady === 'function'){window.FlurryAdapterReady();} }");
            currentContent = hp.g(currentContent);
            stringBuilder.append("var content='");
            stringBuilder.append(currentContent);
            stringBuilder.append("';var compiled=window.Hogan.compile(document.body.innerHTML);var rendered=compiled.render(JSON.parse(content));document.body.innerHTML=rendered;");
            stringBuilder.append("})();");
            if (this.g != null) {
                this.g.loadUrl(stringBuilder.toString());
            }
        }
    }

    private synchronized void setMraidJsEnvInitialized(boolean z) {
        this.A = z;
    }

    private synchronized boolean g() {
        return this.A;
    }

    private synchronized void h() {
        if (!g()) {
            j();
            setMraidJsEnvInitialized(true);
        }
    }

    private synchronized void i() {
        setMraidJsEnvInitialized(false);
    }

    private void j() {
        gd.a(3, this.d, "initializeMraid");
        String str = "{useCustomClose:" + false + ",isModal:" + false + ",width:undefined,height:undefined,placementType:\"" + (r() ? "interstitial" : "inline") + "\"}";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("javascript:(function() {");
        stringBuilder.append("var mraidCtor=function(flurryBridge,initState){var mraid={};var STATES=mraid.STATES={LOADING:\"loading\",UNKNOWN:\"unknown\",DEFAULT:\"default\",EXPANDED:\"expanded\",HIDDEN:\"hidden\"};var EVENTS=mraid.EVENTS={ASSETREADY:\"assetReady\",ASSETREMOVED:\"assetRemoved\",ASSETRETIRED:\"assetRetired\",INFO:\"info\",ERROR:\"error\",ORIENTATIONCHANGE:\"orientationChange\",READY:\"ready\",STATECHANGE:\"stateChange\",VIEWABLECHANGE:\"viewableChange\"};var listeners={};var currentState=STATES.LOADING;var expandProperties={width:initState.width,height:initState.height,isModal:initState.isModal,useCustomClose:false};var collapseProperties={};var placementType=initState.placementType;var disable=false;var safeCloseEnabled=false;var closeId=\"flurry-mraid-default-close\";var imgUrl=\"http://flurry.cachefly.net/adSpaceStyles/images/bttn-close-bw.png\";var safeClose=function(){try{if(window.mraid)window.mraid.close();else if(window.flurryadapter)flurryadapter.executeCall(\"adWillClose\");else console.log(\"unable to close\")}catch(error){console.log(\"unable to close: \"+error)}};var makeDefaultClose=function(){var img=document.createElement(\"img\");img.src=imgUrl;img.id=closeId;img.style.position=\"absolute\";img.style.top=\"10px\";img.style.right=\"10px\";img.style.width=\"50px\";img.style.height=\"50px\";img.style.zIndex=1E4;return img};var updateDefaultClose=function(){if(!expandProperties.useCustomClose&&(placementType===\"interstitial\"||currentState===STATES.EXPANDED)){addDefaultClose();flurryBridge.executeCall(\"mraidCloseButtonVisible\", \"useCustomClose\", \"true\");safeCloseEnabled=true;console.log('close button added');}else {removeDefaultClose(); console.log('close button removed');}};var addDefaultClose=function(){var closeButton=document.getElementById(closeId);if(!closeButton){closeButton=makeDefaultClose();document.body.appendChild(closeButton)}};var removeDefaultClose=function(){var closeButton=document.getElementById(closeId);if(closeButton)document.body.removeChild(closeButton)};var setupDefaultCloseHandler=function(){document.body.addEventListener(\"click\",function(e){e=e||window.event;var target=e.target||e.srcElement;if(target.id===closeId)safeClose()})};var contains=function(value,obj){for(var i in obj)if(obj[i]===value)return true;return false};var stringify=function(obj){if(typeof obj==\"object\")if(obj.push){var out=[];for(var p in obj)if(obj.hasOwnProperty(p))out.push(obj[p]);return\"[\"+out.join(\",\")+\"]\"}else{var out=[];for(var p in obj)if(obj.hasOwnProperty(p))out.push(\"'\"+p+\"':\"+obj[p]);return\"{\"+out.join(\",\")+\"}\"}else return new String(obj)};var broadcastEvent=function(){var args=new Array(arguments.length);for(var i=0;i<arguments.length;i++)args[i]=arguments[i];var event=args.shift();try{if(listeners[event])for(var j=0;j<listeners[event].length;j++)if(typeof listeners[event][j]===\"function\")listeners[event][j].apply(undefined,args);else if(typeof listeners[event][j]===\"string\"&&typeof window[listeners[event][j]]===\"function\")window[listeners[event][j]].apply(undefined,args)}catch(e){console.log(e)}};mraid.disable=function(){removeDefaultClose();disable=true};mraid.addEventListener=function(event,listener){if(disable)return;if(!event||!listener)broadcastEvent(EVENTS.ERROR,\"Both event and listener are required.\",\"addEventListener\");else if(!contains(event,EVENTS))broadcastEvent(EVENTS.ERROR,\"Unknown event: \"+event,\"addEventListener\");else if(!listeners[event])listeners[event]=[listener];else listeners[event].push(listener);updateDefaultClose();flurryBridge.executeCall(\"eventListenerAdded\")};mraid.stateChange=function(newState){if(disable)return;if(currentState===newState)return;broadcastEvent(EVENTS.INFO,\"setting state to \"+stringify(newState));var oldState=currentState;currentState=newState;if(oldState===STATES.LOADING&&newState===STATES.DEFAULT){setupDefaultCloseHandler();broadcastEvent(EVENTS.READY)}else if(oldState===STATES.HIDDEN||newState===STATES.HIDDEN)broadcastEvent(EVENTS.VIEWABLECHANGE);else if(oldState===STATES.DEFAULT&&newState===STATES.EXPANDED)updateDefaultClose();else if(newState===STATES.DEFAULT&&oldState===STATES.EXPANDED)updateDefaultClose();broadcastEvent(EVENTS.STATECHANGE,currentState)};mraid.close=function(){if(disable)return;var state=mraid.getState();if(state===STATES.DEFAULT){mraid.stateChange(STATES.HIDDEN);flurryBridge.executeCall(\"adWillClose\")}else if(state===STATES.EXPANDED){mraid.stateChange(STATES.DEFAULT);flurryBridge.executeCall(\"collapse\")}else console.log(\"close() called in state \"+state)};mraid.expand=function(url){if(disable)return;var state=mraid.getState();if(state!==STATES.DEFAULT){console.log(\"expand() called in state \"+state);return}if(placementType===\"interstitial\"){console.log(\"expand() called for placement type \"+placementType);return}if(url)flurryBridge.executeCall(\"open\",\"url\",url);else flurryBridge.executeCall(\"expand\",\"width\",expandProperties.width,\"height\",expandProperties.height);mraid.stateChange(STATES.EXPANDED)};mraid.setExpandProperties=function(properties){if(disable)return;if(typeof properties.width===\"number\"&&!isNaN(properties.width))expandProperties.width=properties.width;if(typeof properties.height===\"number\"&&!isNaN(properties.height))expandProperties.height=properties.height;if(typeof properties.useCustomClose===\"boolean\"){expandProperties.useCustomClose=properties.useCustomClose;updateDefaultClose()}};mraid.getExpandProperties=function(properties){if(disable)return;var ret={};ret.width=expandProperties.width;ret.height=expandProperties.height;ret.isModal=expandProperties.isModal;ret.useCustomClose=expandProperties.useCustomClose;return ret};mraid.getPlacementType=function(){return placementType};mraid.getVersion=function(){if(disable)return\"\";return\"1.0\"};mraid.getState=function(){if(disable)return\"\";return currentState};mraid.isViewable=function(){if(disable)return false;if(mraid.getState()===\"hidden\")return false;else return true};mraid.open=function(url){if(disable)return;try{flurryBridge.executeCall(\"open\",\"url\",url)}catch(e){console.log(e)}};mraid.playVideo=function(url){if(disable){return;}try{flurryBridge.executeCall(\"playVideo\",\"url\",url);}catch(e){console.log(e);}};mraid.removeEventListener=function(event,listener){if(disable)return;if(!event)broadcastEvent(\"error\",\"Must specify an event.\",\"removeEventListener\");else if(listener&&listeners[event])for(var i=0;i<listeners[event].length;i++){if(listeners[event][i]===listener)listeners[event].splice(i,1)}else if(listeners[event])listeners[event]=[]};mraid.useCustomClose=function(use){if(disable)return;if(typeof use===\"boolean\"){expandProperties.useCustomClose=use;updateDefaultClose();if (safeCloseEnabled){flurryBridge.executeCall(\"mraidCloseButtonVisible\", \"useCustomClose\", \"true\");}else{flurryBridge.executeCall(\"mraidCloseButtonVisible\", \"useCustomClose\", use);}}};return mraid};");
        stringBuilder.append("window.mraid=mraidCtor(window.flurryadapter,");
        stringBuilder.append(str);
        stringBuilder.append(");");
        stringBuilder.append("})();");
        if (this.g != null) {
            this.g.loadUrl(stringBuilder.toString());
        }
    }

    private void k() {
        gd.a(3, this.d, "activateMraid");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("javascript:");
        stringBuilder.append("if(window.mraid){window.mraid.stateChange(window.mraid.STATES.DEFAULT);}");
        if (this.g != null) {
            this.g.loadUrl(stringBuilder.toString());
        }
    }

    private void a(aw awVar) {
        if (awVar.equals(aw.EV_AD_LISTENER_ADDED)) {
            l();
        }
    }

    private void l() {
        if (getContext() instanceof Activity) {
            Activity activity = (Activity) getContext();
            if (r()) {
                cn.a(activity, cn.a(), true);
                return;
            }
            return;
        }
        gd.a(3, this.d, "no activity present");
    }

    public eb(Context context, r rVar, com.flurry.sdk.ec.a aVar, boolean z) {
        super(context, rVar, aVar);
        setClickable(true);
        this.c = z;
        if (getContext() instanceof Activity) {
            this.i = ((Activity) getContext()).getRequestedOrientation();
        }
        if (getAdUnit() != null) {
            this.w = getAdUnit().supportMRAID;
        } else {
            gd.a(3, this.d, "adunit is Null");
        }
    }

    private void m() {
        gd.a(3, this.d, "closing ad unity view");
        onViewClose();
    }

    public void cleanupLayout() {
        if (this.e != null) {
            this.e.cleanupLayout();
            this.e = null;
        }
        fz.a().a(this.b);
    }

    protected void onViewLoadTimeout() {
        Map hashMap = new HashMap();
        hashMap.put("errorCode", Integer.toString(av.kNoNetworkConnectivity.a()));
        co.a(aw.EV_AD_WILL_CLOSE, hashMap, getContext(), getAdObject(), getAdController(), 0);
    }

    public void initLayout() {
        gd.a(3, this.d, "initLayout: ad creative layout: {width = " + getCurrentAdFrame().adSpaceLayout.adWidth + ", height = " + getCurrentAdFrame().adSpaceLayout.adHeight + ", adFrameIndex = " + getAdController().c() + ", context = " + getContext() + "}");
        cleanupLayout();
        fz.a().a("com.flurry.android.impl.ads.views.AdViewEvent", this.b);
        Context context = getContext();
        removeAllViews();
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestLayout();
        switch (getCurrentBinding()) {
            case 1:
                if (this.g == null) {
                    this.g = new WebView(context);
                    this.g.getSettings().setJavaScriptEnabled(true);
                    this.g.setVerticalScrollBarEnabled(false);
                    this.g.setHorizontalScrollBarEnabled(false);
                    this.g.setBackgroundColor(0);
                    this.g.clearCache(false);
                    this.l = new a();
                    this.g.setWebChromeClient(this.l);
                    this.k = new b();
                    this.g.setWebViewClient(this.k);
                }
                if (this.a != null) {
                    b(this.a);
                } else if (getAdFrameIndex() == 0) {
                    String v = getAdController().v();
                    if (v == null) {
                        b(getCurrentDisplay());
                    } else {
                        String c = cv.c(getCurrentDisplay());
                        this.g.loadDataWithBaseURL(c, v, "text/html", "utf-8", c);
                        a(aw.EV_RENDERED, Collections.emptyMap(), getAdController(), 0);
                    }
                } else {
                    b(getCurrentDisplay());
                }
                this.g.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
                dismissProgressDialog();
                if (r()) {
                    showProgressDialog();
                }
                s();
                return;
            case 2:
                cy d = getAdController().d(getAdFrameIndex());
                if (d != null) {
                    a(d.f(), ds.VIDEO_AD_TYPE_VAST);
                    return;
                }
                if (this.g == null) {
                    this.g = new WebView(context);
                    this.g.getSettings().setJavaScriptEnabled(true);
                    this.g.setVerticalScrollBarEnabled(false);
                    this.g.setHorizontalScrollBarEnabled(false);
                    this.g.setBackgroundColor(0);
                    this.g.clearCache(false);
                    this.l = new a();
                    this.g.setWebChromeClient(this.l);
                    this.k = new b();
                    this.g.setWebViewClient(this.k);
                }
                this.g.loadDataWithBaseURL("base://url/", getCurrentDisplay(), "text/html", "utf-8", "base://url/");
                a(aw.EV_RENDERED, Collections.emptyMap(), getAdController(), 0);
                this.g.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
                dismissProgressDialog();
                if (r()) {
                    showProgressDialog();
                }
                s();
                return;
            case 3:
                a(getCurrentDisplay(), ds.VIDEO_AD_TYPE_CLIPS);
                return;
            default:
                Map hashMap = new HashMap();
                hashMap.put("errorCode", Integer.toString(av.kInvalidAdUnit.a()));
                a(aw.EV_RENDER_FAILED, hashMap, getAdController(), 0);
                return;
        }
    }

    private void a(String str, ds dsVar) {
        if (str != null) {
            Context context = getContext();
            if (this.e == null) {
                this.e = dr.a(context, dsVar, getAdObject(), this.C);
                this.e.setVideoUri(a(str));
                this.e.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
                this.e.initLayout();
            }
            addView(this.e);
        }
    }

    private Uri a(String str) {
        Uri uri = null;
        try {
            gd.a(3, this.d, "Precaching: Getting video from cache: " + str);
            File a = i.a().l().a(getAdObject(), str);
            if (a != null) {
                uri = Uri.parse("file://" + a.getAbsolutePath());
            }
        } catch (Throwable e) {
            gd.a(3, this.d, "Precaching: Error accessing cached file.", e);
        }
        if (uri != null) {
            return uri;
        }
        gd.a(3, this.d, "Precaching: Error using cached file. Loading with url: " + str);
        return Uri.parse(str);
    }

    private void b(final String str) {
        hr gkVar = new gk();
        gkVar.a(str);
        gkVar.a(10000);
        gkVar.b(new gy());
        gkVar.a(new com.flurry.sdk.gk.a<Void, String>(this) {
            final /* synthetic */ eb b;

            public void a(gk<Void, String> gkVar, final String str) {
                gd.a(3, this.b.d, "Prerender: HTTP status code is:" + gkVar.e() + " for url: " + str);
                if (gkVar.c()) {
                    final String c = cv.c(str);
                    this.b.a(aw.EV_RENDERED, Collections.emptyMap(), this.b.getAdController(), 0);
                    fp.a().a(new hq(this) {
                        final /* synthetic */ AnonymousClass5 c;

                        public void safeRun() {
                            if (this.c.b.g != null) {
                                this.c.b.g.loadDataWithBaseURL(c, str, "text/html", "utf-8", c);
                            }
                        }
                    });
                    return;
                }
                fp.a().a(new hq(this) {
                    final /* synthetic */ AnonymousClass5 a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        Map hashMap = new HashMap();
                        hashMap.put("errorCode", Integer.toString(av.kPrerenderDownloadFailed.a()));
                        this.a.b.a(aw.EV_RENDER_FAILED, hashMap, this.a.b.getAdController(), 0);
                    }
                });
            }
        });
        fn.a().a((Object) this, gkVar);
    }

    private void c(a aVar) {
        if (this.g != null) {
            gd.a(3, this.d, "Callcomplete " + aVar.c().a.a());
            this.g.loadUrl("javascript:flurryadapter.callComplete('" + aVar.c().a.a() + "');");
        }
    }

    private void a(int i, int i2) {
        if (getContext() instanceof Activity) {
            Activity activity = (Activity) getContext();
            if (this.s == null) {
                gd.a(3, this.d, "expand(" + i + "," + i2 + ")");
                i.a().d().b(getContext());
                i.a().e().a(getContext());
                if (!(this.g == null || -1 == indexOfChild(this.g))) {
                    removeView(this.g);
                }
                this.r = activity.getRequestedOrientation();
                if (this.t == null) {
                    this.t = new FrameLayout(activity);
                    this.t.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
                    if (this.g != null && this.g.getParent() == null) {
                        this.t.addView(this.g, new LayoutParams(-1, -1, 17));
                    }
                }
                if (this.s == null) {
                    this.s = new Dialog(activity, 16973834);
                    cx.a(this.s.getWindow());
                    this.s.setContentView(this.t, new ViewGroup.LayoutParams(-1, -1));
                    this.s.setOnDismissListener(new OnDismissListener(this) {
                        final /* synthetic */ eb a;

                        {
                            this.a = r1;
                        }

                        public void onDismiss(DialogInterface dialogInterface) {
                            gd.a(3, this.a.d, "extendedWebViewDialog.onDismiss()");
                            if (this.a.g != null) {
                                this.a.g.loadUrl("javascript:if(window.mraid){window.mraid.close();};");
                            }
                        }
                    });
                    this.s.setCancelable(true);
                    this.s.show();
                }
                cn.a(activity, cn.a(), true);
                return;
            }
            return;
        }
        gd.a(3, this.d, "no activity present");
    }

    private void b(int i, int i2) {
        if (getContext() instanceof Activity) {
            Activity activity = (Activity) getContext();
            i.a().d().c(getContext());
            i.a().e().b(getContext());
            if (this.s != null) {
                gd.a(3, this.d, "collapse(" + i + "," + i2 + ")");
                if (this.s != null && this.s.isShowing()) {
                    this.s.hide();
                    this.s.setOnDismissListener(null);
                    this.s.dismiss();
                }
                this.s = null;
                cn.a(activity, this.r);
                if (this.t != null) {
                    if (!(this.g == null || -1 == this.t.indexOfChild(this.g))) {
                        this.t.removeView(this.g);
                    }
                    this.t = null;
                }
                if (this.g != null && this.g.getParent() == null) {
                    addView(this.g);
                    return;
                }
                return;
            }
            return;
        }
        gd.a(3, this.d, "no activity present");
    }

    private void n() {
        this.g.loadUrl("javascript:(function() { document.getElementById('flurry_interstitial_close').style.display = 'none'; })()");
    }

    private void o() {
        if (r() && !this.x) {
            this.x = true;
            this.h = new ImageButton(getContext());
            ViewGroup.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams.addRule(10);
            layoutParams.addRule(11);
            float f = getResources().getDisplayMetrics().density;
            layoutParams.setMargins(0, (int) (10.0f * f), (int) (10.0f * f), 0);
            int i = (int) (f * 50.0f);
            layoutParams.height = (int) (50.0f * f);
            layoutParams.width = i;
            this.h.setLayoutParams(layoutParams);
            el elVar = new el();
            elVar.h();
            Bitmap e = elVar.e();
            this.h.setBackgroundColor(0);
            this.h.setImageBitmap(e);
            this.h.setOnClickListener(new View.OnClickListener(this) {
                final /* synthetic */ eb a;

                {
                    this.a = r1;
                }

                public void onClick(View view) {
                    this.a.a(aw.EV_AD_WILL_CLOSE, Collections.emptyMap(), this.a.getAdController(), 0);
                }
            });
            setMraidButtonVisibility(false);
            addView(this.h);
        }
    }

    private void a(aw awVar, Uri uri) {
        if (awVar.equals(aw.EV_MRAID_CLOSE_BUTTON_VISIBLE)) {
            Object queryParameter = uri.getQueryParameter("useCustomClose");
            if (TextUtils.isEmpty(queryParameter) || !queryParameter.equals("true")) {
                setMraidButtonVisibility(true);
            } else {
                setMraidButtonVisibility(false);
            }
        }
    }

    private void b(aw awVar) {
        if (awVar.equals(aw.EV_MRAID_NOT_SUPPORTED)) {
            p();
        }
    }

    private void setMraidButtonVisibility(boolean z) {
        if (this.h == null) {
            return;
        }
        if (z) {
            this.h.setVisibility(0);
        } else {
            this.h.setVisibility(4);
        }
    }

    private void p() {
        this.g.loadUrl("javascript:if(window.mraid && typeof window.mraid.disable === 'function'){window.mraid.disable();}");
    }

    private void q() {
        this.g.loadUrl("javascript:var closeButtonDiv =  document.getElementById('flurry_interstitial_close');if (typeof(closeButtonDiv) == 'undefined' || closeButtonDiv == null){var newdiv = document.createElement('div');var divIdName = 'flurry_interstitial_close';newdiv.setAttribute('id',divIdName);newdiv.innerHTML = '<a href=\"flurry://flurrycall?event=adWillClose\"><div id=\"rtb_close\"><img src=\"http://cdn.flurry.com/adSpaceStyles.dev/images/bttn-close-bw.png\" alt=\"close advertisement\" /></div></a></div>';document.body.appendChild(newdiv);}");
    }

    @TargetApi(11)
    public void onActivityResume() {
        fz.a().a("com.flurry.android.impl.ads.views.AdViewEvent", this.b);
        if (this.g != null && VERSION.SDK_INT >= 11) {
            this.f = true;
            this.g.onResume();
        }
        if (this.e != null) {
            this.e.onActivityResume();
        }
        if (this.e != null) {
            this.f = true;
        }
        this.B = true;
    }

    @TargetApi(11)
    public void onActivityPause() {
        if (this.g != null && VERSION.SDK_INT >= 11) {
            this.g.onPause();
        }
        if (this.e != null) {
            this.e.onActivityPause();
        }
        this.f = false;
        this.B = true;
    }

    @TargetApi(11)
    public void onActivityStop() {
        if (this.z != null && this.z.isShowing()) {
            this.z.dismiss();
            this.z = null;
        }
        if (this.e != null) {
            this.e.onActivityStop();
        }
        dismissProgressDialog();
    }

    @TargetApi(11)
    public void onActivityDestroy() {
        gd.a(3, this.d, "onDestroy");
        if (this.z != null && this.z.isShowing()) {
            this.z.dismiss();
            this.z = null;
        }
        dismissProgressDialog();
        if (this.e != null) {
            this.e.onActivityDestroy();
        }
        if (this.g != null) {
            if (this.l != null) {
                this.l.onHideCustomView();
            }
            if (this.s != null) {
                b(0, 0);
            }
            this.y = false;
            removeView(this.g);
            this.g.stopLoading();
            if (VERSION.SDK_INT >= 11) {
                this.g.onPause();
            }
            this.g.destroy();
            this.g = null;
        }
    }

    public void a(aw awVar, Map<String, String> map, ap apVar, int i) {
        gd.a(3, this.d, "fireEvent(event=" + awVar + ",params=" + map + ")");
        co.a(awVar, map, getContext(), getAdObject(), apVar, i);
    }

    private int getCurrentBinding() {
        return getCurrentAdFrame().binding;
    }

    private String getCurrentContent() {
        return getCurrentAdFrame().content;
    }

    private String getCurrentDisplay() {
        return getCurrentAdFrame().display;
    }

    private String getCurrentFormat() {
        return getCurrentAdFrame().adSpaceLayout.format;
    }

    private boolean r() {
        return getCurrentFormat().equals(AdCreative.kFormatTakeover);
    }

    private AdFrame getCurrentAdFrame() {
        return getAdController().j();
    }

    boolean a(View view) {
        Object parent = view.getParent();
        return parent != null && parent == this;
    }

    private void s() {
        if (getContext() instanceof Activity) {
            Activity activity = (Activity) getContext();
            if (r()) {
                int a = cn.a(activity, getAdUnit().screenOrientation);
                if (-1 != a) {
                    cn.a(activity, a, true);
                }
            }
        }
    }
}
